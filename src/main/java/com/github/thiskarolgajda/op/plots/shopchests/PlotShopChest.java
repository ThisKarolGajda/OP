package com.github.thiskarolgajda.op.plots.shopchests;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.opkarol.oplibrary.location.OpLocation;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
@Data
public class PlotShopChest {
    private final OpLocation location;
    private final ItemStack selling;
    private final int amountPerPurchase;
    private final int costPerPurchase;
    private int available;
}
