package com.github.thiskarolgajda.op.region.inventory;

import com.github.thiskarolgajda.op.region.Region;
import com.github.thiskarolgajda.op.region.role.RegionRoleType;
import me.opkarol.oplibrary.injection.formatter.LoreBuilder;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class RegionPlayerRoleSelectInventory extends ChestInventory {

    public RegionPlayerRoleSelectInventory(Player player, Region region, @Nullable Runnable onHome) {
        super(3, "Wybierz grupę");

        if (onHome != null) {
            setItemHome(22, player, onHome);
        }

        setItem(item("%name%", LoreBuilder.create().anyMouseButtonText("edytować zasady")), 12, new ItemBuilder(Material.PLAYER_HEAD), event -> {
            event.setCancelled(true);
            new RegionPlayerRulesSelectInventory(player, region, RegionRoleType.MEMBER, onHome);
        }, Map.of(
                "%name%", "Członek"
        ));

        setItem(item("%name%", LoreBuilder.create().anyMouseButtonText("edytować zasady")), 14, new ItemBuilder(Material.CREEPER_HEAD), event -> {
            event.setCancelled(true);
            new RegionPlayerRulesSelectInventory(player, region, RegionRoleType.GUEST, onHome);
        }, Map.of(
                "%name%", "Gość"
        ));

        fillEmptyWithBlank();
        open(player);
    }
}
    