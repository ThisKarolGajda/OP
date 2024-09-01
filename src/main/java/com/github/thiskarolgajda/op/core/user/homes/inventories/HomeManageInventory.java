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

import java.util.List;
import java.util.Map;

public class HomeManageInventory extends ChestInventory {
    public HomeManageInventory(Player player, UserHome home) {
        super(3, "Zarządzaj domem");

        setItemHome(22, player, () -> new HomesInventory(player));

        UserHomes homes = Plugin.get(UserHomesDatabase.class).getSafe(player.getUniqueId());

        setItem(item("Usuń dom"), 10, new ItemBuilder(Material.BARRIER), event -> {
            event.setCancelled(true);
            homes.delete(home);
            player.closeInventory();
            Plugin.get(UserHomesDatabase.class).save(homes);
            new HomesInventory(player);
        });

        setItem(item("Zmień nazwę", List.of("Obecna nazwa: %name%")), 12, new ItemBuilder(Material.NAME_TAG), event -> {
            event.setCancelled(true);
            new HomeChangeNameAnvilInventory(player, (string) -> {
                home.setName(string);
                homes.saveHome(home);
                new HomeManageInventory(player, home);
            });
        }, Map.of("%name%", home.getName()));

        setItem(item("Zmień lokalizacje", List.of("Obecna lokalizacja: %location%")), 14, new ItemBuilder(Material.CRIMSON_ROOTS), event -> {
            event.setCancelled(true);
            home.setLocation(new OpLocation(player.getLocation()));
            homes.saveHome(home);
            new HomesInventory(player);
        }, Map.of("%location%", home.getLocation().toFamilyStringWithoutWorld()));

        setItem(item("Zmień ikonę"), 16, new ItemBuilder(home.getIcon()), event -> {
            event.setCancelled(true);
            home.setRandomIcon();
            homes.saveHome(home);
            new HomeManageInventory(player, home);
        });

        fillEmptyWithBlank();
        open(player);
    }
}
