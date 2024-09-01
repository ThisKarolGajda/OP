package com.github.thiskarolgajda.op.region.inventory;

import com.github.thiskarolgajda.op.region.Region;
import com.github.thiskarolgajda.op.region.RegionDatabase;
import com.github.thiskarolgajda.op.region.rule.RegionRuleType;
import com.github.thiskarolgajda.op.region.rule.RegionRules;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.injection.formatter.LoreBuilder;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.misc.Books;
import me.opkarol.oplibrary.misc.StringIconUtil;
import org.bukkit.entity.Player;

import java.util.Map;

public class RegionRuleSelectInventory extends ChestInventory {
    public RegionRuleSelectInventory(Player player, Region region, Runnable onHome) {
        super(5, "Edytuj zasady regionu");

        setListPattern(player, onHome);

        RegionRules rules = region.getRegionRules();

        for (RegionRuleType rule : RegionRuleType.values()) {
            boolean isAllowed = rules.isRuleAllowed(rule);
            setNextFree(item("Zasada %name%", LoreBuilder.create("Aktywna: %enabled%").anyMouseButtonText("przełączyć")), Books.getRandomHead(rule.name()), event -> {
                event.setCancelled(true);
                rules.toggle(rule);
                Plugin.get(RegionDatabase.class).save(region);
                new RegionRuleSelectInventory(player, region, onHome);
            }, Map.of("%name%", rule.getName(), "%enabled%", StringIconUtil.getReturnedEmojiFromBoolean(isAllowed)));
        }

        open(player);
    }
}
