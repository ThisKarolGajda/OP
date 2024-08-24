package com.github.thiskarolgajda.op.core.user.achievements;

import com.github.thiskarolgajda.op.permission.PermissionType;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.commands.annotations.Command;
import me.opkarol.oplibrary.commands.annotations.NoUse;
import me.opkarol.oplibrary.misc.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@Command("osiagniecia")
public class UserAchievementsCommand {

    @NoUse
    public void defaultCommand(Player player, String[] args) {
        if (args.length == 0) {
            new UserAchievementsInventory(player);
            return;
        }

        if (args.length == 1) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            if (target.hasPlayedBefore()) {
                new UserAchievementsInventory(player, target);
            }
        }

        if (args.length == 3 && PermissionType.ADMIN.hasPermission(player)) {
            if (args[0].equalsIgnoreCase("odblokuj")) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                if (target.hasPlayedBefore()) {
                    UserAchievements achievements = Plugin.get(UserAchievementsDatabase.class).getSafe(target.getUniqueId());
                    String achievement = args[2];
                    StringUtil.getEnumValue(achievement, UserAchievementType.class).ifPresent(achievements::add);
                    Plugin.get(UserAchievementsDatabase.class).save(achievements);
                }
            }
        }
    }
}
