package com.github.thiskarolgajda.op.core.warps.custom;

import com.github.thiskarolgajda.op.core.warps.Warp;
import com.github.thiskarolgajda.op.core.warps.WarpFunctionType;
import com.github.thiskarolgajda.op.core.warps.WarpsDatabase;
import lombok.Getter;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.location.OpLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CustomWarp {
    @Inject
    private static WarpsDatabase warpsDatabase;
    private final String name;
    @Getter
    private final WarpFunctionType type;

    public CustomWarp(String name, WarpFunctionType type) {
        this.name = name;
        this.type = type;
    }

    public @Nullable OpLocation getWarpLocation() {
        Warp warp = getWarp();
        return warp.getLocation();
    }

    public void setWarpLocation(OpLocation location) {
        Warp warp = getWarp();
        warp.setLocation(location);
        Plugin.get(WarpsDatabase.class).save(warp);
    }

    public @Nullable Location getWarpBukkitLocation() {
        Warp warp = getWarp();
        return warp.getLocation().getLocation();
    }

    public @NotNull Warp getWarp() {
        Warp warp = warpsDatabase.getWarp(name);
        if (warp == null) {
            warp = new Warp(warpsDatabase.getUnusedUUID(), name, new OpLocation(0, 0, 0, 0, 0, Bukkit.getWorlds().getFirst()), false, type);
            Plugin.get(WarpsDatabase.class).save(warp);
        }

        return warp;
    }

}
