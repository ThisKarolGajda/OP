package com.github.thiskarolgajda.op.plots.shopchests;

import com.github.thiskarolgajda.op.plots.Plot;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

public class PlotShopChestManageInventory extends ChestInventory {
    public PlotShopChestManageInventory(Player player, Plot plot, PlotShopChest shopChest) {
        super(3, "Sklep działki");

        setItemHome(22, player, () -> new PlotShopChestsInventory(player, plot));

        setItem(item("Sklep %name%", List.of("Lokalizacja: %location%", "Amount: %amount%", "Cost per purchase: %cost%", "Available: %available%")), 0, new ItemBuilder(), event -> {
            event.setCancelled(true);
        }, Map.of("%location%", shopChest.getLocation().toFamilyStringWithoutWorld(), "%name%", shopChest.getSelling().name(), "%amount%", String.valueOf(shopChest.getAmountPerPurchase()), "%cost%", String.valueOf(shopChest.getCostPerPurchase()), "%available%", String.valueOf(shopChest.getAvailable())));

        setItem(item("Usuń sklep"), 1, new ItemBuilder(Material.RED_WOOL), event -> {
            event.setCancelled(true);
            PlotShopChests chests = plot.getShopChests();
            chests.remove(shopChest);
            new PlotShopChestsInventory(player, plot);
        });

        fillEmptyWithBlank();
        open(player);
    }
}
