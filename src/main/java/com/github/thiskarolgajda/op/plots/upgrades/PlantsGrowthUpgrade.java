package com.github.thiskarolgajda.op.plots.upgrades;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.listeners.Listener;
import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockGrowEvent;

import java.util.Optional;

public class PlantsGrowthUpgrade extends Listener {
    @Inject
    private static PlotDatabase database;

    @EventHandler
    public void onPlantGrow(BlockGrowEvent event) {
        BlockState blockState = event.getNewState();
        if (blockState.getBlockData() instanceof Ageable ageable) {
            if (ageable.getAge() == ageable.getMaximumAge()) {
                return;
            }

            Location location = blockState.getLocation();
            Optional<Plot> optional = database.getPlot(location);
            if (optional.isEmpty()) {
                return;
            }

            Plot plot = optional.get();
            int newAge = ageable.getAge() + 1;
            int level = plot.getUpgrades().getLevel(PlotUpgradeType.PLANTS_GROWTH);

            int maxLevel = PlotUpgradeType.PLANTS_GROWTH.getLevelLimit();
            double chance = (double) level / maxLevel;
            if (Math.random() < chance) {
                newAge++;
            }

            if (newAge > ageable.getMaximumAge()) {
                newAge = ageable.getMaximumAge();
            }

            ageable.setAge(newAge);
            blockState.setBlockData(ageable);
            Plugin.run(() -> blockState.update(true));
        }
    }
}
