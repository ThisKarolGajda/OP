package com.github.thiskarolgajda.op.core.warps;

import me.opkarol.oplibrary.commands.annotations.*;
import org.bukkit.entity.Player;

@Command("warpy")
public class WarpsCommand {

    @Default
    @NoUse
    public void warpsCommand(Player player) {
        SelectWarp.teleport(player);
    }

    @Subcommand("dzialki")
    @Cooldown(2)
    public void plotWarpsCommand(Player player) {
//        new PlotWarpsInventory(player);
    }
}
