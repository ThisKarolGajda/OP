package com.github.thiskarolgajda.op.core.teleportation;

import com.github.thiskarolgajda.op.core.user.homes.UserHome;
import com.github.thiskarolgajda.op.core.warps.Warp;
import com.github.thiskarolgajda.op.permission.PermissionType;
import com.github.thiskarolgajda.op.plots.homes.PlotHome;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.injection.messages.StringMessage;
import me.opkarol.oplibrary.listeners.Listener;
import me.opkarol.oplibrary.location.OpLocation;
import me.opkarol.oplibrary.runnable.OpRunnable;
import me.opkarol.oplibrary.wrappers.OpParticle;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.thiskarolgajda.op.OP.playerNotOnline;

public class TeleportationManager extends Listener {
    private static final Map<UUID, OpRunnable> playersWithActiveTeleportCooldownThatDidNotMove = new HashMap<>();
    private static final Map<UUID, UUID> latestTpaRequests = new HashMap<>();
    private static final int COOLDOWN_IN_SECONDS = 3;

    public static StringMessage alreadyTeleporting = new StringMessage("Już się teleportujesz!");
    public static StringMessage notSafeTeleportLocation = new StringMessage("Miejsce do którego chcesz się przetelportować jest zbyt niebezpieczne!");
    public static StringMessage teleported = new StringMessage("Przeteleportowano!");
    public static StringMessage stoppedTeleportation = new StringMessage("Przerwano teleportację!");
    public static StringMessage noTeleportRequest = new StringMessage("Nie masz żadnego oczekiwania na teleportację!");
    public static StringMessage cannotTeleportToItself = new StringMessage("Nie możesz przeteleportować się do siebie!");
    public static StringMessage teleportRequestSent = new StringMessage("Wysłano żądanie o teleportację!");
    public static StringMessage teleportRequestReceived = StringMessage.arg("Otrzymano żądanie o teleportację od: %name%.", object -> Map.of("%name%", object.toString()));

    public static void setLastTpaRequest(UUID player, UUID target) {
        latestTpaRequests.put(target, player);
    }

    public static void teleport(Player player, @NotNull Warp warp) {
        teleport(player, warp.getLocation().getLocation(), canSkipCooldown(player), warp.getName());
    }

    public static void teleport(Player player, Location location) {
        teleport(player, location, canSkipCooldown(player), null);
    }

    public static void teleport(Player player, Location location, String teleportationName) {
        teleport(player, location, canSkipCooldown(player), teleportationName);
    }

    private static boolean canSkipCooldown(@NotNull Player player) {
        return PermissionType.ADMIN.hasPermission(player);
    }

    public static void teleport(@NotNull Player player, Location location, boolean skipCooldown, @Nullable String teleportationName) {
        if (playersWithActiveTeleportCooldownThatDidNotMove.containsKey(player.getUniqueId())) {
            alreadyTeleporting.error(player);
            return;
        }

        if (skipCooldown) {
            teleportToLocation(player, location);
            return;
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * (COOLDOWN_IN_SECONDS + 1), 1, false, false));
        AtomicInteger progress = new AtomicInteger(0);
        new OpRunnable((r) -> {
            if (playersWithActiveTeleportCooldownThatDidNotMove.get(player.getUniqueId()) == null) {
                r.cancelTask();
                return;
            }

            StringBuilder progressString = new StringBuilder();
            if (progress.get() > 0) {
                progressString.append("#!<f0e116>");
            }

            for (int i = 0; i < COOLDOWN_IN_SECONDS * 2; i++) {
                if (progress.get() == 0) {
                    progressString.append("#<f0e116>█#<FFA751>█#<8D8D8D>█████");
                    break;
                }

                if (i == progress.get()) {
                    progressString.append("█#!<FFA751>");
                }
                if (i > progress.get()) {
                    progressString.append("#<8D8D8D>█");
                } else {
                    if (progress.get() == 0) {
                        progressString.append("#<8D8D8D>");
                    }

                    progressString.append("█");
                }
            }

            if (progress.get() > 0 && !progressString.toString().contains("#!<FFA751>")) {
                progressString.append("#!<FFA751>");
            }

            progress.getAndIncrement();
            Plugin.sound(player, Sound.UI_BUTTON_CLICK);
            Plugin.title(player, progressString.toString(), "&l#<FFA751>│ &7Teleportowanie do: #<f0e116>" + (teleportationName != null ? teleportationName : new OpLocation(location).toFamilyStringWithoutWorld()) + "&l#<FFA751>│", 0, 11, 0);
        }).runTaskTimerAsynchronously(0, 10);

