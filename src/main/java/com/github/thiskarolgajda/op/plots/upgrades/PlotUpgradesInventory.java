package com.github.thiskarolgajda.op.plots.upgrades;

import com.github.thiskarolgajda.op.core.user.economy.MoneyTextFormatter;
import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.inventories.PlotMainInventory;
import me.opkarol.oplibrary.extensions.Vault;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.tools.Heads;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

import static com.github.thiskarolgajda.op.OP.notEnoughMoney;
import static me.opkarol.oplibrary.translations.Messages.getTranslation;
import static me.opkarol.oplibrary.translations.Messages.sendMessage;

public class PlotUpgradesInventory extends ChestInventory {

    public PlotUpgradesInventory(Player player, Plot plot) {
        super(3, "Ulepszenia działki");
        PlotUpgrades plotUpgrades = plot.getUpgrades();

        setUpgradeItem(player, plot, 10, "Ulepszenie ilości chunków", plotUpgrades, PlotUpgradeType.CHUNK_LIMIT);
        setUpgradeItem(player, plot, 11, "Ulepszenie rozwoju zwierząt", plotUpgrades, PlotUpgradeType.ANIMALS_GROWTH);
        setUpgradeItem(player, plot, 13, "Ulepszenie limitu graczy", plotUpgrades, PlotUpgradeType.PLAYER_LIMIT);
        setUpgradeItem(player, plot, 15, "Ulepszenie rozwoju roślin", plotUpgrades, PlotUpgradeType.PLANTS_GROWTH);
        setUpgradeItem(player, plot, 16, "Ulepszenie ilości sklepów", plotUpgrades, PlotUpgradeType.SHOP_CHEST_LIMIT);

        setItemHome(22, player, () -> new PlotMainInventory(plot, player));
        fillEmptyWithBlank();
        open(player);
    }

    private void setUpgradeItem(Player player, Plot plot, int slot, String name, PlotUpgrades plotUpgrades, PlotUpgradeType upgradeType) {
        setItem(item(name, List.of("Obecny poziom: %current_level%", "Koszt na następny poziom: %cost_for_next_level%", "Obecna wartość: %value%")), slot, Heads.get(getHeadId(upgradeType)), event -> handleEventClick(event, plot, player, plotUpgrades, upgradeType), Map.of("%current_level%", String.valueOf(plotUpgrades.getLevel(upgradeType)), "%cost_for_next_level%", getCostForNextLevel(plot, plotUpgrades, upgradeType), "%value%", getUpgradeValue(plot, upgradeType)));
    }

    private String getHeadId(PlotUpgradeType upgradeType) {
        return switch (upgradeType) {
            case CHUNK_LIMIT -> "c61b279e00f19d9f69a712f6560767cae0cfa02c83160d46c7f50c7526f6776e";
            case ANIMALS_GROWTH -> "3d597f77cde32c9ac9b06f82fcf7c9cb500facc14bff166222b24be39962f0ef";
            case PLAYER_LIMIT -> "7fe9725c950472e469b9fccae32f61bcefebdb5ea9ce9c92d58171ffb7a336fe";
            case PLANTS_GROWTH -> "49392a2bfa1c4a795bad101797cd54077910c55c1fa8ae55b679e95d2c6e860f";
            case SHOP_CHEST_LIMIT -> "49392a2bfa1c4a795bad101797cd54077910c55c1fa8ae55b679e95d2c6e860f";
        };
    }

    private String getUpgradeValue(Plot plot, PlotUpgradeType upgradeType) {
        return switch (upgradeType) {
            case CHUNK_LIMIT, PLAYER_LIMIT, SHOP_CHEST_LIMIT ->
                    String.valueOf(plot.getUpgrades().getLevel(upgradeType));
            case ANIMALS_GROWTH -> getAnimalMatureTime(plot.getUpgrades().getLevel(PlotUpgradeType.ANIMALS_GROWTH));
            case PLANTS_GROWTH -> String.valueOf(plot.getUpgrades().getLevel(PlotUpgradeType.PLANTS_GROWTH));
        };
    }

    private void handleEventClick(@NotNull InventoryClickEvent event, Plot plot, Player player, PlotUpgrades plotUpgrades, PlotUpgradeType type) {
        event.setCancelled(true);
        buyUpgrade(plot, player, plotUpgrades, type);
        player.closeInventory();
        new PlotUpgradesInventory(player, plot);
    }

    private String getCostForNextLevel(Plot plot, @NotNull PlotUpgrades plotUpgrades, PlotUpgradeType type) {
        double value = plotUpgrades.getCostForNextLevel(plot, type);
        if (value == 0) {
            return getTranslation("reached_max_level");
        }

        return MoneyTextFormatter.format(value);
    }

    private @NotNull String getAnimalMatureTime(int level) {
        return switch (level) {
            case 0 -> "20 min";
            case 1 -> "18 min";
            case 2 -> "16 min";
            case 3 -> "14 min";
            case 4 -> "12 min";
            case 5 -> "10 min";
            default -> throw new IllegalStateException("Unexpected value: " + level);
        };
    }

    private @NotNull String getPlotSizes(int level) {
        return switch (level) {
            case 0 -> "41x41";
            case 1 -> "51x51";
            case 2 -> "61x61";
            case 3 -> "71x71";
            case 4 -> "81x81";
            case 5 -> "91x91";
            case 6 -> "111x111";
            default -> throw new IllegalStateException("Unexpected value: " + level);
        };
    }

    private void buyUpgrade(Plot plot, Player player, @NotNull PlotUpgrades plotUpgrades, PlotUpgradeType type) {
        Vault vault = Vault.getInstance();
        if (plotUpgrades.canNotLevelUp(type)) {
            sendMessage("cant_upgrade_more", player);
            return;
        }

        double cost = plotUpgrades.getCostForNextLevel(plot, type);
        if (!vault.has(player, cost)) {
            notEnoughMoney.error(player, cost);
            return;
        }

        Vault.VAULT_RETURN_INFO returnInfo = vault.withdraw(player, cost);
        if (returnInfo == Vault.VAULT_RETURN_INFO.WITHDRAW_SUCCESSFUL) {
            plotUpgrades.increaseLevel(plot, type);
            sendMessage("success_upgraded", player);
        }
    }
}
