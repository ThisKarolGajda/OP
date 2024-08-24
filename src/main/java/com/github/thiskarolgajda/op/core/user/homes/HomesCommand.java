package com.github.thiskarolgajda.op.core.user.homes;

import com.github.thiskarolgajda.op.core.teleportation.TeleportationManager;
import com.github.thiskarolgajda.op.core.user.homes.inventories.HomesInventory;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.commands.annotations.Command;
import me.opkarol.oplibrary.commands.annotations.Default;
import me.opkarol.oplibrary.commands.annotations.NoUse;
import me.opkarol.oplibrary.translations.Messages;
import org.bukkit.entity.Player;

import java.util.Map;

@Command("dom")
public class HomesCommand {

    @Default
    public void defaultCommand(Player player, String name) {
        UserHomes homes = Plugin.get(UserHomesDatabase.class).getSafe(player.getUniqueId());
        UserHome home = homes.getHome(name);
        if (home == null) {
            Messages.sendMessage("homes.invalid", player, Map.of(
                    "%homes%", homes.getHomesNames()
            ));
            return;
        }

        TeleportationManager.teleport(player, home);
    }

    @NoUse
    public void noUse(Player player) {
        new HomesInventory(player);
    }

}
