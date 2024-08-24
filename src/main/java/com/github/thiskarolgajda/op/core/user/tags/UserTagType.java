package com.github.thiskarolgajda.op.core.user.tags;

import java.util.Map;

import static com.github.thiskarolgajda.op.core.user.tags.UserTagConfig.*;

public enum UserTagType {
    LIGHTNING,
    HAMMERS,
    ANCHOR,
    SWORDS,
    RADIATION,
    CLOCK,
    FLOWER,
    SKULL,
    TRIDENT,
    SCISSORS,
    YEAH,
    X,
    SNOW,
    HEART,
    FIREWORK,
    CLOUD,
    UMBRELLA,
    ECO,
    FIRE,
    ;

    private Map<String, Object> getMap() {
        return switch (this) {
            case LIGHTNING -> lightning;
            case HAMMERS -> hammers;
            case ANCHOR -> anchor;
            case SWORDS -> swords;
            case RADIATION -> radiation;
            case CLOCK -> clock;
            case FLOWER -> flower;
            case SKULL -> skull;
            case TRIDENT -> trident;
            case SCISSORS -> scissors;
            case YEAH -> yeah;
            case X -> x;
            case SNOW -> snow;
            case HEART -> heart;
            case FIREWORK -> firework;
            case CLOUD -> clock;
            case UMBRELLA -> umbrella;
            case ECO -> eco;
            case FIRE -> fire;
        };
    }

    public String getName() {
        return (String) getMap().get("name");
    }

    public int getPrice() {
        return (int) getMap().get("price");
    }
}
