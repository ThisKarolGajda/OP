package com.github.thiskarolgajda.op.plots.blocklimits;

import com.github.thiskarolgajda.op.core.user.economy.MoneyTextFormatter;
import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.inventories.PlotMainInventory;
import me.opkarol.oplibrary.extensions.Vault;
import me.opkarol.oplibrary.injection.messages.StringMessage;
import me.opkarol.oplibrary.inventories.ChestListInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.github.thiskarolgajda.op.OP.notEnoughMoney;
import static me.opkarol.oplibrary.inventories.ChestInventory.item;

public class PlotBlockLimitsInventory {
    public static StringMessage increasedPlotBlockLimit = new StringMessage("Podniesiono limit bloku!");

    public PlotBlockLimitsInventory(Player player, Plot plot) {
        open(player, plot);
    }

    private void open(Player player, Plot plot) {
        new ChestListInventory.ChestListInventoryBuilder<PlotBlockLimitType>("Limity bloków", item("%name%", List.of("Użycie: %current_usage%/%current_limit%", "Koszt ulepszenia: %price% (%count% el.)")))
                .withItem(redstoneLimitType -> redstoneLimitType == PlotBlockLimitType.REDSTONE ? new ItemBuilder(Material.REDSTONE) : new ItemBuilder(redstoneLimitType.getMaterial()))
                .withReplacements(redstoneLimitType -> {
                    PlotBlockLimit limit = plot.getBlockLimits().get(redstoneLimitType);
                    return Map.of(
                            "%current_limit%", String.valueOf(limit.getLimit()),
                            "%current_usage%", String.valueOf(limit.getUsed()),
                            "%price%", MoneyTextFormatter.format(limit.getPriceForNextLimitUpgrade()),
                            "%name%", redstoneLimitType.getName(),
                            "%count%", String.valueOf(limit.getType().getUnits())
                    );
                })
                .withList(Arrays.asList(PlotBlockLimitType.values()))
                .withOnClick(redstone -> {
                    PlotBlockLimits limits = plot.getBlockLimits();
                    PlotBlockLimit limit = limits.get(redstone);
                    int cost = limit.getPriceForNextLimitUpgrade();
                    if (Vault.remove(player, cost) == Vault.VAULT_RETURN_INFO.WITHDRAW_SUCCESSFUL) {
                        limit.increaseLevel();
                        limits.set(limit);
                        plot.setBlockLimits(limits);
                        new PlotBlockLimitsInventory(player, plot);
                        increasedPlotBlockLimit.success(player);
                    } else {
                        notEnoughMoney.error(player, cost);
                    }
                })
                .withOnHomeAction(() -> new PlotMainInventory(plot, player))
                .build(player);
    }
}
