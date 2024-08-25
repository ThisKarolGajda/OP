package com.github.thiskarolgajda.op.plots.warp.inventories;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.misc.Books;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PlotWarpsInventory extends ChestInventory {

    public PlotWarpsInventory(Player player) {
        super(4, "Warpy działek");
        setListPattern(player);
        List<Plot> list = Plugin.get(PlotDatabase.class)
                .getAll().stream()
                .filter(Objects::nonNull)
                .filter(plot -> plot.getWarp() != null && plot.getWarp().isEnabled())
                .sorted(Comparator.comparingDouble((Plot plot) -> plot.getWarp().getAverageReviewStars()).reversed())
                .toList();

        list.forEach(plot -> setNextFree(item("plot"), Books.getRandomHead(plot.getName()), event -> {
            event.setCancelled(true);
            new PlotWarpInformationInventory(player, plot);
        }, Map.of(
                "%name%", plot.getWarp().getName(),
                "%visits%", String.valueOf(plot.getWarp().getVisits()),
                "%average_stars%", String.valueOf(plot.getWarp().getAverageReviewStars()),
                "%owner%", plot.getOwnerName()
        )));

        open(player);
    }
}
