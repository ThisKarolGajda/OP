package com.github.thiskarolgajda.op.core.user.invite;

import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.listeners.Listener;
import me.opkarol.oplibrary.translations.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class UserInviteListener extends Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        boolean hasInvites = !Plugin.get(UserInviteDatabase.class).getInvites(player.getUniqueId()).isEmpty();
        if (hasInvites) {
            Messages.sendMessage("player.youHaveInvites", player);
        }
    }
}
