package com.github.thiskarolgajda.op.core.user.invite;

import me.opkarol.oplibrary.database.manager.Database;
import me.opkarol.oplibrary.translations.Messages;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class UserInviteDatabase extends Database<UUID, UserInvites> {
    public UserInviteDatabase() {
        super(UserInvites.class, UserInvites[].class);
    }

    public Set<UserInvite> getInvites(UUID uuid) {
        Optional<UserInvites> optional = get(uuid);
        if (optional.isEmpty()) {
            return new HashSet<>();
        }

        return optional.get().getInvites();
    }

    public void removeInvite(UUID receiver, UserInvite userInvite) {
        Optional<UserInvites> optional = get(receiver);
        if (optional.isEmpty()) {
            return;
        }

        UserInvites userInvites = optional.get();
        userInvites.removeInvite(userInvite);
        save(userInvites);
    }

    public void addPlotInvite(UUID senderUUID, UUID targetUUID, @NotNull UUID plotUUID) {
        UserInvite userInvite = new UserInvite(targetUUID, UserInviteType.PLOT_INVITE, plotUUID.toString(), senderUUID, LocalDateTime.now());
        Optional<UserInvites> optional = get(targetUUID);
        UserInvites userInvites;
        userInvites = optional.orElseGet(() -> new UserInvites(targetUUID));
        userInvites.addInvite(userInvite);
        save(userInvites);

        OfflinePlayer player = Bukkit.getOfflinePlayer(targetUUID);
        if (player.getPlayer() != null) {
            Messages.sendMessage("player.youHaveInvites", player.getPlayer());
        }
    }
}
