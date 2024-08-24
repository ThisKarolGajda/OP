package com.github.thiskarolgajda.op.plots.upgrades;

import lombok.Getter;

@Getter
public class PlotUpgrade {
    private final PlotUpgradeType type;
    private int level;

    public PlotUpgrade(PlotUpgradeType type) {
        this.type = type;
        this.level = 0;
    }

    public void increaseLevel() {
        this.level++;
    }

    public int getLevelLimit() {
        return type.getLevelLimit();
    }

    public double getCostPerLevel() {
        return type.getCostPerLevel();
    }
}
