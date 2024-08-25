package com.github.thiskarolgajda.op.plots.inventories;


import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.utils.AbstractGetStringAnvilInventory;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.github.thiskarolgajda.op.plots.config.PlotConfig.plotNameMaxLength;
import static com.github.thiskarolgajda.op.plots.config.PlotConfig.plotNameMinLength;
import static me.opkarol.oplibrary.translations.Messages.sendMessage;


public class PlotChangeName {

    public PlotChangeName(Player player, Plot plot) {
        new PlotChangeNameAnvilInventory(player, (string) -> {
            plot.setName(string);
            sendMessage("plot.name.changed", player, Map.of("%plot%", string));
        });
    }
}

class PlotChangeNameAnvilInventory extends AbstractGetStringAnvilInventory {

    public PlotChangeNameAnvilInventory(Player player, Consumer<String> onNameChange) {
        super(player, onNameChange);
    }

    @Override
    public int getMaxNameLength() {
        return plotNameMaxLength;
    }

    @Override
    public int getMinNameLength() {
        return plotNameMinLength;
    }

    @Override
    public void sendMaxLengthMessage(Player player, int maxLength) {
        sendMessage("plot.name.maxNameReached", player, Map.of("%max_chars%", String.valueOf(plotNameMaxLength)));
    }

    @Override
    public void sendMinLengthMessage(Player player, int minLength) {
        sendMessage("plot.name.minNameNotReached", player, Map.of("%min_chars%", String.valueOf(plotNameMinLength)));
    }

    @Override
    public void sendIllegalNameMessage(Player player, String name) {
        sendMessage("plot.name.illegalName", player, Map.of("%plot%", name));
    }

    @Override
    public String getTitle() {
        return "Zmień nazwę działki";
    }

    @Override
    public String getItemName() {
        return "";
    }

    @Override
    public List<String> getItemLore() {
        return List.of();
    }
}
