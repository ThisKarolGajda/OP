package com.github.thiskarolgajda.op.core.user.achievements;

import me.opkarol.oplibrary.injection.config.Config;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Config(path = "core.achievement")
public class UserAchievementConfig {

    @Config
    public static Map<String, Object> masterMiner = getObject("Złoty zdobywca", "Zdobądź 1000 sztuk złota");
    @Config
    public static Map<String, Object> createPlot = getObject("Swoje królestwo", "Stwórz działkę");
    @Config
    public static Map<String, Object> haveAParty = getObject("Impreza!!", "Zgromadź 10 osób na swojej działce");
    @Config
    public static Map<String, Object> timeTaster = getObject("Smakosz Czasu", "Zasmakuj 10 godzin przygód na VIPMC");
    @Config
    public static Map<String, Object> chronosApprentice = getObject("Uczeń Chronosa", "Opanuj sztukę spędzania czasu: 100 godzin na VIPMC");
    @Config
    public static Map<String, Object> minecraftTimeBender = getObject("Władca Czasu", "Zegnij czasoprzestrzeń, spędzając 1000 godzin na VIPMC");

    private static @NotNull Map<String, Object> getObject(String name, String description) {
        return Map.of(
                "name", name,
                "description", description
        );
    }
}
