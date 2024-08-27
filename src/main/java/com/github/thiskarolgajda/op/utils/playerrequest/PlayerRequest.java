package com.github.thiskarolgajda.op.utils.playerrequest;

import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import me.opkarol.oplibrary.tools.Heads;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class PlayerRequest {
    public PlayerRequest(Player player, Consumer<OfflinePlayer> playerConsumer, Runnable onHomeAction, boolean excludeItself) {
        new PlayerRequestAnvilInventory(player, playerConsumer, onHomeAction, excludeItself);
    }

    public PlayerRequest(Player player, Consumer<OfflinePlayer> playerConsumer) {
        this(player, playerConsumer, null, true);
    }

    public PlayerRequest(Player player, Consumer<OfflinePlayer> playerConsumer, Runnable onHomeAction) {
        this(player, playerConsumer, onHomeAction, true);
    }

}

class PlayerRequestAnvilInventory {

    public PlayerRequestAnvilInventory(Player player, Consumer<OfflinePlayer> playerConsumer, Runnable onHomeAction, boolean excludeItself) {
        new AnvilGUI.Builder()
                .title("Podaj nazwę gracza")
                .itemLeft(new ItemBuilder(Material.NAME_TAG).setName(" "))
                .itemRight(new ItemBuilder(Material.BARRIER).setName("&k"))
                .itemOutput(new ItemBuilder(Material.NAME_TAG).setName("Gracz"))
                .onClick((slot, state) -> {
                    if (slot != AnvilGUI.Slot.OUTPUT) {
                        return Collections.emptyList();
                    }

                    new ChoosePlayerInventory(state.getText().replace(" ", ""), player, playerConsumer, onHomeAction, excludeItself);
                    return List.of(AnvilGUI.ResponseAction.close());
                })
                .plugin(Plugin.getInstance())
                .open(player);
    }
}

class ChoosePlayerInventory extends ChestInventory {

    public ChoosePlayerInventory(String name, @NotNull Player player, Consumer<OfflinePlayer> action, Runnable onHomeAction, boolean excludeItself) {
        super(5, "Wybierz gracza");
        setListPattern(player, onHomeAction);
        PlayerNameMatcher.getSortedPlayersByName(name, excludeItself ? player.getName() : "").forEach(
                offlinePlayer -> {
                    ItemStack item;
                    if (offlinePlayer.getPlayer() == null) {
                        item = new ItemStack(Material.RED_WOOL);
                    } else {
                        item = Heads.get(offlinePlayer);
                    }

                    setNextFree(item("%name%"), item, event -> {
                        event.setCancelled(true);
                        player.closeInventory();
                        action.accept(offlinePlayer);
                    }, Map.of(
                            "%name%", Objects.requireNonNull(offlinePlayer.getName()),
                            "%last_online%", PlayerLastPlayed.getPlayerLastPlayed(offlinePlayer)
                    ));
                }
        );

        open(player);
    }
}
