package com.github.thiskarolgajda.op.plots.inventories;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDeleter;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static me.opkarol.oplibrary.translations.Messages.sendMessage;

public class RemovePlot {

    public RemovePlot(Player player, Plot plot) {
        new PlotRemoveAnvilInventory(player, plot);
    }
}

class PlotRemoveAnvilInventory {
    public PlotRemoveAnvilInventory(Player player, @NotNull Plot plot) {
        new AnvilGUI.Builder().itemLeft(new ItemBuilder(Material.NAME_TAG).setName(" ")).itemRight(new ItemBuilder(Material.BARRIER).setName("&k")).itemOutput(new ItemBuilder(Material.NAME_TAG).setName("")).onClick((slot, state) -> {
            if (slot != AnvilGUI.Slot.OUTPUT) {
                return Collections.emptyList();
            }

            player.closeInventory();
            String name = state.getText();
            if (name.startsWith(" ")) {
                name = name.substring(1); // Remove space if exists at the start set by the item name
            }

            if (name.equals(plot.getName())) {
                PlotDeleter.deletePlot(plot);
                sendMessage("plot.remove.removed", player, Map.of("%plot%", plot.getName()));
            } else {
                sendMessage("plot.remove.wrongValueEnteredNotDeleted", player);
            }

            return List.of(AnvilGUI.ResponseAction.close());
        }).plugin(Plugin.getInstance()).title(Plugin.format("Wpisz: " + plot.getName() + ", aby usunąć")).open(player);
    }
}
