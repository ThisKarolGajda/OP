package com.github.thiskarolgajda.op.plots.settings;

import me.opkarol.oplibrary.injection.config.Config;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Config(path = "plot.setting")
public class PlotSettingConfig {

    @Config
    public static int dayTypeBuyPrice = 10000;
    @Config
    public static int weatherTypeBuyPrice = 10000;
    @Config
    public static double discountedSettingsCostPercentage = 0.7;

    @Config
    public static Map<String, Object> dawn = getObject("Świt");
    @Config
    public static Map<String, Object> morning = getObject("Rano");
    @Config
    public static Map<String, Object> noon = getObject("Południe");
    @Config
    public static Map<String, Object> sunset = getObject("Zachód słońca");
    @Config
    public static Map<String, Object> dusk = getObject("Zmierzch");
    @Config
    public static Map<String, Object> night = getObject("Noc");
    @Config
    public static Map<String, Object> midnight = getObject("Północ");
    @Config
    public static Map<String, Object> defaultDay = getObject("Domyslna pora dnia");

    @Config
    public static Map<String, Object> defaultWeather = getObject("Domyślna pogoda");
    @Config
    public static Map<String, Object> clear = getObject("Czysta pogoda");
    @Config
    public static Map<String, Object> rain = getObject("Deszcz");

    private static @NotNull Map<String, Object> getObject(String name) {
        return Map.of(
                "name", name
        );
    }

}
