package com.github.thiskarolgajda.op.plots.settings.animals;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class PlotSettingAnimalSpawn {
    private Set<PlotSettingAnimalType> disabledSpawns = new HashSet<>();
    private Set<PlotSettingAnimalType> ownedDisabledSpawns = new HashSet<>();
}
