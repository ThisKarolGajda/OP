package com.github.thiskarolgajda.op.core.admin;

import com.github.thiskarolgajda.op.core.user.achievements.UserAchievementType;
import com.github.thiskarolgajda.op.core.user.achievements.UserAchievements;
import com.github.thiskarolgajda.op.core.user.achievements.UserAchievementsDatabase;
import com.github.thiskarolgajda.op.core.user.economy.MoneyTextFormatter;
import com.github.thiskarolgajda.op.core.user.economy.UserEconomy;
import com.github.thiskarolgajda.op.core.user.economy.UserEconomyDatabase;
import com.github.thiskarolgajda.op.core.user.tags.UserTagType;
import com.github.thiskarolgajda.op.core.user.tags.UserTags;
import com.github.thiskarolgajda.op.core.user.tags.UserTagsDatabase;
import com.github.thiskarolgajda.op.core.warps.SelectWarp;
import com.github.thiskarolgajda.op.core.warps.WarpsDatabase;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.commands.annotations.Command;
import me.opkarol.oplibrary.commands.annotations.Permission;
import me.opkarol.oplibrary.commands.annotations.Subcommand;
import me.opkarol.oplibrary.location.OpLocation;
import me.opkarol.oplibrary.misc.StringUtil;
import me.opkarol.oplibrary.translations.Messages;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Command("admin")
@Permission("opsurvival.admin")
public class AdminCommand {

    @Subcommand("osiagniecia odblokuj-wszystkie")
    public void setAllAchievements(Player player, @NotNull OfflinePlayer target) {
        UserAchievements achievements = Plugin.get(UserAchievementsDatabase.class).getSafe(target.getUniqueId());
        for (UserAchievementType type : UserAchievementType.values()) {
            if (achievements.add(type)) {
                player.sendMessage("Dodano osiągnięcie " + type.getName() + " dla gracza " + target.getName());
            }
        }

        Plugin.get(UserAchievementsDatabase.class).save(achievements);
    }

    @Subcommand("tagi odblokuj-wszystkie")
    public void setAllTags(Player player, OfflinePlayer target) {
        UserTags tags = Plugin.get(UserTagsDatabase.class).getSafe(target.getUniqueId());
        for (UserTagType type : UserTagType.values()) {
            tags.addOwned(type);
            player.sendMessage("Dodano tag " + type.getName() + " dla gracza " + target.getName());
        }

        Plugin.get(UserTagsDatabase.class).save(tags);
    }

    @Subcommand("config reload")
    public void configReload(Player player) {
        Plugin.reload();
        player.sendMessage("Przeładowano config!");
    }

    @Subcommand("pieniadze dodaj")
    public void addMoney(Player player, String money) {
        double amount = StringUtil.getDoubleFromString(money);
        UserEconomy economy = Plugin.get(UserEconomyDatabase.class).getSafe(player.getUniqueId());
        economy.add(amount);
        Plugin.get(UserEconomyDatabase.class).save(economy);
        Messages.sendMessage("economy.received", player, Map.of("%money%", MoneyTextFormatter.format(amount), "%player%", "CONSOLE"));
    }

    @Subcommand("warp zmien-nazwe")
    public void changeWarpName(Player player, String warpName) {
        SelectWarp.select(player, (warp) -> {
            warp.setName(warpName);
            Plugin.get(WarpsDatabase.class).save(warp);
        });
    }

    @Subcommand("warp ustaw")
    public void setWarpLocation(Player player) {
        SelectWarp.select(player, (warp) -> {
            warp.setLocation(new OpLocation(player.getLocation()));
            Plugin.get(WarpsDatabase.class).save(warp);
        });
    }

    @Subcommand("warp przelacz-widocznosc")
    public void toggleWarpVisibility(Player player) {
        SelectWarp.select(player, (warp) -> {
            warp.setVisible(!warp.isVisible());
            Plugin.get(WarpsDatabase.class).save(warp);
        });
    }
}
