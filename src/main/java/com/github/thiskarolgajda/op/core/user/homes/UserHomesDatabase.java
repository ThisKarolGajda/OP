package com.github.thiskarolgajda.op.core.user.homes;

import me.opkarol.oplibrary.database.manager.Database;

import java.util.UUID;

public class UserHomesDatabase extends Database<UUID, UserHomes> {
    public UserHomesDatabase() {
        super(UserHomes.class, UserHomes[].class);
    }

    public UserHomes getSafe(UUID uuid) {
        return get(uuid).orElse(new UserHomes(uuid));
    }

    @Override
    public boolean useMultiFilesForJSON() {
        return true;
    }
}
