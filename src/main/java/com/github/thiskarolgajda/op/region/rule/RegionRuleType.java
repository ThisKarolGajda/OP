package com.github.thiskarolgajda.op.region.rule;

import java.util.Map;

import static com.github.thiskarolgajda.op.region.rule.RegionRuleConfig.*;

public enum RegionRuleType {
    CAN_FIRE_SPREAD,
    CAN_FIRE_IGNITE,
    CAN_BLOCK_SPREAD,
    CAN_LIQUID_SPREAD,
    CAN_TNT_EXPLODE,
    ;

    public Map<String, Object> getMap() {
        return switch (this) {
            case CAN_FIRE_SPREAD -> canFireSpread;
            case CAN_FIRE_IGNITE -> canFireIgnite;
            case CAN_BLOCK_SPREAD -> canBlockSpread;
            case CAN_LIQUID_SPREAD -> canLiquidSpread;
            case CAN_TNT_EXPLODE -> canTxtExplode;
        };
    }

    public String getName() {
        return (String) getMap().get("name");
    }
}
