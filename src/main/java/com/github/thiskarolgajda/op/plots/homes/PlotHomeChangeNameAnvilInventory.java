package com.github.thiskarolgajda.op.plots.homes;

import com.github.thiskarolgajda.op.utils.AbstractGetStringAnvilInventory;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Consumer;

public class PlotHomeChangeNameAnvilInventory extends AbstractGetStringAnvilInventory {
    public PlotHomeChangeNameAnvilInventory(Player player, Consumer<String> onNameChange) {
        super(player, onNameChange);
    }

    @Override
    public int getMaxNameLength() {
        return 32;
    }

    @Override
    public void sendMaxLengthMessage(Player player, int maxLength) {
        //sendMessage("homes.maxNameReached", player, Map.of("%max_chars%", String.valueOf(getMaxNameLength())));
    }

    @Override
    public String getTitle() {
        return "";
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
