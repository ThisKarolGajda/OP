package com.github.thiskarolgajda.op.plots.settings.weather;

import java.util.Map;

import static com.github.thiskarolgajda.op.plots.settings.PlotSettingConfig.*;

public enum PlotSettingWeatherType {
    CLEAR,
    RAIN,
    DEFAULT_WEATHER,
    ;

    public Map<String, Object> getMap() {
        return switch (this) {
            case CLEAR -> clear;
            case RAIN -> rain;
            case DEFAULT_WEATHER -> defaultWeather;
        };
    }

    public String getName() {
        return (String) getMap().get("name");
    }
}
