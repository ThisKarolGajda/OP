package com.github.thiskarolgajda.op.core.user.homes.inventories;

import com.github.thiskarolgajda.op.core.teleportation.TeleportationManager;
import com.github.thiskarolgajda.op.core.user.economy.MoneyTextFormatter;
import com.github.thiskarolgajda.op.core.user.homes.UserHome;
import com.github.thiskarolgajda.op.core.user.homes.UserHomes;
import com.github.thiskarolgajda.op.core.user.homes.UserHomesDatabase;
import com.github.thiskarolgajda.op.utils.HeadsType;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.extensions.Vault;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import me.opkarol.oplibrary.translations.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Map;

import static com.github.thiskarolgajda.op.core.user.homes.UserHomes.costPerHomeLimitUpgrade;
import static com.github.thiskarolgajda.op.core.user.homes.UserHomes.defaultHouseName;
import static me.opkarol.oplibrary.injection.messages.StringMessage.errorSound;

public class HomesInventory extends ChestInventory {

    public HomesInventory(Player player) {
        super(3, "homes");

//        setItem(item("plots"), 26, HeadsType.HOME.getHead(), event -> new ChoosePlot(player, (plot) -> new PlotHomesInventory(player, plot)));

        UserHomes homes = Plugin.get(UserHomesDatabase.class).getSafe(player.getUniqueId());

        for (int i = 0; i < 5; i++) {
            int slot = 11 + i;
            if (homes.getHomes().size() > i) {
                UserHome home = homes.getHomes().get(i);
                setItem(item("home"), slot, new ItemBuilder(home.getIcon()), event -> {
                    event.setCancelled(true);
                    if (event.isShiftClick()) {
                        return;
                    }

                    if (event.isLeftClick()) {
                        TeleportationManager.teleport(player, home);
                        player.closeInventory();
                        return;
                    }

                    new HomeManageInventory(player, home);
                }, Map.of(
                        "%name%", home.getName(),
                        "%location%", home.getLocation().toFamilyStringWithoutWorld()
                ));
            } else {
                if (homes.getHomesLimit() <= i) {
                    setItem(item("increase_limit"), slot, new ItemBuilder(Material.BARRIER), event -> {
                        event.setCancelled(true);
                        if (Vault.remove(player, costPerHomeLimitUpgrade) != Vault.VAULT_RETURN_INFO.WITHDRAW_SUCCESSFUL) {
                            Messages.sendMessage("notEnoughMoney", player, Map.of("%cost%", MoneyTextFormatter.format(costPerHomeLimitUpgrade)));
                            return;
                        }

                        homes.setHomesLimit(homes.getHomesLimit() + 1);
                        player.closeInventory();
                        Plugin.get(UserHomesDatabase.class).saveAsync(homes).thenRun(() -> new HomesInventory(player));
                    }, Map.of(
                            "%limit%", String.valueOf(homes.getHomesLimit()),
                            "%price%", MoneyTextFormatter.format(costPerHomeLimitUpgrade)
                    ));
                    continue;
                }

                setItem(item("empty"), slot, HeadsType.GREY_HEAD.getHead(), event -> {
                    event.setCancelled(true);
                    if (homes.getHomesLimit() <= homes.getHomes().size()) {
                        Messages.sendMessage("homes.reachedLimit", player);
                        errorSound.play(player);
                        return;
                    }

                    homes.addHome(player.getLocation(), defaultHouseName);
                    player.closeInventory();
                    Plugin.get(UserHomesDatabase.class).saveAsync(homes).thenRun(() -> new HomesInventory(player));
                });
            }
        }

        fillEmptyWithBlank();
        open(player);
    }
}
