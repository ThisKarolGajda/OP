package com.github.thiskarolgajda.op.plots.settings.animals;

import lombok.Getter;
import org.bukkit.entity.EntityType;

import java.util.Map;

import static com.github.thiskarolgajda.op.plots.settings.PlotSettingConfig.*;

@Getter
public enum PlotSettingAnimalType {
    CREEPER(EntityType.CREEPER),
    SKELETON(EntityType.SKELETON),
    SPIDER(EntityType.SPIDER),
    HUSK(EntityType.HUSK),
    SLIME(EntityType.SLIME),
    ZOMBIE_VILLAGER(EntityType.ZOMBIE_VILLAGER),
    ZOMBIE(EntityType.ZOMBIE),
    WARDEN(EntityType.WARDEN),
    GUARDIAN(EntityType.GUARDIAN),
    STRAY(EntityType.STRAY),
    WITHER_SKELETON(EntityType.WITHER_SKELETON),
    DROWNED(EntityType.DROWNED),
    PILLAGER(EntityType.PILLAGER),
    RAVAGER(EntityType.RAVAGER),
    WITCH(EntityType.WITCH),
    ENDERMAN(EntityType.ENDERMAN),
    ENDERMITE(EntityType.ENDERMITE),
    ;

    private final EntityType type;

    PlotSettingAnimalType(EntityType type) {
        this.type = type;
    }

    public Map<String, Object> getMap() {
        return switch (this) {
            case CREEPER -> creeper;
            case SKELETON -> skeleton;
            case SPIDER -> spider;
            case HUSK -> husk;
            case SLIME -> slime;
            case ZOMBIE_VILLAGER -> zombieVillager;
            case ZOMBIE -> zombie;
            case WARDEN -> warden;
            case GUARDIAN -> guardian;
            case STRAY -> stray;
            case WITHER_SKELETON -> witherSkeleton;
            case DROWNED -> drowned;
            case PILLAGER -> pillager;
            case RAVAGER -> ravager;
            case WITCH -> witch;
            case ENDERMAN -> enderman;
            case ENDERMITE -> endermite;
        };
    }

    public String getName() {
        return (String) getMap().get("name");
    }
}
