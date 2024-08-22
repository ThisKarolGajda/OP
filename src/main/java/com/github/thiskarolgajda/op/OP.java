package com.github.thiskarolgajda.op;

import com.github.thiskarolgajda.op.plots.PlotDatabase;
import com.github.thiskarolgajda.op.region.RegionListener;
import com.github.thiskarolgajda.op.region.RegionsDatabase;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.injection.DependencyInjection;
import org.jetbrains.annotations.Nullable;

public final class OP extends Plugin {

    @Override
    public void enable() {
        DependencyInjection.registerInject(new RegionsDatabase());
        DependencyInjection.registerInject(new RegionListener());

        DependencyInjection.registerInject(new PlotDatabase());
    }

    @Override
    public void disable() {

    }

    @Override
    public @Nullable Integer registerBStatsOnStartup() {
        return null;
    }
}
