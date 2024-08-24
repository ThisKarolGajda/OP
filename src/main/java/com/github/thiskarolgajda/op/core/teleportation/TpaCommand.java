package com.github.thiskarolgajda.op.core.teleportation;

import me.opkarol.oplibrary.commands.annotations.Command;
import me.opkarol.oplibrary.commands.annotations.Default;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Map;

import static com.github.thiskarolgajda.op.OP.playerNotOnline;
import static com.github.thiskarolgajda.op.core.teleportation.TeleportationManager.*;

@Command("tpa")
public class TpaCommand {

    @Default
    public void tpaCommand(Player player, OfflinePlayer playerToTeleport) {
        if (playerToTeleport.getPlayer() == null) {
            playerNotOnline.error(player);
            return;
        }

        if (playerToTeleport.getUniqueId().equals(player.getUniqueId())) {
            cannotTeleportToItself.error(player);
            return;
        }

        teleportRequestSent.success(player);
        teleportRequestReceived.success(playerToTeleport.getPlayer(), Map.of("%name%", player.getName()));
        TeleportationManager.setLastTpaRequest(player.getUniqueId(), playerToTeleport.getUniqueId());
    }

}
