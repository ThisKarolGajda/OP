package com.github.thiskarolgajda.op.plots.blockcounter;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.listeners.Listener;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlotBlockCounterListener extends Listener {
    @Inject
    private static PlotDatabase database;

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBlockPlace(@NotNull BlockPlaceEvent event) {
        Material type = event.getBlockPlaced().getType();
        if (!PlotBlockCounterType.contains(type)) {
            return;
        }

        Optional<Plot> optional = database.getPlot(event.getBlockPlaced().getLocation());
        if (optional.isEmpty()) {
            return;
        }

        Plot plot = optional.get();
        PlotBlockCounter counter = plot.getBlockCounter();
        counter.increment(PlotBlockCounterType.getType(type));
        database.save(plot);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBlockBreak(@NotNull BlockBreakEvent event) {
        Material type = event.getBlock().getType();
        if (!PlotBlockCounterType.contains(type)) {
            return;
        }

        Optional<Plot> optional = database.getPlot(event.getBlock().getLocation());
        if (optional.isEmpty()) {
            return;
        }

        Plot plot = optional.get();
        PlotBlockCounter counter = plot.getBlockCounter();
        counter.decrement(PlotBlockCounterType.getType(type));
        database.save(plot);
    }
}
