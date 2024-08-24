package com.github.thiskarolgajda.op.core.user.profile;

import com.github.thiskarolgajda.op.core.user.achievements.UserAchievementType;
import com.github.thiskarolgajda.op.core.user.achievements.UserAchievementsDatabase;
import com.github.thiskarolgajda.op.core.user.achievements.UserAchievementsInventory;
import com.github.thiskarolgajda.op.core.user.economy.MoneyTextFormatter;
import com.github.thiskarolgajda.op.core.user.economy.UserEconomyDatabase;
import com.github.thiskarolgajda.op.core.user.economy.UserEconomyManager;
import com.github.thiskarolgajda.op.core.user.tags.UserTagType;
import com.github.thiskarolgajda.op.core.user.tags.UserTagsDatabase;
import com.github.thiskarolgajda.op.core.user.tags.UserTagsInventory;
import com.github.thiskarolgajda.op.utils.HeadsType;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.injection.formatter.LoreBuilder;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.misc.VillagerHeadGenerator;
import me.opkarol.oplibrary.tools.Heads;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.github.thiskarolgajda.op.utils.DateFormatter.STYLE_FORMATTER;
import static me.opkarol.oplibrary.injection.messages.StringMessage.errorSound;
import static me.opkarol.oplibrary.translations.Messages.sendMessage;

public class UserProfileInventory extends ChestInventory {

    public UserProfileInventory(Player player, OfflinePlayer target) {
        super(3, "Profil gracza " + player.getName());

        UUID uuid = target.getUniqueId();
        UserTagType tag = Plugin.get(UserTagsDatabase.class).getSafe(uuid).getSelected();
        setItem(item("%name%", List.of("Pierwsze dołączenie: %first_join_date% Ostatnia gra: %last_join_date%", "Czas gry: %played_time%", "Pieniądze: %vault%")), 10, Heads.get(target), event -> event.setCancelled(true), Map.ofEntries(
                Map.entry("%name%", target.getName() == null ? "" : target.getName()),
                Map.entry("%uuid%", target.getUniqueId().toString()),
                Map.entry("%first_join_date%", Plugin.get(UserProfileDatabase.class).getSafe(uuid).getFirstJoin().format(STYLE_FORMATTER)),
                Map.entry("%last_join_date%", Plugin.get(UserProfileDatabase.class).getSafe(uuid).getLastJoin().format(STYLE_FORMATTER)),
                Map.entry("%played_time%", Plugin.get(UserProfileDatabase.class).getSafe(uuid).getPlayedTime()),
                Map.entry("%vault%", MoneyTextFormatter.format(Plugin.get(UserEconomyDatabase.class).getSafe(uuid).getGold())),
                Map.entry("%ranking%", String.valueOf(Plugin.get(UserEconomyManager.class).getPlayerPositionInEconomyRanking(uuid))),
                Map.entry("%all_ranking%", String.valueOf(Plugin.get(UserEconomyManager.class).getPlayersInRanking())),
                Map.entry("%tag%", (tag == null ? "" : tag.getName() + " "))
        ));

        UserAchievementType achievementType = Plugin.get(UserProfileDatabase.class).getSafe(uuid).getSelectedAchievement();
        LocalDateTime selectedAchievement = null;
        if (achievementType != null) {
            selectedAchievement = Plugin.get(UserAchievementsDatabase.class).getSafe(uuid).get(achievementType);
        }

        setItem(item("Osiągnięcia", LoreBuilder.create("Wybrane osiągnięcie: %selected_achievement%\nZdobyte: %selected_achievement_date%", "Osiągnięto: %achievements% na %total_achievements%", "Łącznie graczy zdobyło: %achievement_gained_all_players% (%achievement_gained_all_players_percentage%)", "Graczy zdobyło wczoraj: %achievement_gained_players% (%achievement_gained_players_percentage%)").anyMouseButtonText("zmienić")), 13, achievementType == null ? HeadsType.ACHIEVEMENTS.getHead() : achievementType.getItem(), event -> {
            event.setCancelled(true);
            new UserAchievementsInventory(player, target);
        }, Map.of(
                "%achievements%", String.valueOf(Plugin.get(UserAchievementsDatabase.class).getSafe(uuid).get().size()),
                "%total_achievements%", String.valueOf(UserAchievementType.values().length),
                "%selected_achievement%", achievementType == null ? "---" : achievementType.getName(),
                "%achievement_gained_all_players%", String.valueOf(Plugin.get(UserAchievementsDatabase.class).getTotalAmountOfPlayersWithAchievement(achievementType)),
                "%achievement_gained_players%", String.valueOf(Plugin.get(UserAchievementsDatabase.class).getTotalAmountOfPlayersWithAchievementUnlockedYesterday(achievementType)),
                "%achievement_gained_all_players_percentage%", Plugin.get(UserAchievementsDatabase.class).getTotalAmountOfPlayersPercentage(achievementType),
                "%achievement_gained_players_percentage%", Plugin.get(UserAchievementsDatabase.class).getTotalAmountOfPlayersPercentageYesterday(achievementType),
                "%selected_achievement_date%", selectedAchievement == null ? "" : selectedAchievement.format(STYLE_FORMATTER)
        ));

        UserTagType tagType = Plugin.get(UserTagsDatabase.class).getSafe(uuid).getSelected();
        setItem(item("tags"), 16, tagType == null ? HeadsType.NAME_TAG.getHead() : VillagerHeadGenerator.getRandomHead(tagType.getName()), event -> {
            event.setCancelled(true);
            if (player.getUniqueId().equals(target.getUniqueId())) {
                new UserTagsInventory(player);
            } else {
                sendMessage("player.onlyOwnerCanDoThat", player);
                errorSound.play(player);
            }
        }, Map.of(
                "%selected_tag%", tagType == null ? "---" : tagType.getName()
        ));

        fillEmptyWithBlank();
        open(player);
    }

    public UserProfileInventory(Player player) {
        this(player, player);
    }
}
    