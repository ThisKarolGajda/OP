package com.github.thiskarolgajda.op.region.rule;

import me.opkarol.oplibrary.injection.config.Config;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Config(path = "region.rule")
public class RegionRuleConfig {

    @Config
    public static Map<String, Object> canFireSpread = getObject("Czy ogień może się rosprzestrzeniać");
    @Config
    public static Map<String, Object> canFireIgnite = getObject("Czy ogień może się zacząć palić");
    @Config
    public static Map<String, Object> canBlockSpread = getObject("Czy bloki mogą się rosprzestrzeniać");
    @Config
    public static Map<String, Object> canLiquidSpread = getObject("Czy woda może się rosprzestrzeniać");
    @Config
    public static Map<String, Object> canTxtExplode = getObject("Czy TNT może wybuchać");

    private static @NotNull Map<String, Object> getObject(String name) {
        return Map.of("name", name);
    }
}
