package com.github.thiskarolgajda.op.region;

import me.opkarol.oplibrary.database.manager.Database;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class RegionDatabase extends Database<String, Region> {

    public RegionDatabase() {
        super(Region.class, Region[].class);
    }

    public List<Region> getChildren(Region region) {
        final List<Region> children = new ArrayList<>();
        for (Region child : getAll()) {
            if (Objects.equals(child.getParentRegionId(), region.getId())) {
                children.add(child);
            }
        }

        return children;
    }

    public Optional<Region> getRegion(int chunkX, int chunkZ) {
        return get("x" + chunkX + "z" + chunkZ);
    }

    public Optional<Region> getRegion(Location location) {
        return getRegion(location.getChunk().getX(), location.getChunk().getZ());
    }

    public boolean containsRegion(int chunkX, int chunkZ) {
        return contains("x" + chunkX + "z" + chunkZ);
    }

    public boolean containsRegion(Location location) {
        return containsRegion(location.getChunk());
    }

    public void delete(@NotNull Region region) {
        List<Region> children = getChildren(region);
        for (Region child : children) {
            delete(child);
        }

        super.delete(region);
    }

    @Override
    public boolean useMultiFilesForJSON() {
        return true;
    }

    public boolean containsRegion(@NotNull Chunk chunk) {
        return containsRegion(chunk.getX(), chunk.getZ());
    }
}
