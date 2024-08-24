package com.github.thiskarolgajda.op.region;

import com.github.thiskarolgajda.op.region.inventory.RegionRoleSelectInventory;
import me.opkarol.oplibrary.commands.annotations.Command;
import me.opkarol.oplibrary.commands.annotations.Subcommand;
import me.opkarol.oplibrary.injection.Inject;
import org.bukkit.entity.Player;

import static com.github.thiskarolgajda.op.region.RegionConfig.*;

@Command("region")
public class RegionCommand {
    @Inject
    private static RegionDatabase regionDatabase;

    @Subcommand("stworz")
    public void createRegion(Player player) {
        if (regionDatabase.containsRegion(player.getLocation())) {
            thisLocationIsOccupied.send(player);
            return;
        }

        Region region = new Region(player.getUniqueId(), player.getLocation());
        regionDatabase.save(region);
        createdRegion.send(player);
    }

    @Subcommand("info")
    public void infoRegion(Player player) {
        Region region = regionDatabase.getRegion(player.getLocation()).orElse(null);
        if (region == null) {
            noRegionInThisLocation.send(player);
            return;
        }

        regionInfo.send(player, region);
    }

    @Subcommand("zarzadzaj")
    public void manageRegion(Player player) {
        Region region = regionDatabase.getRegion(player.getLocation()).orElse(null);
        if (region == null) {
            noRegionInThisLocation.send(player);
            return;
        }

        if (!region.getOwner().equals(player.getUniqueId())) {
            youAreNotOwnerOfThisRegion.send(player);
            return;
        }

        new RegionRoleSelectInventory(player, region);
    }

    @Subcommand("usun")
    public void deleteRegion(Player player) {
        Region region = regionDatabase.getRegion(player.getLocation()).orElse(null);
        if (region == null) {
            noRegionInThisLocation.send(player);
            return;
        }

        if (!region.getOwner().equals(player.getUniqueId())) {
            youAreNotOwnerOfThisRegion.send(player);
            return;
        }

        regionDatabase.delete(region);
        deletedRegion.send(player);
    }
}
