package com.github.thiskarolgajda.op.plots.upgrades;

import me.opkarol.oplibrary.injection.config.Config;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Config(path = "plot.upgrade")
public class PlotUpgradeConfig {

    @Config
    public static double discountedUpgradesCostPercentage = 0.7;

    @Config
    public static Map<String, Object> plantsGrowth = getObject(10, 10000);
    @Config
    public static Map<String, Object> animalsGrowth = getObject(10, 10000);
    @Config
    public static Map<String, Object> playerLimit = getObject(100, 100);
    @Config
    public static Map<String, Object> shopChestLimit = getObject(100, 100);
    @Config
    public static Map<String, Object> chunkLimit = getObject(100, 100);

    @Contract(value = "_, _ -> new", pure = true)
    private static @NotNull Map<String, Object> getObject(int limitLevel, double costPerLevel) {
        return Map.of(
                "levelLimit", limitLevel,
                "costPerLevel", costPerLevel
        );
    }
}
