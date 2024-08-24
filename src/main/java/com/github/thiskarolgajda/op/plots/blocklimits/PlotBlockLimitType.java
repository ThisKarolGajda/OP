package com.github.thiskarolgajda.op.plots.blocklimits;

import lombok.Getter;
import org.bukkit.Material;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import static com.github.thiskarolgajda.op.plots.blocklimits.PlotBlockLimitConfig.*;

@Getter
public enum PlotBlockLimitType {
    REDSTONE(Material.REDSTONE_WIRE),
    HOPPER(Material.HOPPER),
    OBSERVER(Material.OBSERVER),
    PISTON(Material.PISTON),
    REPEATER(Material.REPEATER),
    COMPARATOR(Material.COMPARATOR),
    REDSTONE_TORCH(Material.REDSTONE_TORCH),
    REDSTONE_LAMP(Material.REDSTONE_LAMP),
    REDSTONE_BLOCK(Material.REDSTONE_BLOCK),
    LEVER(Material.LEVER),
    STICKY_PISTON(Material.STICKY_PISTON),
    TRIPWIRE_HOOK(Material.TRIPWIRE_HOOK),
    DAYLIGHT_DETECTOR(Material.DAYLIGHT_DETECTOR),
    TARGET(Material.TARGET),
    ;

    private final Material material;

    PlotBlockLimitType(Material material) {
        this.material = material;
    }

    public static boolean contains(Material type) {
        return getType(type) != null;
    }

    public static @Nullable PlotBlockLimitType getType(Material type) {
        for (PlotBlockLimitType redstoneLimitType : PlotBlockLimitType.values()) {
            if (redstoneLimitType.getMaterial() == type) {
                return redstoneLimitType;
            }
        }

        return null;
    }

    public String getName() {
        return (String) getMap().get("name");
    }

    public int getCost() {
        return (int) getMap().get("cost");
    }

    public int getUnits() {
        return (int) getMap().get("units");
    }

    private Map<String, Object> getMap() {
        return switch (this) {
            case REDSTONE -> redstone;
            case HOPPER -> hopper;
            case OBSERVER -> observer;
            case PISTON -> piston;
            case REPEATER -> repeater;
            case COMPARATOR -> comparator;
            case REDSTONE_LAMP -> redstoneLamp;
            case REDSTONE_BLOCK -> redstoneBlock;
            case LEVER -> lever;
            case STICKY_PISTON -> stickyPiston;
            case TRIPWIRE_HOOK -> tripwireHook;
            case DAYLIGHT_DETECTOR -> daylightDetector;
            case REDSTONE_TORCH -> redstoneTorch;
            case TARGET -> target;
        };
    }
}
