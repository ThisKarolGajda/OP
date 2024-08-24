package com.github.thiskarolgajda.op.core.warps;

import com.github.thiskarolgajda.op.core.teleportation.TeleportationManager;
import me.opkarol.oplibrary.commands.annotations.Command;
import me.opkarol.oplibrary.commands.annotations.Default;
import me.opkarol.oplibrary.commands.annotations.NoUse;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.injection.messages.StringMessage;
import org.bukkit.entity.Player;

import java.util.Map;

@Command("warp")
public class WarpCommand {
    @Inject
    private static WarpsDatabase database;

    public static StringMessage warpDoesNotExist = StringMessage.arg("Warp o nazie: %name% nie istnieje!", object -> Map.of("%name%", object.toString()));

    @Default
    public void warpCommand(Player player, String warpName) {
        Warp warp = database.getWarp(warpName);
        if (warp == null) {
            warpDoesNotExist.error(player, warpDoesNotExist);
            return;
        }

        TeleportationManager.teleport(player, warp);
    }

    @NoUse
    public void noUse(Player player) {
        SelectWarp.teleport(player);
    }
}
