package com.github.thiskarolgajda.op.plots.config;

import me.opkarol.oplibrary.injection.config.Config;
import me.opkarol.oplibrary.injection.messages.StringMessage;

import java.util.List;
import java.util.Map;

@Config(path = "plot.config")
public class PlotConfig {
    @Config
    public static int plotNameMaxLength = 55;
    @Config
    public static int plotNameMinLength = 5;
    @Config
    public static int costForPlotHomeLimitUpgrade = 10000;
    @Config
    public static List<String> plotHologramLines = List.of("&7⋖ &l#!<f5a418>%name%#!<f0e116>&7 ⋗", "", "#<f5a418>ᴡłᴀśᴄɪᴄɪᴇʟ: #<f0e116>%owner%", "#<f5a418>ᴄᴢłᴏɴᴋᴏᴡɪᴇ: #<f0e116>%members%", "#<f5a418>ᴅᴏᴍ: #<f0e116>%location%", "", "#<f5a418>śʀᴇᴅɴɪᴀ ᴏᴄᴇɴ: #<f0e116>%average_review%", "#<f5a418>ᴏᴅᴡɪᴇᴅᴢɪɴʏ: #<f0e116>%visits%");
    @Config
    public static List<String> supportedPlotWorlds = List.of("world");
    @Config
    public static int defaultPlotLimit = 1;
    @Config
    public static List<String> defaultPlotNames = List.of("Działeczka %name%a", "Działka %name%a", "Spokojna przystań %name%a", "Posesja %name%a", "Bajkowa oaza %name%a", "Kraina marzeń %name%a", "Słoneczna posiadłość %name%a", "Letnia rezydencja %name%a", "Miejsce na ziemi %name%a");
    @Config
    public static int defaultPlotExpirationDays = 31;
    @Config
    public static int defaultPlotHomeLimit = 1;
    @Config
    public static String defaultPlotHouseName = "Dom";
    @Config
    public static String plotWarpReviewExitCommand = "anuluj";
    @Config
    public static int plotWarpReviewMaxLength = 100;
    @Config
    public static String defaultPlotWarpName = "Warp %plot%";
    @Config
    public static int startPlotMaxMembers = 3;
    @Config
    public static int costForAnimalType = 1000;
    @Config
    public static String plotGuestName = "Gość";
    @Config
    public static String plotMemberName = "Czlonek";
    @Config
    public static String plotOwnerName = "Właściciel";

    public static StringMessage createdPlot = StringMessage.arg("Stworzono działkę %name%", object -> Map.of("%name%", object.toString()));
    public static StringMessage invalidPlotLocation = new StringMessage("Nieprawidłowa lokalizacja działki. Użyj /dzialka wyszukaj, aby znaleźć wolne miejsce!");
    public static StringMessage reachedMaxPlotLimit = new StringMessage("Osiągnieto limit działek!");
    public static StringMessage removedPlot = StringMessage.arg("Usunięto działkę %name%", object -> Map.of("%name%", object.toString()));
    public static StringMessage invalidPlotWorld = new StringMessage("Na tym świecie nie możesz mieć działki!");

}
