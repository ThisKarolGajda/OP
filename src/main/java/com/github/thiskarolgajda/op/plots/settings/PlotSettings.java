package com.github.thiskarolgajda.op.plots.settings;

import com.github.thiskarolgajda.op.plots.settings.types.PlotSettingAnimalSpawn;
import com.github.thiskarolgajda.op.plots.settings.types.PlotSettingDayType;
import com.github.thiskarolgajda.op.plots.settings.types.PlotSettingMusic;
import com.github.thiskarolgajda.op.plots.settings.types.PlotSettingWeatherType;
import lombok.Data;

@Data
public class PlotSettings {
    private PlotSettingDayType dayType = PlotSettingDayType.DEFAULT;
    private PlotSettingWeatherType weatherType = PlotSettingWeatherType.DEFAULT;
    private boolean pvp = false;
    private boolean flying = false;
    private PlotSettingAnimalSpawn animalSpawn = new PlotSettingAnimalSpawn();
    private PlotSettingMusic music = new PlotSettingMusic();
}
