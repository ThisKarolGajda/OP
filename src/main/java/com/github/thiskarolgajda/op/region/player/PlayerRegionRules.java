package com.github.thiskarolgajda.op.region.player;

import java.util.Set;

public record PlayerRegionRules(Set<PlayerRegionRuleType> allowedRules) {

    public boolean isRuleAllowed(PlayerRegionRuleType rule) {
        return allowedRules.contains(rule);
    }

    public void toggleRule(PlayerRegionRuleType rule) {
        if (allowedRules.contains(rule)) {
            allowedRules.remove(rule);
        } else {
            allowedRules.add(rule);
        }
    }
}
