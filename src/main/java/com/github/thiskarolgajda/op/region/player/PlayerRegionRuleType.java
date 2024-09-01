package com.github.thiskarolgajda.op.region.player;

import java.util.Map;

import static com.github.thiskarolgajda.op.region.player.PlayerRegionRuleConfig.*;

public enum PlayerRegionRuleType {
    PLACE_BLOCK,
    BREAK_BLOCK,
    FILL_BUCKET,
    EMPTY_BUCKET,
    FIGHT_PLAYER,
    FIGHT_ENTITY,
    OPEN_CHEST,
    ;

    public Map<String, Object> getMap() {
        return switch (this) {
            case PLACE_BLOCK -> placeBlock;
            case BREAK_BLOCK -> breakBlock;
            case FILL_BUCKET -> fillBucket;
            case EMPTY_BUCKET -> emptyBucket;
            case FIGHT_PLAYER -> fightPlayer;
            case FIGHT_ENTITY -> fightEntity;
            case OPEN_CHEST -> openChest;
        };
    }

    public String getName() {
        return (String) getMap().get("name");
    }

}
