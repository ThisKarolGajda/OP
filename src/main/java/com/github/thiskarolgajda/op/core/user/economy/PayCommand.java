package com.github.thiskarolgajda.op.core.user.economy;

import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.commands.annotations.Command;
import me.opkarol.oplibrary.commands.annotations.Default;
import me.opkarol.oplibrary.misc.StringUtil;
import me.opkarol.oplibrary.translations.Messages;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Map;

@Command("przelej")
public class PayCommand {

    static void pay(Player player, OfflinePlayer target, String amountString) {
        if (player.getUniqueId().equals(target.getUniqueId())) {
            Messages.sendMessage("economy.cantPayItself", player);
            return;
        }

        if (Plugin.get(UserEconomyDatabase.class).get(target.getUniqueId()).isEmpty()) {
            Messages.sendMessage("economy.playerNotExists", player);
            return;
        }

        double amount = StringUtil.getDoubleFromString(amountString);
        if (amount <= 0) {
            Messages.sendMessage("economy.amountTooLow", player);
            return;
        }

        UserEconomy playerEconomy = Plugin.get(UserEconomyDatabase.class).getSafe(player.getUniqueId());
        if (!playerEconomy.hasGold(amount)) {
            Messages.sendMessage("economy.youDoNotHaveEnoughMoney", player);
            return;
        }

        playerEconomy.remove(amount);
        Plugin.get(UserEconomyDatabase.class).save(playerEconomy);
        Messages.sendMessage("economy.paid", target.getPlayer(), Map.of("%money%", MoneyTextFormatter.format(amount), "%player%", target.getName() == null ? "" : target.getName()));

        UserEconomy targetEconomy = Plugin.get(UserEconomyDatabase.class).getSafe(target.getUniqueId());
        targetEconomy.add(amount);
        Plugin.get(UserEconomyDatabase.class).save(targetEconomy);
        if (target.getPlayer() != null) {
            Messages.sendMessage("economy.received", target.getPlayer(), Map.of("%money%", MoneyTextFormatter.format(amount), "%player%", player.getName()));
        }
    }

    @Default
    public void payCommand(Player player, OfflinePlayer target, String amountString) {
        pay(player, target, amountString);
    }
}
