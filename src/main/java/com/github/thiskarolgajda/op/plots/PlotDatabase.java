package com.github.thiskarolgajda.op.plots;

import com.github.thiskarolgajda.op.region.Region;
import com.github.thiskarolgajda.op.region.RegionDatabase;
import me.opkarol.oplibrary.database.manager.Database;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.location.OpLocation;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PlotDatabase extends Database<UUID, Plot> {
    @Inject
    private static RegionDatabase regionDatabase;

    public PlotDatabase() {
        super(Plot.class, Plot[].class);
    }

    public Optional<Plot> getPlot(@NotNull OpLocation location) {
        return getPlot(location.getLocation());
    }

    public Optional<Plot> getPlot(Location location) {
        Optional<Region> optionalRegion = regionDatabase.getRegion(location);
        if (optionalRegion.isEmpty()) {
            return Optional.empty();
        }

        Region region = optionalRegion.get();
        String regionData = region.getData();
        if (regionData == null || regionData.isEmpty()) {
            return Optional.empty();
        }

        UUID uuid = UUID.fromString(regionData);
        return get(uuid);
    }

    public UUID getUnusedUUID() {
        UUID uuid;

        do {
            uuid = UUID.randomUUID();
        } while (contains(uuid));

        return uuid;
    }

    @Override
    public boolean useMultiFilesForJSON() {
        return true;
    }

    public List<Plot> getOwnerPlots(UUID uuid) {
        return getAll().stream().filter(plot -> plot.isOwner(uuid)).toList();
    }

    public List<Plot> getAddedPlots(UUID uuid) {
        return getAll().stream().filter(plot -> plot.isAdded(uuid)).toList();
    }

    /**
     * Retrieves a Plot from the block counter leaderboard based on the specified position.
     * The leaderboard is sorted in descending order of total block value.
     *
     * @param position The position in the leaderboard to retrieve the Plot from.
     *                 Must be a positive integer.
     * @return The Plot at the specified position in the leaderboard, or null if the
     * position exceeds the number of available plots.
     * @throws IllegalArgumentException if the position is invalid (less than 1).
     */
    public @Nullable Plot getPlotInBlockCounterLeaderboard(int position) {
        List<Plot> plots = getAll();

        plots.sort((plot1, plot2) -> {
            int totalValue1 = plot1.getBlockCounter().getTotalValue();
            int totalValue2 = plot2.getBlockCounter().getTotalValue();
            return Integer.compare(totalValue2, totalValue1);
        });

        if (position > plots.size()) {
            return null;
        }

        if (position < 1) {
            throw new IllegalArgumentException("Invalid position: " + position);
        }

        return plots.get(position - 1);
    }
}
