package com.github.thiskarolgajda.op.region;

import com.github.thiskarolgajda.op.region.player.PlayerRegionRuleType;
import com.github.thiskarolgajda.op.region.rule.RegionRuleType;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.listeners.Listener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class RegionListener extends Listener {
    @Inject
    private static RegionDatabase regionDatabase;

    private void declineCancellableEvent(String key, Player player, Cancellable event, @Nullable Block block) {
        //Messages.sendMessage(key, player);
        player.sendMessage(key);
        event.setCancelled(true);
        if (block != null) {
            Plugin.particle(player, Particle.EXPLOSION, block.getLocation().add(0.5, 0, 0.5), 1);
        }
    }

    // Player listeners

    @EventHandler
    private void playerPlaceBlock(BlockPlaceEvent event) {
        Location location = event.getBlockPlaced().getLocation();
        Optional<Region> region = regionDatabase.getRegion(location);
        if (region.isEmpty()) {
            return;
        }

        if (region.get().can(event.getPlayer(), PlayerRegionRuleType.PLACE_BLOCK)) {
            return;
        }

        declineCancellableEvent("regions.cannotPlaceBlock", event.getPlayer(), event, event.getBlockPlaced());
    }

    @EventHandler
    private void playerBreakBlock(BlockBreakEvent event) {
        Location location = event.getBlock().getLocation();
        Optional<Region> region = regionDatabase.getRegion(location);
        if (region.isEmpty()) {
            return;
        }

        if (region.get().can(event.getPlayer(), PlayerRegionRuleType.BREAK_BLOCK)) {
            return;
        }

        declineCancellableEvent("regions.cannotBreakBlock", event.getPlayer(), event, event.getBlock());
    }

    @EventHandler
    private void playerInteract(PlayerInteractEvent event) {
        //TODO: add checks for individual block types: doors, buttons, levers, etc.
        //if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null) {
        //    return;
        //}
//
        //Location location = event.getClickedBlock().getLocation();
        //Optional<Region> region = getRegion(location);
        //if (region.isEmpty()) {
        //    return;
        //}
//
        //if (region.get().can(event.getPlayer(), RegionRuleType.INTERACT_BLOCK)) {
        //    return;
        //}
//
        //declineCancellableEvent("regions.cannotInteractBlock", event.getPlayer(), event, event.getClickedBlock());
    }

    @EventHandler
    private void playerFillBucket(PlayerBucketFillEvent event) {
        Location location = event.getBlockClicked().getLocation();
        Optional<Region> region = regionDatabase.getRegion(location);
        if (region.isEmpty()) {
            return;
        }

        if (region.get().can(event.getPlayer(), PlayerRegionRuleType.FILL_BUCKET)) {
            return;
        }

        declineCancellableEvent("regions.cannotFillBucket", event.getPlayer(), event, event.getBlockClicked());
    }

    @EventHandler
    private void playerEmptyBucket(PlayerBucketEmptyEvent event) {
        Location location = event.getBlockClicked().getLocation();
        Optional<Region> region = regionDatabase.getRegion(location);
        if (region.isEmpty()) {
            return;
        }

        if (region.get().can(event.getPlayer(), PlayerRegionRuleType.EMPTY_BUCKET)) {
            return;
        }

        declineCancellableEvent("regions.cannotEmptyBucket", event.getPlayer(), event, event.getBlockClicked());
    }

    @EventHandler
    private void playerFightPlayer(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player damaged) || !(event.getDamager() instanceof Player damager)) {
            return;
        }

        Optional<Region> region = regionDatabase.getRegion(damaged.getLocation());
        if (region.isEmpty()) {
            return;
        }

        if (region.get().can(damager, PlayerRegionRuleType.FIGHT_PLAYER)) {
            return;
        }

        declineCancellableEvent("regions.cannotFightPlayer", damager, event, null);
    }

    @EventHandler
    private void playerFightEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player || !(event.getDamager() instanceof Player player)) {
            return;
        }

        Optional<Region> region = regionDatabase.getRegion(event.getEntity().getLocation());
        if (region.isEmpty()) {
            return;
        }

        if (region.get().can(player, PlayerRegionRuleType.FIGHT_ENTITY)) {
            return;
        }

        declineCancellableEvent("regions.cannotFightEntity", player, event, null);
    }

    @EventHandler
    private void openChest(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null || !event.getClickedBlock().getType().name().toLowerCase().contains("chest")) {
            return;
        }

        Location location = event.getClickedBlock().getLocation();
        Optional<Region> region = regionDatabase.getRegion(location);
        if (region.isEmpty()) {
            return;
        }

        if (region.get().can(event.getPlayer(), PlayerRegionRuleType.OPEN_CHEST)) {
            return;
        }

        declineCancellableEvent("regions.cannotOpenChest", event.getPlayer(), event, event.getClickedBlock());
    }

    // Region listeners

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        Location location = event.getBlock().getLocation();
        Optional<Region> region = regionDatabase.getRegion(location);
        if (region.isEmpty()) {
            return;
        }

        if (region.get().can(RegionRuleType.CAN_FIRE_SPREAD)) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        Location location = event.getBlock().getLocation();
        Optional<Region> region = regionDatabase.getRegion(location);
        if (region.isEmpty()) {
            return;
        }

        if (region.get().can(RegionRuleType.CAN_FIRE_IGNITE)) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onFireSpreadBlock(BlockSpreadEvent event) {
        Location location = event.getBlock().getLocation();
        Optional<Region> region = regionDatabase.getRegion(location);
        if (region.isEmpty()) {
            return;
        }

        if (region.get().can(RegionRuleType.CAN_BLOCK_SPREAD)) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onDestroyBlockByWaterOrLava(BlockFromToEvent event) {
        Location location = event.getToBlock().getLocation();
        Optional<Region> region = regionDatabase.getRegion(location);
        if (region.isPresent()) {
            if (!region.get().can(RegionRuleType.CAN_LIQUID_SPREAD)) {
                event.setCancelled(true);
            }
        }

        location = event.getBlock().getLocation();
        region = regionDatabase.getRegion(location);
        if (region.isPresent()) {
            if (!region.get().can(RegionRuleType.CAN_LIQUID_SPREAD)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onTNTExplode(EntityExplodeEvent event) {
        List<Block> blocksWithoutRegion = new ArrayList<>();
        for (Block block : event.blockList()) {
            Location location = block.getLocation();
            Optional<Region> region = regionDatabase.getRegion(location);
            if (region.isEmpty()) {
                blocksWithoutRegion.add(block);
                continue;
            }

            if (region.get().can(RegionRuleType.CAN_TNT_EXPLODE)) {
                blocksWithoutRegion.add(block);
            }
        }

        if (event.blockList().size() != blocksWithoutRegion.size()) {
            event.setCancelled(true);
            Random random = new Random();
            for (Block block : blocksWithoutRegion) {
                Plugin.particle(Particle.EXPLOSION, block.getLocation(), 0);
                Plugin.sound(block.getLocation(), Sound.ENTITY_GENERIC_EXPLODE);

                if (block.getType().equals(Material.TNT)) {
                    block.setType(Material.AIR);
                    block.getWorld().spawnEntity(block.getLocation(), EntityType.TNT);
                    continue;
                }

                // Generate whether it should drop items or not
                if (random.nextFloat() > event.getYield()) {
                    block.breakNaturally();
                } else {
                    block.setType(Material.AIR);
                }
            }
        }
    }
}
