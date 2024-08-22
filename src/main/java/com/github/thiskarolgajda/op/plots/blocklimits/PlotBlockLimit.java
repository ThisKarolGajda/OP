package com.github.thiskarolgajda.op.plots.blocklimits;

import lombok.Data;

@Data
public class PlotBlockLimit {
    private final PlotBlockLimitType type;
    private int used;
    private int limit;
    private int level;

    public PlotBlockLimit(PlotBlockLimitType type, int level) {
        this.type = type;
        this.level = level;
        this.used = 0;
        updateLimit();
    }

    public void updateLimit() {
        this.limit = type.getUnits() * level;
    }

    public void decrement() {
        used--;
    }

    public void increment() {
        used++;
    }

    public int getPriceForNextLimitUpgrade() {
        return getType().getCost() * getType().getUnits();
    }

    public boolean isLimitReached() {
        return used >= limit;
    }

    public void increaseLevel() {
        level++;
        updateLimit();
    }
}
