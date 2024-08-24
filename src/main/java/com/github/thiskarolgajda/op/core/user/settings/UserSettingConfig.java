package com.github.thiskarolgajda.op.core.user.settings;

import me.opkarol.oplibrary.injection.config.Config;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Config(path = "core.setting")
public class UserSettingConfig {

    @Config
    public static Map<String, Object> displayJoinMessage = getDefaultObject("Wyświetlaj wiadomości dołączenia serwera");
    @Config
    public static Map<String, Object> displayQuitMessage = getDefaultObject("Wyświetlaj wiadomości opuszczenia serwera");
    @Config
    public static Map<String, Object> showChatMessage = getDefaultObject("Pokazuj wiadomości na czacie");

    private static @NotNull Map<String, Object> getDefaultObject(String name) {
        return Map.of(
                "name", name
        );
    }
}
