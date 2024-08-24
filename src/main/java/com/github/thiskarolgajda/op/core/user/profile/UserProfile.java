package com.github.thiskarolgajda.op.core.user.profile;

import com.github.thiskarolgajda.op.core.user.achievements.UserAchievementType;
import com.github.thiskarolgajda.op.core.user.settings.UserSettings;
import lombok.Getter;
import lombok.Setter;
import me.opkarol.oplibrary.database.DatabaseEntity;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserProfile implements DatabaseEntity<UUID> {
    private final UUID uuid;
    @Getter
    private final String name;
    @Getter
    private final LocalDateTime firstJoin;
    @Getter
    private final UserSettings settings;
    @Getter
    @Setter
    private int playedTimeInSeconds;
    @Getter
    @Setter
    private LocalDateTime lastJoin;
    private @Nullable UserAchievementType selectedAchievement;

    public UserProfile(UUID uuid, String name, LocalDateTime firstJoin) {
        this.uuid = uuid;
        this.name = name;
        this.firstJoin = firstJoin;
        this.lastJoin = firstJoin;
        this.playedTimeInSeconds = 0;
        this.settings = new UserSettings();
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    public void increasePlayedTimeInSeconds(int playedTimeInSeconds) {
        this.playedTimeInSeconds += playedTimeInSeconds;
    }

    public String getPlayedTime() {
        int hours = playedTimeInSeconds / 3600;
        int minutes = (playedTimeInSeconds % 3600) / 60;
        int seconds = playedTimeInSeconds % 60;

        return hours + "h " + minutes + "m " + seconds + "s";
    }

    public @Nullable UserAchievementType getSelectedAchievement() {
        return selectedAchievement;
    }

    public void setSelectedAchievement(@Nullable UserAchievementType selectedAchievement) {
        this.selectedAchievement = selectedAchievement;
    }

}