        OpRunnable runnable = Plugin.runLater(() -> {
            playersWithActiveTeleportCooldownThatDidNotMove.remove(player.getUniqueId());
            teleportToLocation(player, location);
            player.removePotionEffect(PotionEffectType.BLINDNESS);
        }, 20 * COOLDOWN_IN_SECONDS);
        playersWithActiveTeleportCooldownThatDidNotMove.put(player.getUniqueId(), runnable);
    }

    public static void teleportToLocation(Player player, Location location) {
        loadChunkAt(location);

        World world = location.getWorld();
        if (world == null) {
            return;
        }

        RayTraceResult result = world.rayTrace(
                location,
                new Vector(0, -1, 0),
                400,
                FluidCollisionMode.NEVER,
                true,
                1,
                (entity) -> false
        );

        if (result == null) {
            return;
        }

        Block targetBlock = result.getHitBlock();

        if (targetBlock != null) {
            location.setY(targetBlock.getLocation().add(0, 1, 0).getY());
        }

        if (!isSafeLocation(location)) {
            notSafeTeleportLocation.error(player);
            return;
        }

        Plugin.runLater(() -> {
            teleported.success(player);
            player.teleport(location);
            new OpParticle(Particle.EXPLOSION_EMITTER).display(player);
        }, 1);
    }

    private static void loadChunkAt(Location location) {
        Chunk chunk = location.getChunk();
        if (!chunk.isLoaded()) {
            chunk.load();
        }
    }

    /**
     * Checks if a location is safe (solid ground with 2 breathable blocks)
     *
     * @param location Location to check
     * @return True if location is safe
     */
    public static boolean isSafeLocation(Location location) {
        Block feet = location.getBlock();
        if (!feet.getType().isTransparent() && !feet.getLocation().add(0, 1, 0).getBlock().getType().isTransparent()) {
            return false;
        }

        Block head = feet.getRelative(BlockFace.UP);
        if (!head.getType().isTransparent()) {
            return false;
        }

        Block ground = feet.getRelative(BlockFace.DOWN);
        return ground.getType().isSolid();
    }

    public static void acceptTpaRequest(@NotNull Player player) {
        UUID target = latestTpaRequests.remove(player.getUniqueId());
        if (target == null) {
            noTeleportRequest.error(player);
            return;
        }

        Player targetPlayer = Bukkit.getPlayer(target);
        if (targetPlayer == null) {
            playerNotOnline.error(player);
            return;
        }

        teleport(targetPlayer, player.getLocation());
    }

    public static boolean denyTpaRequest(@NotNull Player player) {
        return latestTpaRequests.remove(player.getUniqueId()) != null;
    }

//    public static void teleportToPlotWarp(Player player, @NotNull Plot plot) {
//        teleport(player, plot.getWarp().getLocation().getLocation(), plot.getWarp().getName());
//    }
//
//    public static void teleport(Player player, @NotNull Plot plot) {
//        teleport(player, plot.getStartHomeLocation().getLocation(), plot.getName());
//    }

    public static void teleport(Player player, @NotNull PlotHome home) {
        teleport(player, home.getLocation().getLocation(), home.getName());
    }

    public static void teleport(Player player, @NotNull UserHome home) {
        teleport(player, home.getLocation().getLocation(), home.getName());
    }

//    public static void teleport(Player player, @NotNull PlotWarp warp) {
//        teleport(player, warp.getLocation().getLocation(), warp.getName());
//    }

    @EventHandler
    public void onPlayerMove(@NotNull PlayerMoveEvent event) {
        if (event.getTo() == null || event.getFrom().getBlock().equals(event.getTo().getBlock())) {
            return;
        }

        Player player = event.getPlayer();
        OpRunnable runnable = playersWithActiveTeleportCooldownThatDidNotMove.get(player.getUniqueId());
        if (runnable != null) {
            Plugin.particle(player, Particle.EXPLOSION, player.getLocation());
            player.removePotionEffect(PotionEffectType.BLINDNESS);
            stoppedTeleportation.error(player);
            runnable.cancelTask();
            playersWithActiveTeleportCooldownThatDidNotMove.remove(player.getUniqueId());
        }
    }
}
