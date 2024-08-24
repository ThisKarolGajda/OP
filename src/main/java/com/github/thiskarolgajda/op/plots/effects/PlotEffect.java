package com.github.thiskarolgajda.op.plots.effects;

import lombok.Data;

@Data
public class PlotEffect {
    private final PlotEffectType type;
    private int level;

    public PlotEffect(PlotEffectType type) {
        this.type = type;
        this.level = 0;
    }
}
