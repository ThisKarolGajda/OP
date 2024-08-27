package com.github.thiskarolgajda.op.core.user.economy;

import me.opkarol.oplibrary.database.manager.Database;
import me.opkarol.oplibrary.injection.Inject;

import java.util.Optional;
import java.util.UUID;

public class UserEconomyDatabase extends Database<UUID, UserEconomy> {
    @Inject
    public UserEconomyDatabase() {
        super(UserEconomy.class, UserEconomy[].class);
    }

    public UserEconomy getSafe(UUID uuid) {
        Optional<UserEconomy> optional = get(uuid);
        return optional.orElseGet(() -> new UserEconomy(uuid));

    }
}
