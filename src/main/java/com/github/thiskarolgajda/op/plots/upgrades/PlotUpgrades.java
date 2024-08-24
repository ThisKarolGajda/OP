package com.github.thiskarolgajda.op.plots.upgrades;

import com.github.thiskarolgajda.op.permission.PermissionType;
import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import lombok.Getter;
import me.opkarol.oplibrary.injection.Inject;

import java.util.HashSet;
import java.util.Set;

@Getter
public class PlotUpgrades {
    @Inject
    private static PlotDatabase database;
    private final Set<PlotUpgrade> upgrades = new HashSet<>();

    public PlotUpgrade get(PlotUpgradeType type) {
        for (PlotUpgrade upgrade : upgrades) {
            if (upgrade.getType() == type) {
                return upgrade;
            }
        }

        PlotUpgrade upgrade = new PlotUpgrade(type);
        upgrades.add(upgrade);
        return upgrade;
    }

    public int getLevel(PlotUpgradeType type) {
        return get(type).getLevel();
    }

    public boolean canNotLevelUp(PlotUpgradeType type) {
        PlotUpgrade upgrade = get(type);
        return upgrade.getLevel() >= upgrade.getLevelLimit();
    }

    public void increaseLevel(Plot plot, PlotUpgradeType type) {
        PlotUpgrade upgrade = get(type);
        upgrade.increaseLevel();
        database.save(plot);
    }

    public double getCostForNextLevel(Plot plot, PlotUpgradeType type) {
        if (canNotLevelUp(type)) {
            return 0;
        }

        PlotUpgrade upgrade = get(type);
        double baseCost = upgrade.getCostPerLevel() * (upgrade.getLevel() + 1);

        if (plot.getOwner().getPlayer() != null && PermissionType.CHEAPER_UPGRADES_COST.hasPermission(plot.getOwner().getPlayer())) {
            return baseCost * PlotUpgradeConfig.discountedUpgradesCostPercentage;
        }
        return baseCost;
    }
}
