package com.github.thiskarolgajda.op.plots.logs;

import java.time.LocalDateTime;

public record PlotLog(LocalDateTime time, PlotLogType type, String value) {

}
