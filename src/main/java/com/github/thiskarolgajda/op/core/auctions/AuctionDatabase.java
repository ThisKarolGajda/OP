package com.github.thiskarolgajda.op.core.auctions;

import com.github.thiskarolgajda.op.core.warps.WarpsDatabase;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.database.manager.Database;

import java.util.UUID;

public class AuctionDatabase extends Database<UUID, AuctionObject> {

    public AuctionDatabase() {
        super(AuctionObject.class, AuctionObject[].class);
    }

    public UUID getUnusedUUID() {
        UUID uuid;
        do {
            uuid = UUID.randomUUID();
        } while (Plugin.get(WarpsDatabase.class).contains(uuid));

        return uuid;
    }
}
