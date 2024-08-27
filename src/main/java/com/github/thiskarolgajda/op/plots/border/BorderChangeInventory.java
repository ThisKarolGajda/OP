package com.github.thiskarolgajda.op.plots.border;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import com.github.thiskarolgajda.op.plots.settings.PlotSettingsInventory;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.misc.StringIconUtil;
import me.opkarol.oplibrary.tools.Heads;
import me.opkarol.oplibrary.translations.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class BorderChangeInventory extends ChestInventory {
    @Inject
    private static PlotDatabase database;

    public BorderChangeInventory(Player player, @NotNull Plot plot) {
        super(3, "Zmień granicę działki");
        setItemHome(22, player, () -> new PlotSettingsInventory(player, plot));

        PlotBorder border = plot.getBorder();
        setItem(item("Wyświetlaj granice na działce"), 10, Heads.get("7c373b60c4804e8f851ba8829bc0250f2db03d5d9e9a010cc03a2d255ad7fc15"), event -> {
            event.setCancelled(true);
            border.setDisplayBorderInsidePlot(!border.isDisplayBorderInsidePlot());
            plot.setBorder(border);
            database.save(plot);
            Messages.sendMessage("plot.changedBorder", player);
            new BorderChangeInventory(player, plot);
        }, Map.of(
                "%enabled%", StringIconUtil.getReturnedEmojiFromBoolean(border.isDisplayBorderInsidePlot())
        ));

        setItem(item("Kolor %name% (%color%)"), 13, Material.BLUE_WOOL, event -> {
            event.setCancelled(true);
            new SelectColor(player, color -> {
                border.setOwnerColorHex(color);
                plot.setBorder(border);
                database.save(plot);
                Messages.sendMessage("plot.changedBorder", player);
                new BorderChangeInventory(player, plot);
            });
        }, Map.of(
                "%name%", "właściciela",
                "%color%", "#<" + border.getOwnerColorHex() + ">&m---------"
        ));

        setItem(item("Kolor %name% (%color%)"), 14, Material.GREEN_WOOL, event -> {
            event.setCancelled(true);
            new SelectColor(player, color -> {
                border.setIgnoredColorHex(color);
                plot.setBorder(border);
                database.save(plot);
                Messages.sendMessage("plot.changedBorder", player);
                new BorderChangeInventory(player, plot);
            });
        }, Map.of(
                "%name%", "członka",
                "%color%", "#<" + border.getMemberColorHex() + ">&m---------"
        ));

        setItem(item("Kolor %name% (%color%)"), 15, Material.LIGHT_BLUE_WOOL, event -> {
            event.setCancelled(true);
            new SelectColor(player, color -> {
                border.setNormalColorHex(color);
                plot.setBorder(border);
                database.save(plot);
                Messages.sendMessage("plot.changedBorder", player);
                new BorderChangeInventory(player, plot);
            });
        }, Map.of(
                "%name%", "",
                "%color%", "#<" + border.getNormalColorHex() + ">&m---------"
        ));

        setItem(item("Kolor %name% (%color%)"), 16, Material.RED_WOOL, event -> {
            event.setCancelled(true);
            new SelectColor(player, color -> {
                border.setIgnoredColorHex(color);
                plot.setBorder(border);
                database.save(plot);
                Messages.sendMessage("plot.changedBorder", player);
                new BorderChangeInventory(player, plot);
            });
        }, Map.of(
                "%name%", "ignorowanego",
                "%color%", "#<" + border.getIgnoredColorHex() + ">&m---------"
        ));

        fillEmptyWithBlank();
        open(player);
    }
}
    