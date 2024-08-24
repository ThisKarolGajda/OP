package com.github.thiskarolgajda.op.core.user.tags;

import lombok.Getter;
import lombok.Setter;
import me.opkarol.oplibrary.database.DatabaseEntity;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UserTags implements DatabaseEntity<UUID> {
    private final UUID uuid;
    @Getter
    private final Set<UserTagType> owned;
    @Getter
    @Setter
    private @Nullable UserTagType selected;

    public UserTags(UUID uuid) {
        this.uuid = uuid;
        owned = new HashSet<>();
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    public boolean isOwned(UserTagType type) {
        return owned.contains(type);
    }

    public void addOwned(UserTagType type) {
        owned.add(type);
    }

    public boolean isSelected(UserTagType type) {
        return selected != null && selected == type;
    }
}
