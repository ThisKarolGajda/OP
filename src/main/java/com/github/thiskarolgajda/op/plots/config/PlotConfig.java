package com.github.thiskarolgajda.op.plots.config;

import me.opkarol.oplibrary.injection.config.Config;

@Config(path = "plot.config")
public class PlotConfig {
    @Config
    public static int plotNameMaxLength = 30;
    @Config
    public static int plotNameMinLength = 5;
}
