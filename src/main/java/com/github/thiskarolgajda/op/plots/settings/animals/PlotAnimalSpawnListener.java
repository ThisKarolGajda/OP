package com.github.thiskarolgajda.op.plots.settings.animals;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.listeners.Listener;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PlotAnimalSpawnListener extends Listener {

    @EventHandler(ignoreCancelled = true)
    public void onCreatureSpawn(@NotNull CreatureSpawnEvent event) {
        if (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.NATURAL) {
            return;
        }

        Location location = event.getLocation();
        Optional<Plot> optional = Plugin.get(PlotDatabase.class).getPlot(location);
        if (optional.isEmpty()) {
            return;
        }

        Plot plot = optional.get();
        if (plot.getSettings().getAnimalSpawn().getDisabledSpawns().stream().anyMatch(animalType -> animalType.getType() == event.getEntity().getType())) {
            event.setCancelled(true);
        }
    }
}
