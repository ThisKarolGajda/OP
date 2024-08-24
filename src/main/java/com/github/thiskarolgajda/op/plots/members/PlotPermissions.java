package com.github.thiskarolgajda.op.plots.members;

import lombok.Getter;

@Getter
public enum PlotPermissions {
    MANAGE_EXPIRATION(PlotPermissionType.PLOT),
    ;

    private final PlotPermissionType type;

    PlotPermissions(PlotPermissionType type) {
        this.type = type;
    }

}
