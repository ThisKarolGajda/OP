package com.github.thiskarolgajda.op.plots.warp.inventories;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import com.github.thiskarolgajda.op.plots.warp.PlotWarp;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.injection.messages.StringMessage;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import me.opkarol.oplibrary.tools.FormatTool;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.github.thiskarolgajda.op.plots.config.PlotConfig.plotNameMaxLength;
import static com.github.thiskarolgajda.op.plots.config.PlotConfig.plotNameMinLength;

public class PlotWarpChangeNameAnvilInventory {

    public static StringMessage plotWarpNameMin = StringMessage.arg("Minimalna długość nazwy warpu działki to %min%.", object -> Map.of("%min%", object.toString()));
    public static StringMessage plotWarpNameMax = StringMessage.arg("Maksymalna długość nazwy warpu działki to %max%.", object -> Map.of("%max%", object.toString()));
    public static StringMessage illegalPlotWarpName = new StringMessage("Podano nielegalną nazwę warpu działki!");
    public static StringMessage changedPlotWarpName = StringMessage.arg("Zmieniono nazwę warpu działki na %name%.", object -> Map.of("%name%", object.toString()));

    public PlotWarpChangeNameAnvilInventory(Player player, Plot plot) {
        open(player, plot);
    }

    private void open(Player player, Plot plot) {
        new AnvilGUI.Builder()
                .itemLeft(new ItemBuilder(Material.NAME_TAG).setName(" "))
                .itemRight(new ItemBuilder(Material.BARRIER).setName("&k"))
                .itemOutput(new ItemBuilder(Material.NAME_TAG).setName("Naciśnij, aby zmienić"))
                .onClick((slot, state) -> {
                    if (slot != AnvilGUI.Slot.OUTPUT) {
                        return Collections.emptyList();
                    }

                    player.closeInventory();
                    String name = state.getText();

                    if (name.startsWith(" ") && !name.startsWith("  ") && name.length() > 1) {
                        name = name.substring(1);
                    }

                    int length = name.length();
                    if (length > plotNameMaxLength) {
                        plotWarpNameMax.error(player, plotNameMaxLength);
                        return Collections.emptyList();
                    }

                    if (length < plotNameMinLength) {
                        plotWarpNameMin.error(player, plotNameMinLength);
                        return Collections.emptyList();
                    }

                    if (plot.isNameIllegal(name)) {
                        illegalPlotWarpName.error(player);
                        return Collections.emptyList();
                    }

                    name = ChatColor.stripColor(FormatTool.formatMessage(name));
                    PlotWarp warp = plot.getWarp();
                    if (warp != null) {
                        warp.setName(name);
                        Plugin.get(PlotDatabase.class).save(plot);
                    }

                    changedPlotWarpName.success(player, name);
                    return List.of(AnvilGUI.ResponseAction.close());
                })
                .title("Zmień nazwę warpu działki")
                .plugin(Plugin.getInstance())
                .open(player);
    }
}
