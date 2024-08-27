package com.github.thiskarolgajda.op.plots.members;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.github.thiskarolgajda.op.plots.members.PlotPermissionTypeConfig.*;

@Getter
public enum PlotPermissionsType {
    TELEPORT_HOME(PlotPermissionType.PLOT),
    USE_WARP(PlotPermissionType.PLOT),
    BLOCK_COUNTER(PlotPermissionType.PLOT),
    LIMIT_BLOCKS(PlotPermissionType.PLOT),
    DISPLAY_BORDER(PlotPermissionType.PLOT),
    MANAGE_EXPIRE(PlotPermissionType.PLOT),
    SET_SETTINGS(PlotPermissionType.PLOT),
    CHANGE_EFFECTS(PlotPermissionType.PLOT),
    BUY_UPGRADES(PlotPermissionType.PLOT),
    MANAGE_IGNORED(PlotPermissionType.PLOT),
    MANAGE_MEMBERS(PlotPermissionType.PLOT),
    SET_NAME(PlotPermissionType.PLOT),
    ;

    private final PlotPermissionType type;

    PlotPermissionsType(PlotPermissionType type) {
        this.type = type;
    }

    public String getName() {
        return (String) getMap().get("name");
    }

    public String getTexture() {
        return (String) getMap().get("texture");
    }

    public boolean isDefault() {
        return (boolean) getMap().get("isDefault");
    }

    public Map<String, Object> getMap() {
        return switch (this) {
            case TELEPORT_HOME -> teleportHome;
            case USE_WARP -> useWarp;
            case BLOCK_COUNTER -> blockCounter;
            case LIMIT_BLOCKS -> limitBlocks;
            case DISPLAY_BORDER -> displayBorder;
            case MANAGE_EXPIRE -> manageExpire;
            case SET_SETTINGS -> setSettings;
            case CHANGE_EFFECTS -> changeEffects;
            case BUY_UPGRADES -> buyUpgrades;
            case MANAGE_IGNORED -> manageIgnored;
            case MANAGE_MEMBERS -> manageMembers;
            case SET_NAME -> setName;
        };
    }

    public static List<PlotPermissionsType> getDefaultPermissions() {
        return Arrays.stream(PlotPermissionsType.values())
                .filter(PlotPermissionsType::isDefault)
                .toList();
    }
}
