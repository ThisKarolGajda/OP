package com.github.thiskarolgajda.op.core.user.tags;

import me.opkarol.oplibrary.database.manager.Database;

import java.util.Optional;
import java.util.UUID;

public class UserTagsDatabase extends Database<UUID, UserTags> {

    public UserTagsDatabase() {
        super(UserTags.class, UserTags[].class);
    }

    public UserTags getSafe(UUID uuid) {
        Optional<UserTags> optional = get(uuid);
        return optional.orElse(new UserTags(uuid));
    }
}
