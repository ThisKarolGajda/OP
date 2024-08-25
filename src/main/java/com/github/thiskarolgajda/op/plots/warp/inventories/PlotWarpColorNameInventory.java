package com.github.thiskarolgajda.op.plots.warp.inventories;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import com.github.thiskarolgajda.op.plots.warp.PlotWarp;
import com.github.thiskarolgajda.op.plots.warp.PlotWarpNameColorType;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import me.opkarol.oplibrary.tools.Heads;
import me.opkarol.oplibrary.translations.Messages;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.NotNull;

import java.util.Map;


public class PlotWarpColorNameInventory extends ChestInventory {
    @Inject
    private static PlotDatabase database;

    public PlotWarpColorNameInventory(Player player, @NotNull Plot plot) {
        super(5, "plot_warp_color_name");

        setListPattern(player, () -> new PlotWarpManageInventory(player, plot));
        PlotWarp warp = plot.getWarp();

        setGlobalItem(item("remove"), 40, Heads.get("473fd8a06e6ea820794cda214fc46b4c329fa9af324e44eab4496c2d9f5ba6fd"), event -> {
            player.closeInventory();
            event.setCancelled(true);
            warp.setColor(null);
            database.save(plot);
            new PlotWarpColorNameInventory(player, plot);
        });

        setGlobalItem(item("display"), 41, Heads.get("7e13ef671e4bbd81358c8ffc8af864b651604f3c49362486441ee9d8cd127ae"), event -> event.setCancelled(true), Map.of("%name%", warp.getName()));

        for (PlotWarpNameColorType type : PlotWarpNameColorType.values()) {
            ItemBuilder builder = new ItemBuilder(type.getMaterial());
            if (warp.getColor() != null && warp.getColor() == type) {
                builder.setEnchants(Map.of(Enchantment.LUCK_OF_THE_SEA, 1));
                builder.setFlags(ItemFlag.HIDE_ENCHANTS);
            }

            setNextFree(item("name_color"), builder, event -> {
                event.setCancelled(true);
                player.closeInventory();
                Messages.sendMessage("plot.warp.changedColor", player);
                warp.setColor(type);
                database.save(plot);
                new PlotWarpColorNameInventory(player, plot);
            }, Map.of("%name%", type.getCode() + type.getName()));
        }

        open(player);
    }
}
