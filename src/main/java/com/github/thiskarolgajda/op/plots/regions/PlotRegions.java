package com.github.thiskarolgajda.op.plots.regions;

import com.github.thiskarolgajda.op.region.Region;
import com.github.thiskarolgajda.op.region.RegionDatabase;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.opkarol.oplibrary.injection.Inject;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
public class PlotRegions {
    @Inject
    private static RegionDatabase database;
    private final Region parentRegion;

    public List<Region> getChildrenRegions() {
        return database.getChildren(parentRegion);
    }

    public List<Chunk> getChildrenRegionChunks() {
        return getChildrenRegions().stream()
                .map(Region::getChunk)
                .toList();
    }

    public List<Region> getRegions() {
        List<Region> regions = database.getChildren(parentRegion);
        regions.add(parentRegion);
        return regions;
    }

    public void add(@NotNull Region region) {
        region.setParent(parentRegion);
        database.save(region);
    }

    public @Nullable Region create(Location location) {
        Optional<Region> optional = database.getRegion(location);
        if (optional.isPresent()) {
            return null;
        }

        Region region = new Region(parentRegion.getId(), location);
        database.save(region);
        return region;
    }

    public boolean containsChunk(Chunk chunk) {
        return getRegions().stream().anyMatch(region -> region.getChunkX() == chunk.getX() && region.getChunkZ() == chunk.getZ());
    }

    public List<Chunk> getChunks() {
        List<Chunk> chunks = new ArrayList<>(getChildrenRegionChunks());
        chunks.add(parentRegion.getChunk());
        return chunks;
    }
}
