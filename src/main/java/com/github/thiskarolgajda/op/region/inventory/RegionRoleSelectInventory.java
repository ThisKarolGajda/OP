package com.github.thiskarolgajda.op.region.inventory;

import com.github.thiskarolgajda.op.region.Region;
import com.github.thiskarolgajda.op.region.role.RegionRoleType;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Map;

public class RegionRoleSelectInventory extends ChestInventory {

    public RegionRoleSelectInventory(Player player, Region region) {
        super(3, "region_role_select");

        setItem(item("role"), 11, new ItemBuilder(Material.PLAYER_HEAD), event -> {
            event.setCancelled(true);
            new RegionRulesSelectInventory(player, region, RegionRoleType.MEMBER);
        }, Map.of(
                "%name%", "Członek"
        ));

        setItem(item("role"), 15, new ItemBuilder(Material.CREEPER_HEAD), event -> {
            event.setCancelled(true);
            new RegionRulesSelectInventory(player, region, RegionRoleType.GUEST);
        }, Map.of(
                "%name%", "Gość"
        ));

        fillEmptyWithBlank();
        open(player);
    }
}
    