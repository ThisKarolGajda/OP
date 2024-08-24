package com.github.thiskarolgajda.op.core.user.invite;

import me.opkarol.oplibrary.injection.config.Config;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Config(path = "core.invite")
public class UserInviteConfig {

    @Config
    public static Map<String, Object> plotInvite = getDefaultObject("Zaproszenie do działki");

    @Contract(value = "_ -> new", pure = true)
    private static @NotNull Map<String, Object> getDefaultObject(String name) {
        return Map.of(
                "name", name
        );
    }
}
