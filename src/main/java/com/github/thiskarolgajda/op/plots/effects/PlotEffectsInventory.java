package com.github.thiskarolgajda.op.plots.effects;

import com.github.thiskarolgajda.op.core.user.economy.MoneyTextFormatter;
import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.inventories.PlotMainInventory;
import me.opkarol.oplibrary.extensions.Vault;
import me.opkarol.oplibrary.inventories.ChestListInventory;
import me.opkarol.oplibrary.tools.Heads;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static com.github.thiskarolgajda.op.OP.notEnoughMoney;
import static me.opkarol.oplibrary.inventories.ChestInventory.item;
import static me.opkarol.oplibrary.translations.Messages.sendMessage;

public class PlotEffectsInventory {

    public PlotEffectsInventory(Player player, Plot plot) {
        new ChestListInventory.ChestListInventoryBuilder<PlotEffectType>("plot_effects", item("effect"))
                .withItem(effect -> Heads.get(effect.getTexture()))
                .withOnHomeAction(() -> new PlotMainInventory(plot, player))
                .withReplacements(effect -> Map.of(
                        "%name%", effect.getName(),
                        "%level%", String.valueOf(plot.getEffects().getLevel(effect)),
                        "%price%", MoneyTextFormatter.format(plot.getEffects().getPriceForNextLevel(effect)),
                        "%max_level%", String.valueOf(effect.getMaxLevel())
                ))
                .withList(PlotEffectType.values())
                .withOnClick(effect -> effectClick(player, plot, effect))
                .build(player);
    }

    private void effectClick(Player player, @NotNull Plot plot, PlotEffectType effect) {
        Vault vault = Vault.getInstance();
        if (plot.getEffects().isMaxLevel(effect)) {
            sendMessage("plot.effects.cantUpgradeMore", player);
            return;
        }

        double cost = plot.getEffects().getPriceForNextLevel(effect);
        Vault.VAULT_RETURN_INFO returnInfo = vault.withdraw(player, cost);
        if (returnInfo == Vault.VAULT_RETURN_INFO.WITHDRAW_SUCCESSFUL) {
            PlotEffects effects = plot.getEffects();
            effects.advanceLevel(effect);
            plot.setEffects(effects);
            Bukkit.getPluginManager().callEvent(new PlotEffectsChangeCurrentEvent(plot));
            sendMessage("plot.effects.upgradedSuccess", player);
        } else {
            notEnoughMoney.error(player, cost);
        }

        new PlotEffectsInventory(player, plot);
    }
}
    