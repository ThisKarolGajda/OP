package com.github.thiskarolgajda.op.core.user.settings;

import java.util.Map;

import static com.github.thiskarolgajda.op.core.user.settings.UserSettingConfig.*;

public enum UserSettingType {
    DISPLAY_JOIN_MESSAGE,
    DISPLAY_QUIT_MESSAGE,
    SHOW_CHATS_MESSAGE;

    private Map<String, Object> getMap() {
        return switch (this) {
            case DISPLAY_JOIN_MESSAGE -> displayJoinMessage;
            case DISPLAY_QUIT_MESSAGE -> displayQuitMessage;
            case SHOW_CHATS_MESSAGE -> showChatMessage;
        };
    }

    public String getName() {
        return (String) getMap().get("name");
    }
}
