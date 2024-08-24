package com.github.thiskarolgajda.op.core.user.achievements;

import me.opkarol.oplibrary.database.manager.Database;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class UserAchievementsDatabase extends Database<UUID, UserAchievements> {

    public UserAchievementsDatabase() {
        super(UserAchievements.class, UserAchievements[].class);
    }

    public UserAchievements getSafe(UUID uuid) {
        Optional<UserAchievements> optional = get(uuid);
        return optional.orElseGet(() -> {
            UserAchievements achievements = new UserAchievements(uuid);
            save(achievements);
            return achievements;
        });
    }

    public int getTotalAmountOfPlayersWithAchievement(UserAchievementType type) {
        int total = 0;

        for (UserAchievements achievements : getAll()) {
            if (achievements.has(type)) {
                total++;
            }
        }

        return total;
    }

    public int getTotalAmountOfPlayersWithAchievementUnlockedYesterday(UserAchievementType type) {
        int total = 0;

        for (UserAchievements achievements : getAll()) {
            if (achievements.has(type)) {
                LocalDateTime dateTime = achievements.get(type).plusDays(1);
                LocalDateTime now = LocalDateTime.now();
                if (now.getDayOfMonth() == dateTime.getDayOfMonth() && now.getMonth() == dateTime.getMonth() && now.getYear() == dateTime.getYear()) {
                    total++;
                }
            }
        }

        return total;
    }

    public String getTotalAmountOfPlayersPercentage(UserAchievementType type) {
        double percentage = (double) getTotalAmountOfPlayersWithAchievement(type) / getAll().size() * 100;
        return String.format("%.2f%%", percentage);
    }

    public String getTotalAmountOfPlayersPercentageYesterday(UserAchievementType type) {
        double percentage = (double) getTotalAmountOfPlayersWithAchievementUnlockedYesterday(type) / getAll().size() * 100;
        return String.format("%.2f%%", percentage);
    }
}
