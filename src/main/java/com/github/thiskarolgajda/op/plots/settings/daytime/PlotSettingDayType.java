package com.github.thiskarolgajda.op.plots.settings.daytime;

import lombok.Getter;

import java.util.Map;

import static com.github.thiskarolgajda.op.plots.settings.PlotSettingConfig.*;

@Getter
public enum PlotSettingDayType {
    DAWN(0),
    MORNING(1000),
    NOON(6000),
    SUNSET(12000),
    DUSK(13000),
    NIGHT(14000),
    MIDNIGHT(18000),
    DEFAULT_DAY(-1),
    ;

    private final long value;

    PlotSettingDayType(long value) {
        this.value = value;
    }

    public Map<String, Object> getMap() {
        return switch (this) {
            case DAWN -> dawn;
            case MORNING -> morning;
            case NOON -> noon;
            case SUNSET -> sunset;
            case DUSK -> dusk;
            case NIGHT -> night;
            case MIDNIGHT -> midnight;
            case DEFAULT_DAY -> defaultDay;
        };
    }

    public String getName() {
        return (String) getMap().get("name");
    }

}
