package com.github.thiskarolgajda.op.plots.hologram;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.config.PlotConfig;
import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PlotHologramsManager {
    private static Boolean isHologramEnabled;

    public static void display(@NotNull Plot plot) {
        if (isHologramEnabled == null) {
            isHologramEnabled = Bukkit.getPluginManager().getPlugin("DecentHolograms") != null;
        }

        if (!isHologramEnabled) {
            return;
        }

//        String regionIdentifier = plot.getRegionIdentifier();
//        if (DHAPI.getHologram(regionIdentifier) != null) {
//            DHAPI.removeHologram(regionIdentifier);
//        }
//
//        if (!plot.getHologram().isDisplayHologram()) {
//            return;
//        }
//
//        Hologram hologram = DHAPI.createHologram(regionIdentifier, plot.getStartHomeLocation().getLocation().add(0, 3, 0));
//        List<String> temp = getLines(plot);
//        DHAPI.setHologramLines(hologram, Plugin.format(temp));
    }

    private static @NotNull List<String> getLines(@NotNull Plot plot) {
        List<String> temp = new ArrayList<>(PlotConfig.plotHologramLines);
        temp.replaceAll(s -> s
                        .replace("%name%", plot.getName())
//                        .replace("%owner%", plot.getOwnerName())
//                .replace("%average_review%", String.valueOf(plot.getWarp().getAverageReviewStars()))
//                .replace("%visits%", String.valueOf(plot.getWarp().getVisitsNumber()))
//                        .replace("%location%", plot.getStartHomeFamilyLocation())
//                        .replace("%members%", plot.getMembersNames())
        );
        return temp;
    }

    public static void removeIfExists(String id) {
        if (isHologramEnabled == null) {
            isHologramEnabled = Bukkit.getPluginManager().getPlugin("DecentHolograms") != null;
        }

        if (!isHologramEnabled) {
            return;
        }

        if (DHAPI.getHologram(id) != null) {
            DHAPI.removeHologram(id);
        }
    }
}
