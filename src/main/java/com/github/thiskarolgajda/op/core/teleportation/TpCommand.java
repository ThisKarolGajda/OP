package com.github.thiskarolgajda.op.core.teleportation;

import me.opkarol.oplibrary.commands.annotations.Command;
import me.opkarol.oplibrary.commands.annotations.Default;
import me.opkarol.oplibrary.commands.annotations.Permission;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import static com.github.thiskarolgajda.op.OP.playerNotOnline;
import static com.github.thiskarolgajda.op.core.teleportation.TeleportationManager.cannotTeleportToItself;

@Command("tp")
public class TpCommand {

    @Default
    @Permission("opvillages.admin.tp")
    public void tpCommand(Player player, OfflinePlayer target) {
        if (target.getPlayer() == null) {
            playerNotOnline.error(player);
            return;
        }

        if (target.getUniqueId().equals(player.getUniqueId())) {
            cannotTeleportToItself.error(player);
            return;
        }

        TeleportationManager.teleport(player, target.getPlayer().getLocation());
    }
}
