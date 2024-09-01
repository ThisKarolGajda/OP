package com.github.thiskarolgajda.op.region.player;

import me.opkarol.oplibrary.injection.config.Config;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Config(path = "player.region.rule")
public class PlayerRegionRuleConfig {

    @Config
    public static Map<String, Object> placeBlock = getObject("Kładzenie bloków");
    @Config
    public static Map<String, Object> breakBlock = getObject("Niszczenie bloków");
    @Config
    public static Map<String, Object> fillBucket = getObject("Napełnianie wiadra");
    @Config
    public static Map<String, Object> emptyBucket = getObject("Opróżnianie wiadra");
    @Config
    public static Map<String, Object> fightPlayer = getObject("Walka z graczem");
    @Config
    public static Map<String, Object> fightEntity = getObject("Walka z potworami");
    @Config
    public static Map<String, Object> openChest = getObject("Otwieranie skrzynki");

    private static @NotNull Map<String, Object> getObject(String name) {
        return Map.of("name", name);
    }

}
