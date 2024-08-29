package com.github.thiskarolgajda.op.core.chat;

import com.github.thiskarolgajda.op.core.user.profile.UserProfile;
import com.github.thiskarolgajda.op.core.user.profile.UserProfileDatabase;
import com.github.thiskarolgajda.op.core.user.settings.UserSettingType;
import com.github.thiskarolgajda.op.core.user.tags.UserTags;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.misc.StringUtil;
import me.opkarol.oplibrary.misc.Tuple;
import me.opkarol.oplibrary.tools.FormatTool;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

public class PlayerChatHelper {
    public static @Nullable String getMaxPlayerPrefix(final @NotNull Player player) {
        return player.getEffectivePermissions().stream().map(permission -> {
            if (permission.getPermission().startsWith("prefix.")) {
                String[] args = permission.getPermission().split("\\.");
                if (args.length == 3) {
                    int weight = StringUtil.getIntFromString(args[1]);
                    return Tuple.of(weight, args[2]);
                }
            }
            return Tuple.of(0, "");
        }).max(Comparator.comparing(Tuple::first)).map(Tuple::second).orElse(null);
    }

    public static @NotNull String getMessageCode(Player player) {
        return FormatTool.formatMessage("&7"); //fixme implement
    }

    public static @NotNull String getOpenProfileMessage(String name) {
        return "&7Kliknij aby otworzyć profil\ngracza #<FCAA4E><SL>" + name + "<SL>&7."; //fixme implement
    }

    public static void sendMessage(TextComponent allComponents) {
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            UserProfile profile = Plugin.get(UserProfileDatabase.class).getSafe(player1);
            if (profile.getSettings().isActive(UserSettingType.DISPLAY_JOIN_MESSAGE)) {
                player1.spigot().sendMessage(allComponents);
            }
        }

        Bukkit.getConsoleSender().spigot().sendMessage(allComponents);
    }

    public static TextComponent buildPlayerName(Player player) {
        TextComponent component = new TextComponent(FormatTool.formatMessage(getMessageCode(player) + player.getName() + " "));
        setComponentProfileOpenAction(component, player, getOpenProfileMessage(player.getName()));
        return component;
    }

    public static BaseComponent buildPlayerTag(Player player, UserTags tags) {
        BaseComponent component = TextComponent.fromLegacy(ChatColor.of("#FCAA4E") + FormatTool.formatMessage(tags.getSelected().getName()) + " ");
        setComponentProfileOpenAction(component, player, getOpenProfileMessage(player.getName()));
        return component;
    }

    public static void setComponentProfileOpenAction(BaseComponent component, Player player, String message) {
        component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/profil otworz " + player.getName()));
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent(FormatTool.formatMessage(message))}));
    }
}
