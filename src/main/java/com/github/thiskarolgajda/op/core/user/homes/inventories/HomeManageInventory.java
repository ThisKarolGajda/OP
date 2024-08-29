package com.github.thiskarolgajda.op.core.user.homes.inventories;

import com.github.thiskarolgajda.op.core.user.homes.UserHome;
import com.github.thiskarolgajda.op.core.user.homes.UserHomes;
import com.github.thiskarolgajda.op.core.user.homes.UserHomesDatabase;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import me.opkarol.oplibrary.location.OpLocation;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class HomeManageInventory extends ChestInventory {
    public HomeManageInventory(Player player, UserHome home) {
        super(3, "home_manage");

        setItemHome(22, player, () -> new HomesInventory(player));

        UserHomes homes = Plugin.get(UserHomesDatabase.class).getSafe(player.getUniqueId());

        setItem(item("delete"), 25, new ItemBuilder(Material.BARRIER), event -> {
            event.setCancelled(true);
            homes.delete(home);
            player.closeInventory();
            Plugin.get(UserHomesDatabase.class).saveAsync(homes).thenRun(() -> new HomesInventory(player));
        });

        setItem(item("change_name"), 10, new ItemBuilder(Material.NAME_TAG), event -> {
            event.setCancelled(true);
            new HomeChangeNameAnvilInventory(player, (string) -> {
                home.setName(string);
                homes.saveHome(home);
                new HomesInventory(player);
            });
        });

        setItem(item("change_location"), 13, new ItemBuilder(Material.CRIMSON_ROOTS), event -> {
            event.setCancelled(true);
            home.setLocation(new OpLocation(player.getLocation()));
            homes.saveHome(home);
            new HomesInventory(player);
        });

        setItem(item("change_icon"), 16, new ItemBuilder(home.getIcon()), event -> {
            event.setCancelled(true);
            home.setRandomIcon();
            homes.saveHome(home);
            new HomesInventory(player);
        });

        fillEmptyWithBlank();
        open(player);
    }
}
