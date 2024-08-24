package com.github.thiskarolgajda.op.core.user.achievements;

import com.github.thiskarolgajda.op.utils.FireworkSpawn;
import me.opkarol.oplibrary.database.DatabaseEntity;
import me.opkarol.oplibrary.translations.Messages;
import me.opkarol.oplibrary.wrappers.OpTitle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static me.opkarol.oplibrary.injection.messages.StringMessage.successSound;

public class UserAchievements implements DatabaseEntity<UUID> {
    private final UUID uuid;
    private final Map<UserAchievementType, LocalDateTime> achievements;

    public UserAchievements(UUID uuid) {
        this.uuid = uuid;
        this.achievements = new HashMap<>();
    }

    public boolean add(UserAchievementType type) {
        if (achievements.containsKey(type)) {
            return false;
        }

        achievements.put(type, LocalDateTime.now());
        displayAchievement(type);
        return true;
    }

    private void displayAchievement(UserAchievementType type) {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            new OpTitle(Messages.getTranslation("achievements.unlocked.title"), Messages.getTranslation("achievements.unlocked.subtitle").replace("%name%", type.getName()), 0, 100, 0)
                    .display(player);
            successSound.play(player);
            new FireworkSpawn().startFireworkShootout(player.getLocation(), 10);
        }
    }

    public Map<UserAchievementType, LocalDateTime> get() {
        return achievements;
    }

    public LocalDateTime get(UserAchievementType type) {
        return achievements.get(type);
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    public boolean has(UserAchievementType type) {
        return achievements.containsKey(type);
    }
}
