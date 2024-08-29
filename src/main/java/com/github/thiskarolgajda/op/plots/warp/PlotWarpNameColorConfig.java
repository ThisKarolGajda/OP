package com.github.thiskarolgajda.op.plots.warp;

import me.opkarol.oplibrary.injection.config.Config;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Config(path = "plot.warp.name.color")
public class PlotWarpNameColorConfig {

    @Config
    public static Map<String, Object> black = getObject("Czarny");
    @Config
    public static Map<String, Object> darkBlue = getObject("Ciemno-niebieski");
    @Config
    public static Map<String, Object> darkGreen = getObject("Ciemno-zielony");
    @Config
    public static Map<String, Object> darkAqua = getObject("Ciemnoniebiesko-zielony");
    @Config
    public static Map<String, Object> darkRed = getObject("Ciemno-czerwony");
    @Config
    public static Map<String, Object> darkPurple = getObject("Ciemno-fioletowy");
    @Config
    public static Map<String, Object> gold = getObject("Złoty");
    @Config
    public static Map<String, Object> gray = getObject("Szary");
    @Config
    public static Map<String, Object> darkGray = getObject("Ciemno-szary");
    @Config
    public static Map<String, Object> blue = getObject("Niebieski");
    @Config
    public static Map<String, Object> green = getObject("Zielony");
    @Config
    public static Map<String, Object> aqua = getObject("Turkusowy");
    @Config
    public static Map<String, Object> red = getObject("Czerwony");
    @Config
    public static Map<String, Object> lightPurple = getObject("Jasno-fioletowy");
    @Config
    public static Map<String, Object> yellow = getObject("Żółty");
    @Config
    public static Map<String, Object> white = getObject("Biały");
    @Config
    public static Map<String, Object> classicBlue = getObject("Classic blue");
    @Config
    public static Map<String, Object> livingCoral = getObject("Living coral");
    @Config
    public static Map<String, Object> ultraViolet = getObject("Ultra violet");
    @Config
    public static Map<String, Object> greenery = getObject("Greenery");

    @Contract(value = "_ -> new", pure = true)
    private static @NotNull Map<String, Object> getObject(String name) {
        return Map.of(
                "name", name
        );
    }
}
