package com.github.thiskarolgajda.op.core.user.tags;

import me.opkarol.oplibrary.commands.annotations.Command;
import me.opkarol.oplibrary.commands.annotations.Default;
import me.opkarol.oplibrary.commands.annotations.NoUse;
import org.bukkit.entity.Player;

@Command("tagi")
public class TagsCommand {

    @NoUse
    @Default
    public void defaultCommand(Player player) {
        new UserTagsInventory(player);
    }
}
