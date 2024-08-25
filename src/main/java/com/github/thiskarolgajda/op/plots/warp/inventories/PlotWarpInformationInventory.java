package com.github.thiskarolgajda.op.plots.warp.inventories;

import com.github.thiskarolgajda.op.core.teleportation.TeleportationManager;
import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.warp.review.WarpReviewsInventory;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.tools.Heads;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class PlotWarpInformationInventory extends ChestInventory {

    public PlotWarpInformationInventory(Player player, @NotNull Plot plot) {
        super(3, "plot_warp_information");

        if (plot.getWarp() == null) {
            return;
        }

        setItemHome(26, player, () -> new PlotWarpsInventory(player));

        setItem(item("teleport"), 11, Heads.get("c3a8e402dad1b7dad9aae6f4015932183429ce87bbbeced3119026f8296336c2"), event -> {
            event.setCancelled(true);
            TeleportationManager.teleport(player, plot.getWarp());
        });

        setItem(item("reviews"), 13, Heads.get("6c0a3d93e43b323d27732309c11b3de92a6e05c16d72aa951aa116ee0a5cfda"), event -> {
            event.setCancelled(true);
            new WarpReviewsInventory(player, plot);
        });

        setItem(item("information"), 15, Heads.get("d01afe973c5482fdc71e6aa10698833c79c437f21308ea9a1a095746ec274a0f"), event -> event.setCancelled(true), Map.of(
                "%warp%", plot.getWarp().getName(),
                "%average_stars%", String.valueOf(plot.getWarp().getAverageReviewStars()),
                "%visits%", String.valueOf(plot.getWarp().getVisits()),
                "%name%", plot.getOwnerName()
        ));

        fillEmptyWithBlank();
        open(player);
    }
}
