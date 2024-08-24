package com.github.thiskarolgajda.op.core.user;

import com.github.thiskarolgajda.op.core.user.profile.UserProfileInventory;
import me.opkarol.oplibrary.listeners.Listener;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class PlayerShiftRightClickOnPlayerListener extends Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEvent(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if (!player.isSneaking()) {
            return;
        }

        Entity entity = event.getRightClicked();
        if (!(entity instanceof Player target)) {
            return;
        }

        new UserProfileInventory(player, target);
    }
}
