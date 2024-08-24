package com.github.thiskarolgajda.op.core.user.settings;

import com.github.thiskarolgajda.op.core.user.profile.UserProfile;
import com.github.thiskarolgajda.op.core.user.profile.UserProfileDatabase;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.misc.Books;
import me.opkarol.oplibrary.misc.StringIconUtil;
import org.bukkit.entity.Player;

import java.util.Map;

public class UserSettingsInventory extends ChestInventory {

    public UserSettingsInventory(Player player) {
        super(5, "user_settings");
        setListPattern(player);

        UserProfile userProfile = Plugin.get(UserProfileDatabase.class).getSafe(player);
        for (UserSettingType setting : UserSettingType.values()) {
            setNextFree(item("setting"), Books.getRandomHead(setting.name()), event -> {
                event.setCancelled(true);
                userProfile.getSettings().toggle(setting);
                new UserSettingsInventory(player);
            }, Map.of(
                    "%name%", setting.getName(),
                    "%selected%", StringIconUtil.getReturnedEmojiFromBoolean(userProfile.getSettings().isActive(setting))
            ));
        }

        open(player);
    }
}
    