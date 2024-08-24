package com.github.thiskarolgajda.op.plots.logs;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

import static com.github.thiskarolgajda.op.plots.logs.PlotLogConfig.createPlot;

public enum PlotLogType {
    CREATE_PLOT((args, format) -> {
        if (args.length != 1) {
            return null;
        }

        return format.replace("%name%", getNameFromUUIDString(args[0]));
    }),
    ;

    private static String getNameFromUUIDString(String uuid) {
        return Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName();
    }

    private final @Nullable BiFunction<String[], String, @Nullable String> deserializeFunction;

    PlotLogType(@Nullable BiFunction<String[], String, @Nullable String> deserializeFunction) {
        this.deserializeFunction = deserializeFunction;
    }

    public @NotNull String get(String value) {
        if (value == null) {
            return "";
        }

        if (deserializeFunction == null) {
            return String.join(" ", value.split(";"));
        }

        String result = deserializeFunction.apply(value.split(";"), getFormat());
        if (result == null) {
            return String.join(" ", value.split(";"));
        }

        return result;
    }

    public Map<String, Object> getMap() {
        return switch (this) {
            case CREATE_PLOT -> createPlot;
        };
    }

    public String getName() {
        return (String) getMap().get("name");
    }

    public String getFormat() {
        return (String) getMap().get("format");
    }
}
