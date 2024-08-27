package com.github.thiskarolgajda.op.plots.members;

import org.bukkit.Bukkit;

import java.util.Set;
import java.util.UUID;

public record PlotMember(UUID uuid, Set<PlotPermissionsType> allowedPermissions) {
    public String getName() {
        return Bukkit.getOfflinePlayer(uuid).getName();
    }

    public void toggle(PlotPermissionsType permission) {
        if (!allowedPermissions.contains(permission)) {
            allowedPermissions.add(permission);
        } else {
            allowedPermissions.remove(permission);
        }
    }
}
