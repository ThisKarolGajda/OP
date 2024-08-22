package com.github.thiskarolgajda.op.plots;

import com.github.thiskarolgajda.op.region.Region;
import com.github.thiskarolgajda.op.region.RegionsDatabase;
import me.opkarol.oplibrary.database.manager.Database;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.location.OpLocation;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public class PlotDatabase extends Database<UUID, Plot> {
    @Inject
    private static RegionsDatabase regionsDatabase;

    public PlotDatabase() {
        super(Plot.class, Plot[].class);
    }

    public Optional<Plot> getPlot(@NotNull OpLocation location) {
        return getPlot(location.getLocation());
    }

    public Optional<Plot> getPlot(Location location) {
        Optional<Region> optionalRegion = regionsDatabase.getRegion(location);
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

    @Override
    public boolean useMultiFilesForJSON() {
        return true;
    }
}
