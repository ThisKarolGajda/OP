package com.github.thiskarolgajda.op.plots.settings;

import com.github.thiskarolgajda.op.plots.settings.animals.PlotSettingAnimalSpawn;
import com.github.thiskarolgajda.op.plots.settings.daytime.PlotSettingDayType;
import com.github.thiskarolgajda.op.plots.settings.music.PlotSettingMusic;
import com.github.thiskarolgajda.op.plots.settings.weather.PlotSettingWeatherType;
import lombok.Data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class PlotSettings {
    private Set<PlotSettingDayType> ownedDayTypes = new HashSet<>(List.of(PlotSettingDayType.DEFAULT_DAY));
    private PlotSettingDayType selectedDayType = PlotSettingDayType.DEFAULT_DAY;
    private Set<PlotSettingWeatherType> ownedWeatherTypes = new HashSet<>(List.of(PlotSettingWeatherType.DEFAULT_WEATHER));
    private PlotSettingWeatherType selectedWeatherType = PlotSettingWeatherType.DEFAULT_WEATHER;
    private boolean pvp = false;
    private boolean flying = false;
    private PlotSettingAnimalSpawn animalSpawn = new PlotSettingAnimalSpawn();
    private PlotSettingMusic music = new PlotSettingMusic();
}
