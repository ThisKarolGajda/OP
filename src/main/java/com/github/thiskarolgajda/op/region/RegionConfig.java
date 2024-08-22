package com.github.thiskarolgajda.op.region;

import me.opkarol.oplibrary.injection.config.Config;
import me.opkarol.oplibrary.injection.messages.StringMessage;

import java.util.Map;

@Config(path = "region")
public class RegionConfig {
    @Config
    public static String guestName = "Gość";
    @Config
    public static String memberName = "Członek";

    public static StringMessage thisLocationIsOccupied = new StringMessage("Ta lokalizacja jest już zajęta");
    public static StringMessage createdRegion = new StringMessage("Stworzono region");
    public static StringMessage noRegionInThisLocation = new StringMessage("Nie ma regioniu w tej lokalizacji");
    public static StringMessage youAreNotOwnerOfThisRegion = new StringMessage("Nie jesteś właścicielem tego regionu");
    public static StringMessage deletedRegion = new StringMessage("Usunięto region");
    public static StringMessage regionInfo = StringMessage.arg("Region: %id%\nWłaściciel: %owner%\nCzłonkowie: %members%", arg -> {
        Region region = (Region) arg;
        return Map.of(
                "%id%", region.getId(),
                "%owner%", region.getOwner().toString(),
                "%members%", region.getPlayerNames()
        );
    });

}
