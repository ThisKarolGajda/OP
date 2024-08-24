package com.github.thiskarolgajda.op.core.user.homes;

import lombok.Getter;
import lombok.Setter;
import me.opkarol.oplibrary.location.OpLocation;
import org.bukkit.Material;

import java.util.UUID;

import static com.github.thiskarolgajda.op.utils.IconGenerator.getRandomIcon;

@Getter
public class UserHome {
    private final UUID uuid;
    @Setter
    private OpLocation location;
    @Setter
    private String name;
    @Setter
    private Material icon;

    public UserHome(UUID uuid, OpLocation location, String name) {
        this.uuid = uuid;
        this.location = location;
        this.name = name;
        this.icon = getRandomIcon();
    }

    public void setRandomIcon() {
        setIcon(getRandomIcon());
    }

}
