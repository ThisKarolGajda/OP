package com.github.thiskarolgajda.op.utils;

import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public abstract class AbstractGetStringAnvilInventory {

    public AbstractGetStringAnvilInventory(Player player, Consumer<String> onNameChange) {
        new AnvilGUI.Builder()
                .itemLeft(new ItemBuilder(Material.NAME_TAG).setName("Wprowadź wartość"))
                .itemRight(new ItemBuilder(Material.BARRIER).setName("&k"))
                .itemOutput(new ItemBuilder(Material.NAME_TAG)
                        .setName(getItemName())
                        .setLore(getItemLore()))
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
                    if (getMaxNameLength() != -1 && length > getMaxNameLength()) {
                        sendMaxLengthMessage(player, getMaxNameLength());
                        return Collections.emptyList();
                    }

                    if (getMinNameLength() != -1 && length < getMinNameLength()) {
                        sendMinLengthMessage(player, getMinNameLength());
                        return Collections.emptyList();
                    }

                    if (isNameIllegal(name)) {
                        sendIllegalNameMessage(player, name);
                        return Collections.emptyList();
                    }

                    onNameChange.accept(name);
                    return List.of(AnvilGUI.ResponseAction.close());
                })
                .title(Plugin.format(getTitle()))
                .plugin(Plugin.getInstance())
                .open(player);
    }

    public int getMaxNameLength() {
        return -1;
    }

    public int getMinNameLength() {
        return -1;
    }

    public boolean isNameIllegal(String name) {
        return name == null;
    }

    public void sendMaxLengthMessage(Player player, int maxLength) {

    }

    public void sendMinLengthMessage(Player player, int minLength) {

    }

    public void sendIllegalNameMessage(Player player, String name) {

    }

    public abstract String getTitle();

    public abstract String getItemName();

    public abstract List<String> getItemLore();
}