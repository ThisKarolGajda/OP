package com.github.thiskarolgajda.op.plots.blockcounter;

import lombok.Getter;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

@Getter
public enum PlotBlockCounterType {
    NETHERITE_BLOCK(Material.NETHERITE_BLOCK, 25),
    DIAMOND_BLOCK(Material.DIAMOND_BLOCK, 15),
    EMERALD_BLOCK(Material.EMERALD_BLOCK, 20),
    GOLD_BLOCK(Material.GOLD_BLOCK, 5),
    IRON_BLOCK(Material.IRON_BLOCK, 1),
    BEACON(Material.BEACON, 100),
    ;

    private final Material material;
    private final int value;

    PlotBlockCounterType(Material material, int value) {
        this.material = material;
        this.value = value;
    }

    public static boolean contains(Material type) {
        return getType(type) != null;
    }

    public static @Nullable PlotBlockCounterType getType(Material type) {
        for (PlotBlockCounterType counterType : PlotBlockCounterType.values()) {
            if (counterType.getMaterial() == type) {
                return counterType;
            }
        }

        return null;
    }

}
