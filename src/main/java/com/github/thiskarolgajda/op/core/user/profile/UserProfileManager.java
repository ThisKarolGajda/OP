package com.github.thiskarolgajda.op.core.user.profile;

import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.listeners.Listener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

public class UserProfileManager extends Listener {
    private final Map<UUID, Long> joinTimestamps = new HashMap<>();

    public UserProfileManager() {
        for (UUID uuid : Bukkit.getOnlinePlayers().stream().map(Entity::getUniqueId).toList()) {
            joinTimestamps.put(uuid, System.currentTimeMillis());
        }

        Plugin.runTimer(() -> {
            // Refresh time for all players every 3 minutes
            Plugin.run(() -> {
                Set<UUID> keysCopy = new HashSet<>(joinTimestamps.keySet());
                for (UUID uuid : keysCopy) {
                    setPlayedTime(uuid);
                    joinTimestamps.put(uuid, System.currentTimeMillis());
                }
            });
        }, 20 * 60 * 3);

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        joinTimestamps.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
        Plugin.get(UserProfileDatabase.class).set(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        setPlayedTime(event.getPlayer().getUniqueId());
    }

    private void setPlayedTime(UUID uuid) {
        long joinTimestamp = joinTimestamps.remove(uuid);
        long playedTime = System.currentTimeMillis() - joinTimestamp;

        UserProfile userProfile = Plugin.get(UserProfileDatabase.class).getSafe(uuid);
        userProfile.increasePlayedTimeInSeconds((int) playedTime / 1000);
        Plugin.get(UserProfileDatabase.class).save(userProfile);
    }
}
