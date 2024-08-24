package com.github.thiskarolgajda.op.core.warps;

import com.github.thiskarolgajda.op.core.teleportation.TeleportationManager;
import com.github.thiskarolgajda.op.utils.HeadsType;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.misc.Books;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.function.Consumer;

public class SelectWarp {

    public static void teleport(Player player) {
        new SelectWarpInventory(player, (warp) -> TeleportationManager.teleport(player, warp));
    }

    public static void select(Player player, Consumer<Warp> onSelect) {
        new SelectWarpInventory(player, onSelect);
    }
}

class SelectWarpInventory extends ChestInventory {

    public SelectWarpInventory(Player player, Consumer<Warp> onSelect) {
        super(5, "select_warp");
        setListPattern(player);

        for (Warp warp : Plugin.get(WarpsDatabase.class).getAll()) {
            setNextFree(item("warp"), Books.getRandomHead(warp.getName()), event -> {
                event.setCancelled(true);
                onSelect.accept(warp);
            }, Map.of(
                    "%name%", warp.getName(),
                    "%location%", warp.getLocation().toFamilyStringWithoutWorld()
            ));
        }

        setGlobalItem(item("plot_warps"), 42, HeadsType.WARP.getHead(), event -> {
            event.setCancelled(true);
//            new PlotWarpsInventory(player);
        });

        open(player);
    }
}
