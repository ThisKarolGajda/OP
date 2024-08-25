package com.github.thiskarolgajda.op.plots.blocklimits;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.listeners.Listener;
import me.opkarol.oplibrary.translations.Messages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlotBlockLimitListener extends Listener {
    @Inject
    private static PlotDatabase database;

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBlockPlace(@NotNull BlockPlaceEvent event) {
        if (!PlotBlockLimitType.contains(event.getBlockPlaced().getType())) {
            return;
        }

        Optional<Plot> optional = database.getPlot(event.getBlockPlaced().getLocation());
        if (optional.isEmpty()) {
            return;
        }

        Plot plot = optional.get();
        PlotBlockLimits limits = plot.getBlockLimits();
        PlotBlockLimit limit = limits.get(event.getBlockPlaced().getType());
        if (limit.isLimitReached()) {
            event.setCancelled(true);
            Plugin.title(event.getPlayer(), Messages.getFormattedTranslation("plot.reachedBlockLimit.title"), Messages.getFormattedTranslation("plot.reachedBlockLimit.subtitle").replace("%type%", limit.getType().getName()).replace("%limit%", String.valueOf(limit.getLimit())), 5, 30, 5);
        } else {
            limit.increment();
            limits.set(limit);
            plot.setBlockLimits(limits);
            database.save(plot);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBlockBreak(@NotNull BlockBreakEvent event) {
        if (!PlotBlockLimitType.contains(event.getBlock().getType())) {
            return;
        }

        Optional<Plot> optional = database.getPlot(event.getBlock().getLocation());
        if (optional.isEmpty()) {
            return;
        }

        Plot plot = optional.get();
        PlotBlockLimits limits = plot.getBlockLimits();
        PlotBlockLimit limit = limits.get(event.getBlock().getType());
        limit.decrement();
        limits.set(limit);
        plot.setBlockLimits(limits);
        database.save(plot);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBlockFromTo(@NotNull BlockFromToEvent event) {
        if (!PlotBlockLimitType.contains(event.getBlock().getType())) {
            return;
        }

        Optional<Plot> optional = database.getPlot(event.getBlock().getLocation());
        if (optional.isEmpty()) {
            return;
        }

        event.setCancelled(true);
    }
}
