package com.github.thiskarolgajda.op.plots.members;

import me.opkarol.oplibrary.injection.config.Config;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Config(path = "plot.permission.type")
public class PlotPermissionTypeConfig {

    @Config
    public static Map<String, Object> teleportHome = getObject("Użyj domu działki", "c3a8e402dad1b7dad9aae6f4015932183429ce87bbbeced3119026f8296336c2", true);
    @Config
    public static Map<String, Object> useWarp = getObject("Użyj warpu działki", "c3a8e402dad1b7dad9aae6f4015932183429ce87bbbeced3119026f8296336c2", true);
    @Config
    public static Map<String, Object> blockCounter = getObject("Wyświetl statystyki bloków", "c3a8e402dad1b7dad9aae6f4015932183429ce87bbbeced3119026f8296336c2", true);
    @Config
    public static Map<String, Object> limitBlocks = getObject("Zarządź limity bloków", "c3a8e402dad1b7dad9aae6f4015932183429ce87bbbeced3119026f8296336c2", false);
    @Config
    public static Map<String, Object> displayBorder = getObject("Granica działki", "c3a8e402dad1b7dad9aae6f4015932183429ce87bbbeced3119026f8296336c2", true);
    @Config
    public static Map<String, Object> manageExpire = getObject("Wygaśnięcie działki", "c3a8e402dad1b7dad9aae6f4015932183429ce87bbbeced3119026f8296336c2", true);
    @Config
    public static Map<String, Object> setSettings = getObject("Ustawienia działki", "c3a8e402dad1b7dad9aae6f4015932183429ce87bbbeced3119026f8296336c2", false);
    @Config
    public static Map<String, Object> changeEffects = getObject("Efekty działki", "c3a8e402dad1b7dad9aae6f4015932183429ce87bbbeced3119026f8296336c2", false);
    @Config
    public static Map<String, Object> buyUpgrades = getObject("Ulepsz działkę", "c3a8e402dad1b7dad9aae6f4015932183429ce87bbbeced3119026f8296336c2", false);
    @Config
    public static Map<String, Object> manageIgnored = getObject("Zarzadzaj ignorowanymi", "c3a8e402dad1b7dad9aae6f4015932183429ce87bbbeced3119026f8296336c2", false);
    @Config
    public static Map<String, Object> manageMembers = getObject("Zarządzaj członkami", "c3a8e402dad1b7dad9aae6f4015932183429ce87bbbeced3119026f8296336c2", false);
    @Config
    public static Map<String, Object> setName = getObject("Zmień nazwę", "c3a8e402dad1b7dad9aae6f4015932183429ce87bbbeced3119026f8296336c2", false);

    @Contract(value = "_, _, _ -> new", pure = true)
    private static @NotNull Map<String, Object> getObject(String name, String texture, boolean isDefault) {
        return Map.of(
                "name", name,
                "texture", texture,
                "isDefault", isDefault
        );
    }
}
