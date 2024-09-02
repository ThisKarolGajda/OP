package com.github.thiskarolgajda.op.plots.shopchests;

import lombok.Data;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlotShopChests {
    private List<PlotShopChest> shopChests = new ArrayList<>();

    public boolean containsShopChest(@NotNull Block block) {
        return getShopChest(block) != null;
    }

    public PlotShopChest getShopChest(@NotNull Block block) {
        Location location = block.getLocation();
        for (PlotShopChest shopChest : shopChests) {
            if (location.getBlockX() == shopChest.getLocation().getBlockX() && location.getBlockY() == shopChest.getLocation().getBlockY() && location.getBlockZ() == shopChest.getLocation().getBlockZ()) {
                return shopChest;
            }
        }

        return null;
    }

    public void remove(PlotShopChest shopChest) {
        shopChests.removeIf(shopChest1 -> shopChest1.getLocation().equals(shopChest.getLocation()));
    }
}
