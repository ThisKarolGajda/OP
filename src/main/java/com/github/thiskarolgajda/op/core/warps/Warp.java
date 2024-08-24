package com.github.thiskarolgajda.op.core.warps;

import lombok.Data;
import me.opkarol.oplibrary.database.DatabaseEntity;
import me.opkarol.oplibrary.location.OpLocation;

import java.util.UUID;

@Data
public class Warp implements DatabaseEntity<UUID> {
    private final UUID uuid;
    private final WarpFunctionType type;
    private String name;
    private OpLocation location;
    private boolean visible;

    public Warp(UUID uuid, String name, OpLocation location, boolean visible, WarpFunctionType type) {
        this.uuid = uuid;
        this.name = name;
        this.location = location;
        this.visible = visible;
        this.type = type;
    }

    @Override
    public UUID getId() {
        return uuid;
    }
}
