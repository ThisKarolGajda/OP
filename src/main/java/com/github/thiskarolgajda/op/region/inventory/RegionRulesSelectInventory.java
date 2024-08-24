package com.github.thiskarolgajda.op.region.inventory;

import com.github.thiskarolgajda.op.region.Region;
import com.github.thiskarolgajda.op.region.RegionDatabase;
import com.github.thiskarolgajda.op.region.player.PlayerRegionRuleType;
import com.github.thiskarolgajda.op.region.player.PlayerRegionRules;
import com.github.thiskarolgajda.op.region.role.RegionRoleType;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.misc.Books;
import me.opkarol.oplibrary.misc.StringIconUtil;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Map;

public class RegionRulesSelectInventory extends ChestInventory {

    public RegionRulesSelectInventory(Player player, Region region, RegionRoleType role) {
        super(5, "region_rules_select");
        setListPattern(player, () -> new RegionRoleSelectInventory(player, region));

        for (PlayerRegionRuleType rule : PlayerRegionRuleType.values()) {
            PlayerRegionRules rules = region.getPlayerRules().get(role);
            if (rules == null) {
                rules = new PlayerRegionRules(new HashSet<>());
            }

            boolean isAllowed = rules.isRuleAllowed(rule);
            PlayerRegionRules finalRules = rules;
            setNextFree(item("rule"), Books.getRandomHead(rule.name()), event -> {
                event.setCancelled(true);
                finalRules.toggleRule(rule);
                region.getPlayerRules().put(role, finalRules);
                Plugin.get(RegionDatabase.class).save(region);
                new RegionRulesSelectInventory(player, region, role);
            }, Map.of(
                    "%name%", rule.name(),
                    "%status%", StringIconUtil.getReturnedEmojiFromBoolean(isAllowed)
            ));
        }

        open(player);
    }
}
    