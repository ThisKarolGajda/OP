package com.github.thiskarolgajda.op.core.teleportation;

import me.opkarol.oplibrary.commands.annotations.Command;
import me.opkarol.oplibrary.commands.annotations.Default;
import org.bukkit.entity.Player;

import static com.github.thiskarolgajda.op.core.teleportation.TeleportationManager.noTeleportRequest;

@Command("tpodrzuc")
public class TpaDenyCommand {

    @Default
    public void tpaDenyCommand(Player player) {
        if (!TeleportationManager.denyTpaRequest(player)) {
            noTeleportRequest.error(player);
        }
    }
}
