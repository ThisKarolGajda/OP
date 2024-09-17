package com.github.thiskarolgajda.op.core.marriage;

import me.opkarol.oplibrary.injection.config.Config;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Config(path = "marriage")
public class MarriageConfig {

    @Config
    public static Map<String, Object> formal = getObject("Formalny");
    @Config
    public static Map<String, Object> informal = getObject("Nieformalny");
    @Config
    public static Map<String, Object> arranged = getObject("Ustawka");
    @Config
    public static Map<String, Object> temporary = getObject("Tymczasowy");
    @Config
    public static Map<String, Object> spiritual = getObject("Duchowy");
    @Config
    public static Map<String, Object> soulmate = getObject("Przeznaczenia");

    @Contract(value = "_ -> new", pure = true)
    private static @NotNull Map<String, Object> getObject(String name) {
        return Map.of(
                "name", name
        );
    }
}
