package com.github.thiskarolgajda.op.plots.blocklimits;

import lombok.Data;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

@Data
public class PlotBlockLimits {
    private final Set<PlotBlockLimit> limits = new HashSet<>();

    public @NotNull PlotBlockLimit get(PlotBlockLimitType type) {
        for (PlotBlockLimit blockLimit : limits) {
            if (blockLimit.getType() == type) {
                return blockLimit;
            }
        }

        return new PlotBlockLimit(type, 1);
    }

    public PlotBlockLimit get(Material type) {
        for (PlotBlockLimit redstoneLimit : limits) {
            if (redstoneLimit.getType().getMaterial() == type) {
                return redstoneLimit;
            }
        }

        PlotBlockLimitType type1 = PlotBlockLimitType.getType(type);
        if (type1 == null) {
            return null;
        }

        return new PlotBlockLimit(type1, 1);
    }

    public void set(PlotBlockLimit redstoneLimit) {
        limits.removeIf(limit -> limit.getType() == redstoneLimit.getType());
        limits.add(redstoneLimit);
    }
}
