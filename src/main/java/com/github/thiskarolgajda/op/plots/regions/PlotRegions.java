package com.github.thiskarolgajda.op.plots.regions;

import com.github.thiskarolgajda.op.region.Region;
import com.github.thiskarolgajda.op.region.RegionDatabase;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.opkarol.oplibrary.injection.Inject;
import org.bukkit.Chunk;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
@AllArgsConstructor
public class PlotRegions {
    @Inject
    private static RegionDatabase database;
    private final Region parentRegion;

    public List<Region> getChildrenRegions() {
        return database.getChildren(parentRegion);
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

    public boolean containsChunk(Chunk chunk) {
        return getRegions().stream().anyMatch(region -> region.getChunkX() == chunk.getX() && region.getChunkZ() == chunk.getZ());
    }
}
