package com.github.thiskarolgajda.op.core.marriage;

import me.opkarol.oplibrary.database.manager.Database;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class MarriageDatabase extends Database<UUID, Marriage> {

    public MarriageDatabase() {
        super(Marriage.class, Marriage[].class);
    }

    public @Nullable Marriage getMarriage(UUID uuid) {
        for (Marriage marriage : getAll()) {
            UUID[] users = marriage.getUsers();
            for (UUID u : users) {
                if (u.equals(uuid)) {
                    return marriage;
                }
            }
        }

        return null;
    }
}
