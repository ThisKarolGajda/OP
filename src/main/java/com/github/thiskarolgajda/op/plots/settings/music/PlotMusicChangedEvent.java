package com.github.thiskarolgajda.op.plots.settings.music;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.utils.OpEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PlotMusicChangedEvent extends OpEvent {
    private final Plot plot;
    private final SimpleSong type;
}
