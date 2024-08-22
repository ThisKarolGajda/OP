package com.github.thiskarolgajda.op.plots.blocklimits;

import me.opkarol.oplibrary.injection.config.Config;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Config(path = "plot.block.limit.type")
public class PlotBlockConfig {

    @Config
    public static Map<String, Object> redstone = getDefaultObject("Czerwony kamień", 1, 50);
    @Config
    public static Map<String, Object> hopper = getDefaultObject("Lejek", 2, 30);
    @Config
    public static Map<String, Object> observer = getDefaultObject("Obserwator", 3, 25);
    @Config
    public static Map<String, Object> piston = getDefaultObject("Tłok", 4, 20);
    @Config
    public static Map<String, Object> repeater = getDefaultObject("Powtarzacz", 2, 40);
    @Config
    public static Map<String, Object> comparator = getDefaultObject("Komparator", 3, 35);
    @Config
    public static Map<String, Object> redstoneTorch = getDefaultObject("Lampa Czerwonego Kamienia", 1, 60);
    @Config
    public static Map<String, Object> redstoneLamp = getDefaultObject("Lampa Czerwonego Kamienia", 2, 45);
    @Config
    public static Map<String, Object> redstoneBlock = getDefaultObject("Blok Czerwonego Kamienia", 5, 10);
    @Config
    public static Map<String, Object> lever = getDefaultObject("Dźwignia", 1, 55);
    @Config
    public static Map<String, Object> stickyPiston = getDefaultObject("Tłok Klejący", 4, 15);
    @Config
    public static Map<String, Object> tripwireHook = getDefaultObject("Haczyk Zaczepowy", 2, 50);
    @Config
    public static Map<String, Object> daylightDetector = getDefaultObject("Detektor Światła Dnia", 3, 25);
    @Config
    public static Map<String, Object> target = getDefaultObject("Celownik", 2, 30);

    @Contract(value = "_, _, _ -> new", pure = true)
    private static @NotNull Map<String, Object> getDefaultObject(String name, int cost, int units) {
        return Map.of(
                "name", name,
                "cost", cost,
                "units", units
        );
    }
}