package com.github.thiskarolgajda.op.plots.permits;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class PlotPermits {
    private final Map<PlotPermit, Long> activePermits = new HashMap<>();
}
