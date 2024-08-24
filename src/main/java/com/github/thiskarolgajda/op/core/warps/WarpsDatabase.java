package com.github.thiskarolgajda.op.core.warps;

import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.database.manager.Database;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class WarpsDatabase extends Database<UUID, Warp> {

    public WarpsDatabase() {
        super(Warp.class, Warp[].class);
    }

    public @Nullable Warp getWarp(String warpName) {
        return getAll()
                .stream().filter(warp -> warp.getName().equalsIgnoreCase(warpName))
                .findFirst()
                .orElse(null);
    }

    public @Nullable Warp getWarp(WarpFunctionType functionType) {
        return getAll()
                .stream().filter(warp -> warp.getType() == functionType)
                .findFirst()
                .orElse(null);
    }

    public  @Nullable Warp getWarp(UUID uuid) {
        return getAll()
                .stream().filter(warp -> warp.getId().equals(uuid))
                .findFirst()
                .orElse(null);
    }

    public UUID getUnusedUUID() {
        UUID uuid;
        do {
            uuid = UUID.randomUUID();
        } while (Plugin.get(WarpsDatabase.class).contains(uuid));

        return uuid;
    }
}
