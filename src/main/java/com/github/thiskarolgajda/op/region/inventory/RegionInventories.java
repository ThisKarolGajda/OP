package com.github.thiskarolgajda.op.region.inventory;

import com.github.thiskarolgajda.op.region.Region;
import com.github.thiskarolgajda.op.region.RegionConfig;
import com.github.thiskarolgajda.op.region.RegionsDatabase;
import com.github.thiskarolgajda.op.region.player.PlayerRegionRuleType;
import com.github.thiskarolgajda.op.region.player.PlayerRegionRules;
import com.github.thiskarolgajda.op.region.role.RegionRoleType;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.injection.formatter.LoreBuilder;
import me.opkarol.oplibrary.injection.inventories.GlobalInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import me.opkarol.oplibrary.misc.Books;
import me.opkarol.oplibrary.misc.StringIconUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Map;

import static me.opkarol.oplibrary.injection.formatter.NameBuilder.name;

public class RegionInventories {
    @Inject
    private static RegionsDatabase database;

    public static void regionRoleSelect(Player player, Region region) {
        regionRoleSelect.open(player, Map.of("region", region));
    }

    static GlobalInventory regionRoleSelect = GlobalInventory.row3("Wybierz rolę").args((inventory, args) -> {
        Region region = args.getArg("region");
        inventory.item("guest", name(RegionConfig.guestName), LoreBuilder.create("Gościem jest każdy użytkownik.").anyMouseToMoveNext().build(), 11, new ItemBuilder(Material.CREEPER_HEAD), click -> {
            click.cancel();
            regionRulesSelect(click.getPlayer(), region, RegionRoleType.GUEST);
        });
        inventory.item("member", name(RegionConfig.memberName), LoreBuilder.create("Członkiem jest każdy użytkownik, który jest dodany do regionu.", "Uprawnienia członka <h>nadpisują <h>uprawnienia <h>gościa!", "Właściciel ma wszystkie uprawnienia.").anyMouseToMoveNext().build(), 15, new ItemBuilder(Material.PLAYER_HEAD), click -> {
            click.cancel();
            regionRulesSelect(click.getPlayer(), region, RegionRoleType.MEMBER);
        });
    }).fillAllEmpty();

    public static void regionRulesSelect(Player player, Region region, RegionRoleType role) {
        regionRulesSelect.open(player, Map.of("region", region, "role", role));
    }

    static GlobalInventory regionRulesSelect = GlobalInventory.row5("Wybierz zasady").args((inventory, args) -> {
        Region region = args.getArg("region");
        RegionRoleType role = args.getArg("role");
        inventory.setHomePageAction(player -> regionRoleSelect(player, region));
        inventory.list("rule", name("%name%"), LoreBuilder.create("Zasada włączona: %status%").anyMouseButtonText("zmienić ustawienie").build(), rule -> Books.getRandomHead(rule.name()), (rule, player) -> {
            PlayerRegionRules rules = region.getPlayerRules(role);
            boolean isAllowed = rules.isRuleAllowed(rule);
            return Map.of("%name%", rule.name(), "%status%", StringIconUtil.getReturnedEmojiFromBoolean(isAllowed));
        }, PlayerRegionRuleType.values(), (rule, click) -> {
            click.cancel();
            PlayerRegionRules rules = region.getPlayerRules(role);
            rules.toggleRule(rule);
            region.getPlayerRules().put(role, rules);
            database.save(region);
            regionRulesSelect(click.getPlayer(), region, role);
        });
    });
}
