package com.github.thiskarolgajda.op.core.user.profile;

import com.github.thiskarolgajda.op.core.user.achievements.UserAchievementsInventory;
import com.github.thiskarolgajda.op.core.user.economy.UserEconomyDatabase;
import com.github.thiskarolgajda.op.core.user.invite.UserInvitesInventory;
import com.github.thiskarolgajda.op.core.user.settings.UserSettingsInventory;
import com.github.thiskarolgajda.op.core.user.tags.UserTagsInventory;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.commands.annotations.Command;
import me.opkarol.oplibrary.commands.annotations.Default;
import me.opkarol.oplibrary.commands.annotations.Subcommand;
import me.opkarol.oplibrary.translations.Messages;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@Command("profil")
@SuppressWarnings("unused")
public class ProfileCommand {

    @Default
    public void openProfile(Player player) {
        new UserProfileInventory(player);
    }

    @Subcommand("osiagniecia")
    public void playerAchievements(Player player, OfflinePlayer target) {
        new UserAchievementsInventory(player, target);
    }

    @Subcommand("osiagniecia")
    public void playerAchievements(Player player) {
        new UserAchievementsInventory(player, player);
    }

    @Subcommand("tagi")
    public void playerTags(Player player) {
        new UserTagsInventory(player);
    }

    @Subcommand("otworz")
    public void openProfile(Player player, OfflinePlayer target) {
        if (!Plugin.get(UserEconomyDatabase.class).contains(target.getUniqueId())) {
            Messages.sendMessage("player.cannotFindPlayer", player);
            return;
        }

        new UserProfileInventory(player, target);
    }

    @Subcommand("zaproszenia")
    public void playerInvites(Player player) {
        new UserInvitesInventory(player);
    }

    @Subcommand("ustawienia")
    public void playerSettings(Player player) {
        new UserSettingsInventory(player);
    }
}
