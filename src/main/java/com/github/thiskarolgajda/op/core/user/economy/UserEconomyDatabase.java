package com.github.thiskarolgajda.op.core.user.economy;

import me.opkarol.oplibrary.database.manager.Database;

import java.util.Optional;
import java.util.UUID;

public class UserEconomyDatabase extends Database<UUID, UserEconomy> {
    public UserEconomyDatabase() {
        super(UserEconomy.class, UserEconomy[].class);
    }

    public UserEconomy getSafe(UUID uuid) {
        Optional<UserEconomy> optional = get(uuid);
        return optional.orElseGet(() -> new UserEconomy(uuid));

    }
}
