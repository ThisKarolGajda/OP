package com.github.thiskarolgajda.op.region.rule;

import java.util.Set;

public record RegionRules(Set<RegionRuleType> rules) {

    public boolean contains(RegionRuleType type) {
        return rules.contains(type);
    }

    public void toggle(RegionRuleType type) {
        if (rules.contains(type)) {
            rules.remove(type);
        } else {
            rules.add(type);
        }
    }
}
