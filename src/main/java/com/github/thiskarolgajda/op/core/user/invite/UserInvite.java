package com.github.thiskarolgajda.op.core.user.invite;

import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.database.DatabaseEntity;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserInvite(UUID receiver, UserInviteType type, String object, UUID sender,
                         LocalDateTime creationDate) implements DatabaseEntity<UUID> {

    public String getSenderName() {
        return Bukkit.getOfflinePlayer(sender).getName();
    }

    public void accept() {
        type.accept(this);
        delete();
    }

    public void delete() {
        Plugin.get(UserInviteDatabase.class).removeInvite(receiver, this);
    }

    @Override
    public UUID getId() {
        return receiver;
    }

    public OfflinePlayer getReceiver() {
        return Bukkit.getOfflinePlayer(receiver);
    }
}
