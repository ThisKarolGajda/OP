package com.github.thiskarolgajda.op.plots;

import com.github.thiskarolgajda.op.plots.blockcounter.PlotBlockCounter;
import com.github.thiskarolgajda.op.plots.blocklimits.PlotBlockLimits;
import com.github.thiskarolgajda.op.plots.border.PlotBorder;
import com.github.thiskarolgajda.op.plots.effects.PlotEffects;
import com.github.thiskarolgajda.op.plots.expiration.PlotExpiration;
import com.github.thiskarolgajda.op.plots.hologram.PlotHologram;
import com.github.thiskarolgajda.op.plots.homes.PlotHomes;
import com.github.thiskarolgajda.op.plots.ignored.PlotIgnored;
import com.github.thiskarolgajda.op.plots.logs.PlotLogs;
import com.github.thiskarolgajda.op.plots.members.PlotMembers;
import com.github.thiskarolgajda.op.plots.permits.PlotPermits;
import com.github.thiskarolgajda.op.plots.regions.PlotRegions;
import com.github.thiskarolgajda.op.plots.settings.PlotSettings;
import com.github.thiskarolgajda.op.plots.shopchests.PlotShopChests;
import com.github.thiskarolgajda.op.plots.specials.PlotSpecials;
import com.github.thiskarolgajda.op.plots.upgrades.PlotUpgrades;
import com.github.thiskarolgajda.op.plots.warp.PlotWarp;
import com.github.thiskarolgajda.op.region.Region;
import com.github.thiskarolgajda.op.region.RegionDatabase;
import com.github.thiskarolgajda.op.region.events.ExternalRegionEnterEvent;
import com.github.thiskarolgajda.op.region.events.RegionEnterEvent;
import lombok.Getter;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.location.OpLocation;
import me.opkarol.oplibrary.misc.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.github.thiskarolgajda.op.plots.config.PlotConfig.*;
import static com.github.thiskarolgajda.op.utils.RandomItemCollector.random;

public class PlotFactory {
    @Inject
    private static RegionDatabase regionDatabase;
    @Inject
    private static PlotDatabase plotDatabase;

    public static @NotNull Tuple<PlotCreationResponse, Plot> createPlot(@NotNull Player player) {
        Location location = player.getLocation();
        PlotLocationResponse plotLocationResponse = isValidNewPlotLocation(location);
        if (!plotLocationResponse.isValid()) {
            return Tuple.of(PlotCreationResponse.INVALID_LOCATION, null);
        }

        int maxPlots = getMaxPlots(player);
        int plots = plotDatabase.getOwnerPlots(player.getUniqueId()).size();
        if (maxPlots != -1 && plots + 1 > maxPlots) {
            return Tuple.of(PlotCreationResponse.REACHED_MAX_PLOTS_LIMIT, null);
        }

        UUID plotId = plotDatabase.getUnusedUUID();
        Region centerRegion = createPlotDefaultRegion(player.getUniqueId(), plotId, location);

        String plotName = generateRandomPlotName(player.getName());
        OpLocation plotLocation = new OpLocation(location);
        Plot plot = new Plot(plotId, plotName, new PlotWarp(plotName, plotLocation), player.getUniqueId(), LocalDateTime.now(), new PlotExpiration(), new PlotMembers(), new PlotIgnored(), new PlotHomes(plotLocation), new PlotEffects(), new PlotBorder(), new PlotUpgrades(), new PlotSettings(), new PlotBlockLimits(), new PlotBlockCounter(), new PlotHologram(), new PlotSpecials(), new PlotShopChests(), new PlotRegions(centerRegion.getId()), new PlotLogs(), new PlotPermits());
        plotDatabase.save(plot);

        List<Chunk> chunkList = plot.getRegion().getChunks();
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            Chunk playerChunk = player1.getLocation().getChunk();
            if (chunkList.contains(playerChunk)) {
                Bukkit.getPluginManager().callEvent(new ExternalRegionEnterEvent(centerRegion, player1, RegionEnterEvent.RegionEnterType.MOVE));
            }
        }

        return Tuple.of(PlotCreationResponse.SUCCESS, plot);
    }

    public static @NotNull String generateRandomPlotName(String ownerName) {
        return defaultPlotNames.stream().collect(random()).replace("%name%", ownerName);
    }

    private static @NotNull Region createPlotDefaultRegion(UUID owner, @NotNull UUID plotId, @NotNull Location center) {
        Chunk chunk = center.getChunk();
        int centerX = chunk.getX();
        int centerZ = chunk.getZ();

        Region centerRegion = new Region(center.getWorld().getName(), owner, centerX, centerZ);
        centerRegion.setData(plotId.toString());
        regionDatabase.save(centerRegion);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (dx == 0 && dz == 0) {
                    continue;
                }

                int neighborX = centerX + dx;
                int neighborZ = centerZ + dz;

                Region region = new Region(centerRegion.getId(), neighborX, neighborZ);
                regionDatabase.save(region);
            }
        }

        return centerRegion;
    }

    public static PlotLocationResponse isValidNewPlotLocation(@NotNull Location location) {
        String world = Objects.requireNonNull(location.getWorld()).getName();
        if (supportedPlotWorlds.stream().noneMatch(string -> string.equals(world))) {
            return PlotLocationResponse.INVALID_WORLD;
        }

        Chunk chunk = location.getChunk();
        int centerX = chunk.getX();
        int centerZ = chunk.getZ();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                int neighborX = centerX + dx;
                int neighborZ = centerZ + dz;

                if (regionDatabase.containsRegion(neighborX, neighborZ)) {
                    return PlotLocationResponse.OVERRIDE_OTHER_PLOT;
                }
            }
        }

        return PlotLocationResponse.PROPER_LOCATION;
    }

    public enum PlotCreationResponse {
        INVALID_LOCATION,
        REACHED_MAX_PLOTS_LIMIT,
        SUCCESS,
        ;
    }

    @Getter
    public enum PlotLocationResponse {
        PROPER_LOCATION(true),
        OVERRIDE_OTHER_PLOT,
        OVERRIDE_WORLD_GUARD, //fixme implement
        INVALID_WORLD,
        ;

        final boolean valid;

        PlotLocationResponse(boolean valid) {
            this.valid = valid;
        }

        PlotLocationResponse() {
            this.valid = false;
        }
    }

    public static int getMaxPlots(@NotNull OfflinePlayer player) {
        if (player.isOp()) {
            return -1;
        }

        return defaultPlotLimit;
    }
}
