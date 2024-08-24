package com.github.thiskarolgajda.op.core.user.invite;

import lombok.Getter;
import me.opkarol.oplibrary.database.DatabaseEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UserInvites implements DatabaseEntity<UUID> {
    private final UUID uuid;
    @Getter
    private Set<UserInvite> invites;

    public UserInvites(UUID uuid) {
        this.uuid = uuid;
        invites = new HashSet<>();
    }

    public void addInvite(UserInvite invite) {
        if (invites == null) {
            invites = new HashSet<>();
        }

        if (invites.stream()
                .anyMatch(userInvite -> userInvite.sender().equals(invite.sender()))) {
            return;
        }

        invites.add(invite);
    }

    public void removeInvite(UserInvite invite) {
        invites.remove(invite);
    }

    @Override
    public UUID getId() {
        return uuid;
    }
}
