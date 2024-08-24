package com.github.thiskarolgajda.op.core.teleportation;

import me.opkarol.oplibrary.commands.annotations.Command;
import me.opkarol.oplibrary.commands.annotations.Default;
import org.bukkit.entity.Player;

@Command("tpakceptuj")
public class TpaAcceptCommand {

    @Default
    public void tpaAcceptCommand(Player player) {
        TeleportationManager.acceptTpaRequest(player);
    }
}
