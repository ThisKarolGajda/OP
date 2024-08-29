package com.github.thiskarolgajda.op.plots.border;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import com.github.thiskarolgajda.op.plots.settings.PlotSettingsInventory;
import com.github.thiskarolgajda.op.utils.HeadsType;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.injection.formatter.LoreBuilder;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.misc.StringIconUtil;
import me.opkarol.oplibrary.translations.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Consumer;

public class BorderChangeInventory extends ChestInventory {
    @Inject
    private static PlotDatabase database;

    public BorderChangeInventory(Player player, @NotNull Plot plot) {
        super(3, "Zmień granicę działki");
        setItemHome(22, player, () -> new PlotSettingsInventory(player, plot));

        PlotBorder border = plot.getBorder();
        setItem(item("Wyświetlaj granice na działce", LoreBuilder.create("Wyświetlanie: %enabled%").anyMouseButtonText("przełączyć")), 10, HeadsType.BORDER.getHead(), event -> {
            event.setCancelled(true);
            border.setDisplayBorderInsidePlot(!border.isDisplayBorderInsidePlot());
            updatePlot(player, plot, border);
        }, Map.of("%enabled%", StringIconUtil.getReturnedEmojiFromBoolean(border.isDisplayBorderInsidePlot())));

        setColorItem(player, plot, border, 13, "właściciela", Material.BLUE_WOOL, border.getOwnerColorHex(), border::setOwnerColorHex);
        setColorItem(player, plot, border, 14, "członka", Material.GREEN_WOOL, border.getMemberColorHex(), border::setIgnoredColorHex);
        setColorItem(player, plot, border, 15, "gościa", Material.LIGHT_BLUE_WOOL, border.getNormalColorHex(), border::setNormalColorHex);
        setColorItem(player, plot, border, 16, "ignorowanego", Material.RED_WOOL, border.getIgnoredColorHex(), border::setIgnoredColorHex);

        fillEmptyWithBlank();
        open(player);
    }

    private void setColorItem(Player player, Plot plot, PlotBorder border, int slot, String name, Material material, String colorHex, Consumer<String> colorSetter) {
        setItem(item("Kolor %name%", LoreBuilder.create("Podgląd: #<" + colorHex + ">&m---------").anyMouseButtonText("zmienić")), slot, material, event -> {
            event.setCancelled(true);
            new SelectColor(player, color -> {
                colorSetter.accept(color);
                updatePlot(player, plot, border);
            }, () -> new BorderChangeInventory(player, plot));
        }, Map.of("%name%", name));
    }

    private void updatePlot(Player player, @NotNull Plot plot, PlotBorder border) {
        plot.setBorder(border);
        database.save(plot);
        Messages.sendMessage("plot.changedBorder", player);
        new BorderChangeInventory(player, plot);
    }
}