package com.github.thiskarolgajda.op.core.user.settings;

import me.opkarol.oplibrary.commands.annotations.Command;
import me.opkarol.oplibrary.commands.annotations.Default;
import me.opkarol.oplibrary.commands.annotations.NoUse;
import org.bukkit.entity.Player;

@Command("ustawienia")
public class SettingsCommand {

    @Default
    @NoUse
    public void defaultCommand(Player player) {
        new UserSettingsInventory(player);
    }
}
