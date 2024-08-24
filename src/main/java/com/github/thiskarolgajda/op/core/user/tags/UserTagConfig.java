package com.github.thiskarolgajda.op.core.user.tags;

import me.opkarol.oplibrary.injection.config.Config;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Config(path = "core.tag")
public class UserTagConfig {

    @Config
    public static Map<String, Object> lightning = getObject("⚡");
    @Config
    public static Map<String, Object> hammers = getObject("⚒");
    @Config
    public static Map<String, Object> anchor = getObject("⚓");
    @Config
    public static Map<String, Object> swords = getObject("⚔");
    @Config
    public static Map<String, Object> radiation = getObject("☣");
    @Config
    public static Map<String, Object> clock = getObject("⌚");
    @Config
    public static Map<String, Object> flower = getObject("☘");
    @Config
    public static Map<String, Object> skull = getObject("☠");
    @Config
    public static Map<String, Object> trident = getObject("Ψ");
    @Config
    public static Map<String, Object> scissors = getObject("✁");
    @Config
    public static Map<String, Object> yeah = getObject("✔");
    @Config
    public static Map<String, Object> x = getObject("✘");
    @Config
    public static Map<String, Object> snow = getObject("❄");
    @Config
    public static Map<String, Object> heart = getObject("♥");
    @Config
    public static Map<String, Object> firework = getObject("☀");
    @Config
    public static Map<String, Object> cloud = getObject("☁");
    @Config
    public static Map<String, Object> umbrella = getObject("☔");
    @Config
    public static Map<String, Object> eco = getObject("♻");
    @Config
    public static Map<String, Object> fire = getObject("🔥");

    @Contract(value = "_ -> new", pure = true)
    private static @NotNull Map<String, Object> getObject(String name) {
        return Map.of(
                "name", "&8[#<FFF000>" + name + "&8]",
                "price", 10000
        );
    }
}
