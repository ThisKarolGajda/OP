package com.github.thiskarolgajda.op.plots.upgrades;

import java.util.Map;

import static com.github.thiskarolgajda.op.plots.upgrades.PlotUpgradeConfig.*;

public enum PlotUpgradeType {
    PLANTS_GROWTH,
    ANIMALS_GROWTH,
    PLAYER_LIMIT,
    SHOP_CHEST_LIMIT,
    CHUNK_LIMIT,
    ;

    private Map<String, Object> getMap() {
        return switch (this) {
            case PLANTS_GROWTH -> plantsGrowth;
            case ANIMALS_GROWTH -> animalsGrowth;
            case PLAYER_LIMIT -> playerLimit;
            case SHOP_CHEST_LIMIT -> shopChestLimit;
            case CHUNK_LIMIT -> chunkLimit;
        };
    }

    public int getLevelLimit() {
        return (int) getMap().get("levelLimit");
    }

    public double getCostPerLevel() {
        return (double) getMap().get("costPerLevel");
    }

}
