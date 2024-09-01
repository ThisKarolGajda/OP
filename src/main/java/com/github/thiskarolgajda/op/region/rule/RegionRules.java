package com.github.thiskarolgajda.op.region.rule;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.github.thiskarolgajda.op.region.rule.RegionRuleType.*;

public record RegionRules(Set<RegionRuleType> allowedRules) {

    public boolean isRuleAllowed(RegionRuleType type) {
        return allowedRules.contains(type);
    }

    public void toggle(RegionRuleType type) {
        if (allowedRules.contains(type)) {
            allowedRules.remove(type);
        } else {
            allowedRules.add(type);
        }
    }

    @Contract(" -> new")
    public static @NotNull RegionRules defaultRules() {
        return new RegionRules(new HashSet<>(List.of(CAN_FIRE_IGNITE, CAN_LIQUID_SPREAD, CAN_BLOCK_SPREAD)));
    }
}
