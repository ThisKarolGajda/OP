package com.github.thiskarolgajda.op.plots.settings.types;

import lombok.Data;
import org.bukkit.entity.EntityType;

import java.util.HashSet;
import java.util.Set;

@Data
public class PlotSettingAnimalSpawn {
    private Set<EntityType> enabledSpawns = new HashSet<>();
}
