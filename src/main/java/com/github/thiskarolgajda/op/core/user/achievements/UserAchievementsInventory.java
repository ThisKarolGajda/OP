package com.github.thiskarolgajda.op.core.user.achievements;

import com.github.thiskarolgajda.op.core.user.profile.UserProfile;
import com.github.thiskarolgajda.op.core.user.profile.UserProfileDatabase;
import com.github.thiskarolgajda.op.core.user.profile.UserProfileInventory;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.translations.Messages;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.Map;

import static com.github.thiskarolgajda.op.utils.DateFormatter.STYLE_FORMATTER;
import static me.opkarol.oplibrary.injection.messages.StringMessage.*;
import static me.opkarol.oplibrary.translations.Messages.sendMessage;

public class UserAchievementsInventory extends ChestInventory {

    public UserAchievementsInventory(Player player, OfflinePlayer target) {
        super(5, "Osiągnięcia gracza " + target.getName());
        setListPattern(player, () -> new UserProfileInventory(player, target));

        UserAchievements achievements = Plugin.get(UserAchievementsDatabase.class).getSafe(target.getUniqueId());
        for (Map.Entry<UserAchievementType, LocalDateTime> entry : achievements.get().entrySet()) {
            UserAchievementType type = entry.getKey();
            LocalDateTime date = entry.getValue();
            setNextFree(item("achievement"), type.getItem(), event -> {
                        event.setCancelled(true);
                        if (player.getUniqueId().equals(target.getUniqueId())) {
                            UserProfile profile = Plugin.get(UserProfileDatabase.class).getSafe(target.getUniqueId());
                            profile.setSelectedAchievement(type);
                            successSound.play(player);
                            Plugin.get(UserProfileDatabase.class).saveAsync(profile);
                            new UserAchievementsInventory(player, target);
                            Messages.sendMessage("achievements.set", player, Map.of(
                                    "%name%", type.getName()
                            ));
                        } else {
                            sendMessage("player.onlyOwnerCanDoThat", player);
                            errorSound.play(player);
                        }

                    }, Map.of(
                            "%name%", type.getName(),
                            "%description%", type.getDescription(),
                            "%date%", date.format(STYLE_FORMATTER),
                            "%achievement_gained_all_players%", String.valueOf(Plugin.get(UserAchievementsDatabase.class).getTotalAmountOfPlayersWithAchievement(type)),
                            "%achievement_gained_players%", String.valueOf(Plugin.get(UserAchievementsDatabase.class).getTotalAmountOfPlayersWithAchievementUnlockedYesterday(type)),
                            "%achievement_gained_all_players_percentage%", Plugin.get(UserAchievementsDatabase.class).getTotalAmountOfPlayersPercentage(type),
                            "%achievement_gained_players_percentage%", Plugin.get(UserAchievementsDatabase.class).getTotalAmountOfPlayersPercentageYesterday(type)
                    )
            );
        }

        open(player);
    }

    public UserAchievementsInventory(Player player) {
        this(player, player);
    }
}
    