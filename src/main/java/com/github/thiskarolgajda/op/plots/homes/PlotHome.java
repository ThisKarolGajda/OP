package com.github.thiskarolgajda.op.plots.homes;

import com.github.thiskarolgajda.op.plots.Plot;
import lombok.Getter;
import lombok.Setter;
import me.opkarol.oplibrary.database.DatabaseEntity;
import me.opkarol.oplibrary.location.OpLocation;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.UUID;

import static com.github.thiskarolgajda.op.utils.IconGenerator.getRandomIcon;

@Getter
@Setter
public class PlotHome implements DatabaseEntity<UUID> {
    private final UUID uuid;
    private OpLocation location;
    private String name;
    private PlotHomeAccessType accessType;
    private Material icon;

    public PlotHome(UUID uuid, OpLocation location, String name) {
        this.uuid = uuid;
        this.location = location;
        this.name = name;
        this.accessType = PlotHomeAccessType.MEMBER;
        setRandomIcon();
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    public void setRandomIcon() {
        setIcon(getRandomIcon());
    }

    public boolean isAvailable(Plot plot, Player player) {
        return switch (accessType) {
            case OWNER -> plot.isOwner(player.getUniqueId());
            case MEMBER -> plot.isAdded(player.getUniqueId());
            case GUEST -> true;
        };
    }

    public void changeAccess() {
        switch (accessType) {
            case OWNER -> setAccessType(PlotHomeAccessType.MEMBER);
            case MEMBER -> setAccessType(PlotHomeAccessType.GUEST);
            case GUEST -> setAccessType(PlotHomeAccessType.OWNER);
        }
    }

}
