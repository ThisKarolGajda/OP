package com.github.thiskarolgajda.op.permission;

import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

@Getter
public enum PermissionType {
    // GLOBAL
    USE_COLOR_CODES("opsurvival.usecolorcodes"),
    ADMIN("opsurvival.admin"),
    // PLOTS
    CHEAPER_SETTINGS_COST("opsurvival.cheapersettingscost"),
    CHEAPER_UPGRADES_COST("opsurvival.cheaperupgradescost"),
    ADDITIONAL_PLOT_SIZE_UPGRADE("opsurvival.additionalplotsizeupgrade"),
    ADDITIONAL_PLOT_MEMBERS_UPGRADE("opsurvival.additionalplotsizeupgrade"),
    FLYING_ON_PLOT_SETTING("opsurvival.flyingonplotsetting"),
    WARP_SET_COLOR_NAME("opsurvival.warpsetcolorname"),
    ;

    private final String permission;

    PermissionType(String permission) {
        this.permission = permission;
    }

    public boolean hasPermission(@NotNull OfflinePlayer player) {
        if (player.getPlayer() == null) {
            return false;
        }

        return player.isOp() || player.getPlayer().hasPermission(permission) || player.getPlayer().hasPermission(ADMIN.getPermission());
    }
}
