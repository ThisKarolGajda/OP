package com.github.thiskarolgajda.op.plots.effects;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

@Getter
public class PlotEffects {
    private final Set<PlotEffect> effects = new HashSet<>();

    public PlotEffect get(PlotEffectType type) {
        for (PlotEffect effect : effects) {
            if (effect.getType() == type) {
                return effect;
            }
        }

        return new PlotEffect(type);
    }

    public boolean isOwned(PlotEffectType effect) {
        return get(effect).getLevel() > 0;
    }

    public int getLevel(PlotEffectType effect) {
        return get(effect).getLevel();
    }

    public double getPriceForNextLevel(@NotNull PlotEffectType effect) {
        return effect.getPricePerLevel() * (getLevel(effect) + 1);
    }

    public boolean isMaxLevel(PlotEffectType type) {
        return getLevel(type) >= type.getMaxLevel();
    }

    public void advanceLevel(PlotEffectType effect) {
        PlotEffect plotEffect = get(effect);
        plotEffect.setLevel(plotEffect.getLevel() + 1);
    }
}
