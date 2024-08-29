package com.github.thiskarolgajda.op.core.chat;

import com.github.thiskarolgajda.op.core.user.tags.UserTags;
import com.github.thiskarolgajda.op.core.user.tags.UserTagsDatabase;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.extensions.PlaceholderAPI;
import me.opkarol.oplibrary.listeners.Listener;
import me.opkarol.oplibrary.tools.FormatTool;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.github.thiskarolgajda.op.core.chat.PlayerChatHelper.*;

public class PlayerJoinAndQuitChatListener extends Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        handle(event.getPlayer(), "&8[&a+&8] ");
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerLeave(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        handle(event.getPlayer(), "&8[&c-&8] ");
    }

    private void handle(Player player, String startMessage) {
        TextComponent allComponents = new TextComponent(FormatTool.formatMessage(startMessage));

        String prefix = getMaxPlayerPrefix(player);
        if (prefix != null && !prefix.isEmpty()) {
            allComponents.addExtra(PlaceholderAPI.set(player, prefix) + " ");
        }

        UserTags tags = Plugin.get(UserTagsDatabase.class).getSafe(player.getUniqueId());
        if (tags.getSelected() != null) {
            allComponents.addExtra(buildPlayerTag(player, tags));
        }

        allComponents.addExtra(buildPlayerName(player));
        sendMessage(allComponents);
    }
}
