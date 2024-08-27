package com.github.thiskarolgajda.op.plots.effects;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.utils.OpEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
@Setter
public class PlotEffectsChangeCurrentEvent extends OpEvent {
    private final Plot plot;
}
