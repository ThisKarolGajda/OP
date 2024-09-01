package com.github.thiskarolgajda.op.core.firstjoin;

import com.github.thiskarolgajda.op.core.chat.OpAsyncPlayerChatEvent;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import me.opkarol.oplibrary.injection.config.Config;
import me.opkarol.oplibrary.injection.messages.Message;
import me.opkarol.oplibrary.listeners.Listener;
import me.opkarol.oplibrary.tools.FormatTool;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FirstJoinChatWelcomeListener extends Listener {
    private static final Pattern MENTION_PATTERN = Pattern.compile("@(\\w+)");

    @Config
    private static List<String> welcomeWords = List.of(
            "siema", "cześć", "czesc", "ceść", "hej", "elo", "siemka", "siemano",
            "witaj", "witajcie", "hejka", "o/", "o7",
            "yo", "salut", "halo", "hallo", "hi", "hello"
    );

    @Message
    public static String welcomeToTheServer = "Witaj na serwerku %name%!";
    @Message
    public static String welcomeNewUserInTheServer = "\n<CEN>&l#!<eda437>Nowa mordka!#!<ebd234>\n<CEN>#<69de1b>Właśnie dołączył do nas #!<eda437>%name%#!<ebd234>#<69de1b>.\n<CEN>#<69de1b>Powitaj go, a napewno zrobi mu się miło!\n&b";

    private static class NewPlayerData {
        long joinTime;
        Set<UUID> mentionedBy;

        NewPlayerData(long joinTime) {
            this.joinTime = joinTime;
            this.mentionedBy = new HashSet<>();
        }
    }

    private final Cache<UUID, NewPlayerData> newPlayerDataCache = CacheBuilder.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();

    @EventHandler(ignoreCancelled = true)
    public void onPlayerFirstJoin(PlayerJoinEvent event) {
        UUID newPlayerId = event.getPlayer().getUniqueId();
        newPlayerDataCache.put(newPlayerId, new NewPlayerData(System.currentTimeMillis()));
        String name = event.getPlayer().getName();
        event.getPlayer().sendMessage(FormatTool.formatMessage(welcomeToTheServer.replace("%name%", name)));
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getUniqueId().equals(newPlayerId)) {
                continue;
            }

            player.sendMessage(FormatTool.formatMessage(welcomeNewUserInTheServer.replace("%name%", name)));
        }
    }

    @EventHandler
    public void onPlayerChat(OpAsyncPlayerChatEvent event) {
        String plainText = ChatColor.stripColor(event.getMessageComponents().stream().map(component -> component.toPlainText()).collect(Collectors.joining("")));
        String firstWord = plainText.split(" ")[0].toLowerCase();
        if (welcomeWords.stream().noneMatch(s -> s.equalsIgnoreCase(firstWord))) {
            return;
        }

        Matcher matcher = MENTION_PATTERN.matcher(plainText);

        while (matcher.find()) {
            String mention = matcher.group(1);
            Player mentionedPlayer = Bukkit.getPlayer(mention);
            if (mentionedPlayer == null) {
                continue;
            }

            NewPlayerData newPlayerData = newPlayerDataCache.getIfPresent(mentionedPlayer.getUniqueId());
            if (newPlayerData != null && System.currentTimeMillis() - newPlayerData.joinTime <= TimeUnit.MINUTES.toMillis(1)) {
                if (!newPlayerData.mentionedBy.contains(event.getPlayer().getUniqueId())) {
                    // fixme reward user for welcoming
                    event.getPlayer().sendMessage(ChatColor.YELLOW + "You mentioned " + mentionedPlayer.getName() + " and will receive a reward!");
                    newPlayerData.mentionedBy.add(event.getPlayer().getUniqueId());
                }
            }
        }
    }
}