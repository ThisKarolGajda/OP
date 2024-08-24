package com.github.thiskarolgajda.op.core.user.profile;

import me.opkarol.oplibrary.database.manager.Database;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserProfileDatabase extends Database<UUID, UserProfile> {
    public UserProfileDatabase() {
        super(UserProfile.class, UserProfile[].class);
    }

    public void set(@NotNull OfflinePlayer player) {
        if (get(player.getUniqueId()).isEmpty()) {
            UserProfile userProfile = new UserProfile(player.getUniqueId(), player.getName(), LocalDateTime.now());
            save(userProfile);
        } else {
            UserProfile userProfile = get(player.getUniqueId()).get();
            userProfile.setLastJoin(LocalDateTime.now());
            save(userProfile);
        }
    }

    public @NotNull UserProfile getSafe(@NotNull UUID uuid) {
        return get(uuid).orElse(new UserProfile(uuid, Bukkit.getPlayer(uuid) == null ? "" : Bukkit.getPlayer(uuid).getName(), LocalDateTime.now()));
    }

    public UserProfile getSafe(OfflinePlayer player) {
        return getSafe(player.getUniqueId());
    }

    @Override
    public boolean useMultiFilesForJSON() {
        return true;
    }
}
