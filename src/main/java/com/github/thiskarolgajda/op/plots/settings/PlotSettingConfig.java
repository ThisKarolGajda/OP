package com.github.thiskarolgajda.op.plots.settings;

import me.opkarol.oplibrary.injection.config.Config;
import org.jetbrains.annotations.Contract;
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

    @Config
    public static Map<String, Object> zombie = getObject("Zombie");
    @Config
    public static Map<String, Object> creeper = getObject("Kriper");
    @Config
    public static Map<String, Object> skeleton = getObject("Szkielet");
    @Config
    public static Map<String, Object> spider = getObject("Pająk");
    @Config
    public static Map<String, Object> husk = getObject("Husk");
    @Config
    public static Map<String, Object> slime = getObject("Slajm");
    @Config
    public static Map<String, Object> zombieVillager = getObject("Wieśniak zombi");
    @Config
    public static Map<String, Object> warden = getObject("Warden");
    @Config
    public static Map<String, Object> guardian = getObject("Guardian");
    @Config
    public static Map<String, Object> stray = getObject("Stray");
    @Config
    public static Map<String, Object> witherSkeleton = getObject("Wither skeleton");
    @Config
    public static Map<String, Object> drowned = getObject("Drowned");
    @Config
    public static Map<String, Object> pillager = getObject("Pilager");
    @Config
    public static Map<String, Object> ravager = getObject("Ravager");
    @Config
    public static Map<String, Object> witch = getObject("Wiedźma");
    @Config
    public static Map<String, Object> enderman = getObject("Enderman");
    @Config
    public static Map<String, Object> endermite = getObject("Endermite");
    @Contract(value = "_ -> new", pure = true)
    private static @NotNull Map<String, Object> getObject(String name) {
        return Map.of(
                "name", name
        );
    }

}
