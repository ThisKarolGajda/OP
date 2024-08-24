package com.github.thiskarolgajda.op.plots.upgrades;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.listeners.Listener;
import org.bukkit.entity.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.Optional;

public class AnimalsGrowthUpgrade extends Listener {
    @Inject
    private static PlotDatabase database;

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof Ageable ageable)) {
            return;
        }

        if (ageable.isAdult()) {
            return;
        }

        Optional<Plot> optional = database.getPlot(event.getLocation());
        if (optional.isEmpty()) {
            return;
        }

        Plot plot = optional.get();
        Plugin.run(() -> {
            int newAge = ageable.getAge() + plot.getUpgrades().getLevel(PlotUpgradeType.ANIMALS_GROWTH) * 2400;
            ageable.setAge(newAge);
        });
    }
}
