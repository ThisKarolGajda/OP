package com.github.thiskarolgajda.op.core.user.homes.inventories;

import com.github.thiskarolgajda.op.core.user.homes.UserHome;
import com.github.thiskarolgajda.op.core.user.homes.UserHomes;
import com.github.thiskarolgajda.op.core.user.homes.UserHomesDatabase;
import com.github.thiskarolgajda.op.utils.HeadsType;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Consumer;

public class SelectHome {

    public SelectHome(Player player, Consumer<UserHome> onSelected) {
        new SelectHomeInventory(player, onSelected);
    }

}

class SelectHomeInventory extends ChestInventory {
    public SelectHomeInventory(@NotNull Player player, Consumer<UserHome> onSelected) {
        super(3, "select_home");

        UserHomes homes = Plugin.get(UserHomesDatabase.class).getSafe(player.getUniqueId());

        for (int i = 0; i < 5; i++) {
            int slot = 11 + i;
            if (homes.getHomes().size() > i) {
                UserHome home = homes.getHomes().get(i);
                setItem(item("home"), slot, new ItemBuilder(home.getIcon()), event -> {
                    event.setCancelled(true);
                    onSelected.accept(home);
                }, Map.of(
                        "%name%", home.getName(),
                        "%location%", home.getLocation().toFamilyStringWithoutWorld()
                ));
            } else {
                setItem(item("empty"), slot, HeadsType.GREY_HEAD.getHead(), event -> event.setCancelled(true));
            }
        }

        fillEmptyWithBlank();
        open(player);
    }
}
