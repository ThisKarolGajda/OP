package com.github.thiskarolgajda.op.plots.shopchests;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import com.github.thiskarolgajda.op.plots.inventories.PlotMainInventory;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.injection.messages.StringMessage;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import me.opkarol.oplibrary.location.OpLocation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class PlotShopChestsInventory extends ChestInventory {

    public static StringMessage invalidPlotShopChestBlock = new StringMessage("Nieprawidłowa lokalizacja bloku sklepu! Musisz patrzeć na baryłke.");
    public static StringMessage givenLocationHasAlreadyChestBlock = new StringMessage("Podana lokalizacja już ma sklep działki!");

    public PlotShopChestsInventory(Player player, @NotNull Plot plot) {
        super(5, "Skrzynki sklepu na działce");

        setListPattern(player, () -> new PlotMainInventory(plot, player));

        PlotShopChests chests = plot.getShopChests();
        for (PlotShopChest shopChest : chests.getShopChests()) {
            setNextFree(item("Sklep %name%"), new ItemBuilder(), event -> {
                event.setCancelled(true);
                new PlotShopChestManageInventory(player, plot, shopChest);
            }, Map.of("%name%", shopChest.getSelling().name()));
        }

        setNextFree(item("Dodaj sklep"), new ItemBuilder(Material.GREEN_WOOL), event -> {
            event.setCancelled(true);
            Block block = player.getTargetBlockExact(7);
            if (block == null || block.getType() != Material.BARREL) {
                invalidPlotShopChestBlock.error(player);
                return;
            }

            if (chests.containsShopChest(block)) {
                givenLocationHasAlreadyChestBlock.error(player);
                return;
            }

            Location location = block.getLocation();
            PlotShopChest chest = new PlotShopChest(new OpLocation(location), Material.CHEST, 1, 1, 1);
            List<PlotShopChest> list = chests.getShopChests();
            list.add(chest);
            chests.setShopChests(list);
            Plugin.get(PlotDatabase.class).save(plot);
            new PlotShopChestsInventory(player, plot);
        });

        open(player);
    }
}
