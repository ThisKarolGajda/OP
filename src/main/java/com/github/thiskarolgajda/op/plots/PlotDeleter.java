package com.github.thiskarolgajda.op.plots;

import com.github.thiskarolgajda.op.region.RegionDatabase;
import me.opkarol.oplibrary.injection.Inject;
import org.jetbrains.annotations.NotNull;

public class PlotDeleter {
    @Inject
    private static PlotDatabase plotDatabase;
    @Inject
    private static RegionDatabase regionDatabase;

    public static void deletePlot(@NotNull Plot plot) {
        regionDatabase.delete(plot.getRegion().getParentRegion());
        plotDatabase.delete(plot);
    }
}
