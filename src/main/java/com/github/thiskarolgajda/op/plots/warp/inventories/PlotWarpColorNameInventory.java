package com.github.thiskarolgajda.op.plots.warp.inventories;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import com.github.thiskarolgajda.op.plots.warp.PlotWarp;
import com.github.thiskarolgajda.op.plots.warp.PlotWarpNameColorType;
import com.github.thiskarolgajda.op.utils.HeadsType;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.injection.formatter.LoreBuilder;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import me.opkarol.oplibrary.tools.Heads;
import me.opkarol.oplibrary.translations.Messages;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;

import static com.github.thiskarolgajda.op.utils.RandomItemCollector.random;


public class PlotWarpColorNameInventory extends ChestInventory {
    @Inject
    private static PlotDatabase database;

    public PlotWarpColorNameInventory(Player player, @NotNull Plot plot) {
        super(5, "Wybierz kolor warpu");

        setListPattern(player, () -> new PlotWarpManageInventory(player, plot));
        PlotWarp warp = plot.getWarp();

        setGlobalItem(item("Wylosuj"), 38, HeadsType.GREY_HEAD.getHead(), event -> {
            event.setCancelled(true);
            warp.setColor(Arrays.stream(PlotWarpNameColorType.values()).collect(random()));
            database.save(plot);
            new PlotWarpColorNameInventory(player, plot);
        });

        setGlobalItem(item("Podgląd: %display%"), 42, Heads.get("7e13ef671e4bbd81358c8ffc8af864b651604f3c49362486441ee9d8cd127ae"), event -> event.setCancelled(true), Map.of("%display%", warp.getName()));

        for (PlotWarpNameColorType type : PlotWarpNameColorType.values()) {
            ItemBuilder builder = new ItemBuilder(type.getMaterial());
            if (warp.getColor() != null && warp.getColor() == type) {
                builder.setEnchants(Map.of(Enchantment.LUCK_OF_THE_SEA, 1));
                builder.setFlags(ItemFlag.HIDE_ENCHANTS);
            }

            setNextFree(item("Kolor %name%", LoreBuilder.create("Wygląd: " + type.getCode() + "Mój warpik").anyMouseButtonText("ustawić")), builder, event -> {
                event.setCancelled(true);
                Messages.sendMessage("plot.warp.changedColor", player);
                warp.setColor(type);
                database.save(plot);
                new PlotWarpColorNameInventory(player, plot);
            }, Map.of("%name%", type.getName()));
        }

        open(player);
    }
}
