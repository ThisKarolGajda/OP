package com.github.thiskarolgajda.op.core.chat;

import com.github.thiskarolgajda.op.core.user.tags.UserTags;
import com.github.thiskarolgajda.op.core.user.tags.UserTagsDatabase;
import com.github.thiskarolgajda.op.permission.PermissionType;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.extensions.PlaceholderAPI;
import me.opkarol.oplibrary.injection.config.Config;
import me.opkarol.oplibrary.listeners.Listener;
import me.opkarol.oplibrary.tools.FormatTool;
import me.opkarol.oplibrary.wrappers.OpSound;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.github.thiskarolgajda.op.core.chat.PlayerChatHelper.*;
import static me.opkarol.oplibrary.injection.formatter.DefaultTextFormatter.primaryColor;

public class PlayerChatListener extends Listener {
    @Config
    private static OpSound mentionSound = new OpSound(Sound.BLOCK_ANVIL_DESTROY);
    @Config
    private static final Map<String, String> EMOJI_MAP = createEmojiMap();
    private static final Pattern MENTION_PATTERN = Pattern.compile("@(\\w+)");

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);

        Player player = event.getPlayer();
        TextComponent allComponents = new TextComponent();

        String prefix = getMaxPlayerPrefix(player);
        if (prefix != null && !prefix.isEmpty()) {
            allComponents.addExtra(PlaceholderAPI.set(player, prefix) + " ");
        }

        UserTags tags = Plugin.get(UserTagsDatabase.class).getSafe(player.getUniqueId());
        if (tags.getSelected() != null) {
            allComponents.addExtra(buildPlayerTag(player, tags));
        }

        allComponents.addExtra(buildPlayerName(player));

        String message = event.getMessage();
        message = replaceEmojis(message);
        if (PermissionType.USE_COLOR_CODES.hasPermission(player)) {
            message = FormatTool.formatMessage(message);
        }

        BaseComponent textComponent = new TextComponent(FormatTool.formatMessage("&8➻ "));
        allComponents.addExtra(textComponent);

        for (BaseComponent component : processMentions(player, message)) {
            allComponents.addExtra(component);
        }

        sendMessage(allComponents);
    }

    private static @NotNull Map<String, String> createEmojiMap() {
        Map<String, String> map = new HashMap<>();
        map.put(":)", "\uD83D\uDE42");
        map.put(";)", "\uD83D\uDE09");
        map.put(":D", "\uD83D\uDE04");
        map.put(":C", "\uD83D\uDE26");
        map.put(":<", "\uD83D\uDE2D");
        map.put("<3", "❤");
        map.put("</3", "\uD83D\uDC94");
        return map;
    }

    private String replaceEmojis(String message) {
        for (Map.Entry<String, String> entry : EMOJI_MAP.entrySet()) {
            message = message.replace(entry.getKey(), entry.getValue());
        }
        return message;
    }

    private @NotNull List<BaseComponent> processMentions(Player player, String message) {
        Matcher matcher = MENTION_PATTERN.matcher(message);
        List<BaseComponent> components = new ArrayList<>();
        int lastEnd = 0;

        Set<String> onlinePlayerNames = Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .collect(Collectors.toSet());

        while (matcher.find()) {
            if (lastEnd < matcher.start()) {
                components.add(new TextComponent(getMessageCode(player) + message.substring(lastEnd, matcher.start())));
            }

            String mentionedName = matcher.group(1);
            Player mentionedPlayer = Bukkit.getPlayer(mentionedName);

            if (mentionedPlayer != null) {
                components.add(addMentionComponent(mentionedPlayer));
            } else {
                for (String onlineName : onlinePlayerNames) {
                    if (onlineName.equalsIgnoreCase(mentionedName) || onlineName.toLowerCase().startsWith(mentionedName.toLowerCase())) {
                        mentionedPlayer = Bukkit.getPlayer(onlineName);
                        components.add(addMentionComponent(mentionedPlayer));
                        break;
                    }
                }
                if (mentionedPlayer == null) {
                    components.add(new TextComponent("@" + mentionedName));
                }
            }

            lastEnd = matcher.end();
        }

        if (lastEnd < message.length()) {
            components.add(new TextComponent(getMessageCode(player) + message.substring(lastEnd)));
        }

        return components;
    }

    private BaseComponent addMentionComponent(@NotNull Player mentionedPlayer) {
        mentionSound.play(mentionedPlayer);
        BaseComponent mentionComponent = TextComponent.fromLegacy(FormatTool.formatMessage(primaryColor.toCode() + "@" + mentionedPlayer.getName()));
        setComponentProfileOpenAction(mentionComponent, mentionedPlayer, getOpenProfileMessage(mentionedPlayer.getName()));
        return mentionComponent;
    }
}