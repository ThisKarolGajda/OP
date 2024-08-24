package com.github.thiskarolgajda.op.plots.config;

import me.opkarol.oplibrary.injection.config.Config;

import java.util.List;

@Config(path = "plot.config")
public class PlotConfig {
    @Config
    public static int plotNameMaxLength = 45;
    @Config
    public static int plotNameMinLength = 5;
    @Config
    public static int costForPlotHomeLimitUpgrade = 10000;
    @Config
    public static List<String> plotHologramLines = List.of(
            "&7⋖ &l#!<f5a418>%name%#!<f0e116>&7 ⋗",
            "",
            "#<f5a418>ᴡłᴀśᴄɪᴄɪᴇʟ: #<f0e116>%owner%",
            "#<f5a418>ᴄᴢłᴏɴᴋᴏᴡɪᴇ: #<f0e116>%members%",
            "#<f5a418>ᴅᴏᴍ: #<f0e116>%location%",
            "",
            "#<f5a418>śʀᴇᴅɴɪᴀ ᴏᴄᴇɴ: #<f0e116>%average_review%",
            "#<f5a418>ᴏᴅᴡɪᴇᴅᴢɪɴʏ: #<f0e116>%visits%"
    );
    @Config
    public static List<String> supportedPlotWorlds = List.of("world");
    @Config
    public static int defaultPlotLimit = 1;
    @Config
    public static List<String> defaultPlotNames = List.of("Działeczka %name%a", "Działka %name%a", "Spokojna przystań %name%a", "Posesja %name%a", "Bajkowa oaza %name%a", "Kraina marzeń %name%a", "Słoneczna posiadłość %name%a", "Letnia rezydencja %name%a");
}
