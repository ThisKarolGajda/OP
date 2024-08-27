package com.github.thiskarolgajda.op.plots.settings.daytime;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.utils.OpEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PlotDayChangedEvent extends OpEvent {
    private final Plot plot;
    private final PlotSettingDayType type;
}
