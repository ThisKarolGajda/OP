package com.github.thiskarolgajda.op.region.inventory;

import com.github.thiskarolgajda.op.region.Region;
import com.github.thiskarolgajda.op.region.RegionDatabase;
import com.github.thiskarolgajda.op.region.player.PlayerRegionRuleType;
import com.github.thiskarolgajda.op.region.player.PlayerRegionRules;
import com.github.thiskarolgajda.op.region.role.RegionRoleType;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.injection.formatter.LoreBuilder;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.misc.Books;
import me.opkarol.oplibrary.misc.StringIconUtil;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class RegionPlayerRulesSelectInventory extends ChestInventory {

    public RegionPlayerRulesSelectInventory(Player player, Region region, RegionRoleType role, @Nullable Runnable onHome) {
        super(5, "Edytuj zasasdy dla " + (role == RegionRoleType.GUEST ? "Gościa" : "Członka"));
        setListPattern(player, () -> new RegionPlayerRoleSelectInventory(player, region, onHome));

        PlayerRegionRules rules = region.getRules(role);
        for (PlayerRegionRuleType rule : PlayerRegionRuleType.values()) {
            boolean isAllowed = rules.isRuleAllowed(rule);
            setNextFree(item("Zasada %name%", LoreBuilder.create("Aktywna: %enabled%").anyMouseButtonText("przełączyć")), Books.getRandomHead(rule.name()), event -> {
                event.setCancelled(true);
                rules.toggle(rule);
                region.getPlayerRules().put(role, rules);
                Plugin.get(RegionDatabase.class).save(region);
                new RegionPlayerRulesSelectInventory(player, region, role, onHome);
            }, Map.of(
                    "%name%", rule.getName(),
                    "%enabled%", StringIconUtil.getReturnedEmojiFromBoolean(isAllowed)
            ));
        }

        open(player);
    }
}
    