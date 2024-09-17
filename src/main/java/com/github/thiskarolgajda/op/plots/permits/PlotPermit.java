package com.github.thiskarolgajda.op.plots.permits;

import lombok.Data;

import java.util.UUID;

@Data
public class PlotPermit {
    private final UUID given;
    private final PlotPermitType type;
    private final long expiration;
    private final int durationInMinutes;
}
