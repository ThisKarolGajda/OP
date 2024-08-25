package com.github.thiskarolgajda.op.plots.members;

import lombok.Getter;

@Getter
public enum PlotPermissionsType {
    MANAGE_EXPIRATION(PlotPermissionType.PLOT), HOME(PlotPermissionType.PLOT), WARP(PlotPermissionType.PLOT), BLOCK_COUNTER(PlotPermissionType.PLOT), LIMIT_BLOCKS(PlotPermissionType.PLOT), BORDER(PlotPermissionType.PLOT), EXPIRE(PlotPermissionType.PLOT), SETTINGS(PlotPermissionType.PLOT), EFFECTS(PlotPermissionType.PLOT), UPGRADES(PlotPermissionType.PLOT), IGNORED(PlotPermissionType.PLOT), MEMBERS(PlotPermissionType.PLOT), NAME(PlotPermissionType.PLOT),
    ;

    private final PlotPermissionType type;

    PlotPermissionsType(PlotPermissionType type) {
        this.type = type;
    }

}
