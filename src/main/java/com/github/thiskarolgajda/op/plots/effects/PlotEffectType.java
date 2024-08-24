package com.github.thiskarolgajda.op.plots.effects;

import lombok.Getter;

import java.util.Map;

import static com.github.thiskarolgajda.op.plots.effects.PlotEffectConfig.*;

@Getter
public enum PlotEffectType {
    REGENERATION,
    RESISTANCE,
    STRENGTH,
    SPEED,
    JUMP_BOOST,
    HASTE,
    NIGHT_VISION,
    WATER_BREATHING,
    FIRE_RESISTANCE,
    SATURATION,
    LUCK;

    public String getName() {
        return (String) getMap().get("name");
    }

    public String getTexture() {
        return (String) getMap().get("texture");
    }

    public int getMaxLevel() {
        return (int) getMap().get("maxLevel");
    }

    public int getPricePerLevel() {
        return (int) getMap().get("maxLevel");
    }

    public Map<String, Object> getMap() {
        return switch (this) {
            case REGENERATION -> regeneration;
            case RESISTANCE -> resistance;
            case STRENGTH -> strength;
            case SPEED -> speed;
            case JUMP_BOOST -> jumpBoost;
            case HASTE -> haste;
            case NIGHT_VISION -> nightVision;
            case WATER_BREATHING -> waterBreathing;
            case FIRE_RESISTANCE -> fireResistance;
            case SATURATION -> saturation;
            case LUCK -> luck;
        };
    }
}
