package com.github.thiskarolgajda.op.plots.shopchests;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.injection.messages.StringMessage;
import me.opkarol.oplibrary.listeners.Listener;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.Optional;

public class PlotShopChestsListener extends Listener {
    public static StringMessage cannotBreakShopChest = new StringMessage("Nie można usunąć sklepu działki!");

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        Location location = event.getBlock().getLocation();
        Optional<Plot> optional = Plugin.get(PlotDatabase.class).getPlot(location);
        if (optional.isEmpty()) {
            return;
        }

        Plot plot = optional.get();
        PlotShopChest chest = plot.getShopChests().getShopChest(location.getBlock());
        if (chest == null) {
            return;
        }

        event.setCancelled(true);
        cannotBreakShopChest.error(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onTNTExplode(EntityExplodeEvent event) {
        for (Block block : event.blockList()) {
            Location location = block.getLocation();
            Optional<Plot> optional = Plugin.get(PlotDatabase.class).getPlot(location);
            if (optional.isEmpty()) {
                return;
            }

            Plot plot = optional.get();
            PlotShopChest chest = plot.getShopChests().getShopChest(location.getBlock());
            if (chest == null) {
                return;
            }

            event.setCancelled(true);
        }
    }
}
