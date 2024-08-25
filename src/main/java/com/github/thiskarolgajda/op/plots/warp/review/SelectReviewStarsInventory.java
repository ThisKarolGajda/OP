package com.github.thiskarolgajda.op.plots.warp.review;

import me.opkarol.oplibrary.inventories.ChestInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.function.Consumer;

public class SelectReviewStarsInventory extends ChestInventory {
    public SelectReviewStarsInventory(Player player, Consumer<Integer> onSelectedStarAmount) {
        super(3, "Wybierz ocenę warpu");

        setStar(onSelectedStarAmount, 11, 1);
        setStar(onSelectedStarAmount, 12, 2);
        setStar(onSelectedStarAmount, 13, 3);
        setStar(onSelectedStarAmount, 14, 4);
        setStar(onSelectedStarAmount, 15, 5);

        fillEmptyWithBlank();
        open(player);
    }

    private void setStar(Consumer<Integer> onSelectedStarAmount, int slot, int stars) {
        setItem(item("%stars%"), slot, getItemBasedOnStars(stars), event -> {
            event.setCancelled(true);
            onSelectedStarAmount.accept(stars);
            event.getWhoClicked().closeInventory();
        }, Map.of(
                "%stars%", String.valueOf(stars)
        ));
    }

    public static Material getItemBasedOnStars(int stars) {
        return switch (stars) {
            case 1 -> Material.RED_WOOL;
            case 2 -> Material.ORANGE_WOOL;
            case 3 -> Material.YELLOW_WOOL;
            case 4 -> Material.LIME_WOOL;
            case 5 -> Material.GREEN_WOOL;
            default -> Material.BLACK_WOOL;
        };
    }
}
