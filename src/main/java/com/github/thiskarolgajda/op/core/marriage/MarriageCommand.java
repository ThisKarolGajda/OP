package com.github.thiskarolgajda.op.core.marriage;

import me.opkarol.oplibrary.commands.annotations.Command;
import me.opkarol.oplibrary.commands.annotations.Default;
import me.opkarol.oplibrary.commands.annotations.NoUse;
import org.bukkit.entity.Player;

@Command("slub")
public class MarriageCommand {

    @Default
    @NoUse
    public void defaultMarryCommand(Player player) {
        new MarriageInventory(player);
    }
}
