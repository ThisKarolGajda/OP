package com.github.thiskarolgajda.op.core.user.invite;

import me.opkarol.oplibrary.commands.annotations.Command;
import me.opkarol.oplibrary.commands.annotations.Default;
import me.opkarol.oplibrary.commands.annotations.NoUse;
import org.bukkit.entity.Player;

@Command("zaproszenia")
public class InvitesCommand {

    @Default
    @NoUse
    public void defaultCommand(Player player) {
        new UserInvitesInventory(player);
    }
}
