package com.github.thiskarolgajda.op.plots.logs;

import me.opkarol.oplibrary.injection.config.Config;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class PlotLogConfig {

    @Config
    public static Map<String, Object> createPlot = getDefaultObject("Stworzenie działki", "%name% stworzył działkę");

    @Contract(value = "_, _ -> new", pure = true)
    private static @NotNull Map<String, Object> getDefaultObject(String name, String format) {
        return Map.of(
                "name", name,
                "format", format
        );
    }
}
