package com.github.thiskarolgajda.op.core.user.economy;

import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.commands.annotations.Command;
import me.opkarol.oplibrary.commands.annotations.Default;
import me.opkarol.oplibrary.commands.annotations.Subcommand;
import me.opkarol.oplibrary.translations.Messages;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Map;

@Command("pieniadze")
public class EconomyCommand {

    @Default
    public void defaultCommand(Player player) {
        Messages.sendMessage("economy.balance", player, Map.of("%balance%", Plugin.get(UserEconomyDatabase.class).getSafe(player.getUniqueId()).getFormattedGold()));
    }

    @Subcommand("przelej")
    public void pay(Player player, OfflinePlayer target, String amountString) {
        PayCommand.pay(player, target, amountString);
    }

    @Subcommand("sprawdz")
    public void check(Player player, OfflinePlayer target) {
        Messages.sendMessage("economy.someoneBalance", player, Map.of("%balance%", Plugin.get(UserEconomyDatabase.class).getSafe(target.getUniqueId()).getFormattedGold()));
    }
}
