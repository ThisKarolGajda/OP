package com.github.thiskarolgajda.op.core.user.settings;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class UserSettings {
    private final Set<UserSettingType> activeSettings;

    public UserSettings() {
        this.activeSettings = new HashSet<>(Set.of(UserSettingType.values()));
    }

    public boolean isActive(UserSettingType setting) {
        return activeSettings.contains(setting);
    }

    public void toggle(UserSettingType setting) {
        if (isActive(setting)) {
            activeSettings.remove(setting);
        } else {
            activeSettings.add(setting);
        }
    }
}
