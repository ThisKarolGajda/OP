package com.github.thiskarolgajda.op.region.events;

import com.github.thiskarolgajda.op.core.teleportation.TeleportationManager;
import com.github.thiskarolgajda.op.core.warps.custom.SpawnWarp;
import com.github.thiskarolgajda.op.region.Region;
import com.github.thiskarolgajda.op.region.RegionDatabase;
import lombok.Getter;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.listeners.Listener;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class RegionEventListener extends Listener {
    @Inject
    private static RegionDatabase database;

    @Getter
    private static final Map<String, List<UUID>> playersInsideRegionData = new HashMap<>();

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMove(@NotNull PlayerMoveEvent event) {
        handlePlayerMovement(event.getPlayer(), event.getFrom(), event.getTo(), RegionEnterEvent.RegionEnterType.MOVE, event);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerTeleport(@NotNull PlayerTeleportEvent event) {
        handlePlayerMovement(event.getPlayer(), event.getFrom(), event.getTo(), RegionEnterEvent.RegionEnterType.TELEPORT, event);
    }

    private void handlePlayerMovement(Player player, Location fromLocation, Location toLocation, RegionEnterEvent.RegionEnterType enterType, Cancellable event) {
        if (toLocation == null) {
            return;
        }

        Chunk toChunk = toLocation.getChunk();
        Chunk fromChunk = fromLocation.getChunk();
        if (toChunk.equals(fromChunk)) {
            return;
        }

        Optional<Region> optionalToRegion = database.getRegion(toChunk.getX(), toChunk.getZ());
        Optional<Region> optionalFromRegion = database.getRegion(fromChunk.getX(), fromChunk.getZ());

        if (optionalFromRegion.isEmpty()) {
            optionalToRegion.ifPresent(toRegion -> {
                if (regionEnter(player, enterType, toRegion)) {
                    event.setCancelled(true);
                }
            });
        } else {
            Region fromRegion = optionalFromRegion.get();
            if (optionalToRegion.isEmpty() || !fromRegion.equals(optionalToRegion.get())) {
                if (regionLeave(fromRegion, player, RegionLeaveEvent.RegionLeaveType.MOVE)) {
                    event.setCancelled(true);
                }
            }

            optionalToRegion.ifPresent(toRegion -> {
                if (!toRegion.equals(fromRegion)) {
                    if (regionEnter(player, enterType, toRegion)) {
                        event.setCancelled(true);
                    }
                }
            });
        }
    }

    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        Chunk chunk = location.getChunk();

        Optional<Region> optionalRegion = database.getRegion(chunk.getX(), chunk.getZ());
        optionalRegion.ifPresent(region -> {
            if (regionEnter(player, RegionEnterEvent.RegionEnterType.JOIN, region)) {
                TeleportationManager.teleport(player, Plugin.get(SpawnWarp.class).getWarp(), true);
            }
        });
    }

    @EventHandler
    public void onPlayerQuit(@NotNull PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation();
        Chunk chunk = location.getChunk();

        Optional<Region> optionalRegion = database.getRegion(chunk.getX(), chunk.getZ());
        optionalRegion.ifPresent(region -> regionLeave(region, player, RegionLeaveEvent.RegionLeaveType.QUIT));
    }

    @EventHandler
    public void onPlayerRespawn(@NotNull PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        Location respawnLocation = event.getRespawnLocation();
        Chunk respawnChunk = respawnLocation.getChunk();

        Optional<Region> optionalFromRegion = database.getRegion(player.getLocation().getChunk().getX(), player.getLocation().getChunk().getZ());
        Optional<Region> optionalToRegion = database.getRegion(respawnChunk.getX(), respawnChunk.getZ());

        optionalFromRegion.ifPresent(fromRegion -> {
            if (optionalToRegion.isEmpty() || !optionalToRegion.get().equals(fromRegion)) {
                regionLeave(fromRegion, player, RegionLeaveEvent.RegionLeaveType.RESPAWN);
            }
        });

        if (optionalToRegion.isPresent() && optionalFromRegion.isPresent()) {
            Region toRegion = optionalToRegion.get();
            Region fromRegion = optionalFromRegion.get();

            if (!toRegion.equals(fromRegion)) {
                if (regionEnter(player, RegionEnterEvent.RegionEnterType.RESPAWN, toRegion)) {
                    TeleportationManager.teleport(player, Plugin.get(SpawnWarp.class).getWarp(), true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerPortal(@NotNull PlayerPortalEvent event) {
        Player player = event.getPlayer();
        Location toLocation = event.getTo();
        if (toLocation == null) {
            return;
        }

        Chunk toChunk = toLocation.getChunk();
        Chunk fromChunk = event.getFrom().getChunk();

        Optional<Region> optionalToRegion = database.getRegion(toChunk.getX(), toChunk.getZ());
        Optional<Region> optionalFromRegion = database.getRegion(fromChunk.getX(), fromChunk.getZ());

        optionalFromRegion.ifPresent(fromRegion -> {
            if (optionalToRegion.isEmpty() || !optionalToRegion.get().equals(fromRegion)) {
                if (regionLeave(fromRegion, player, RegionLeaveEvent.RegionLeaveType.PORTAL)) {
                    event.setCancelled(true);
                }
            }
        });

        optionalToRegion.ifPresent(toRegion -> {
            if (regionEnter(player, RegionEnterEvent.RegionEnterType.PORTAL, toRegion)) {
                event.setCancelled(true);
            }
        });
    }

    private boolean regionLeave(Region region, Player player, RegionLeaveEvent.RegionLeaveType type) {
        RegionLeaveEvent event = new RegionLeaveEvent(region, player, type);
        Bukkit.getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            playersInsideRegionData.computeIfAbsent(region.getData(), k -> new ArrayList<>()).remove(player.getUniqueId());
        }

        return event.isCancelled();
    }

    private boolean regionEnter(Player player, RegionEnterEvent.RegionEnterType enterType, Region region) {
        RegionEnterEvent event = new RegionEnterEvent(region, player, enterType);
        Bukkit.getPluginManager().callEvent(event);
        if (!event.isCancelled()) {
            playersInsideRegionData.computeIfAbsent(region.getData(), k -> new ArrayList<>()).add(player.getUniqueId());
        }

        return event.isCancelled();
    }

}