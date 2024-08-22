package com.github.thiskarolgajda.op.region;

import me.opkarol.oplibrary.database.manager.Database;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegionsDatabase extends Database<String, Region> {

    public RegionsDatabase() {
        super(Region.class, Region[].class);
    }

    public List<Region> getChildren(Region region) {
        final List<Region> children = new ArrayList<>();
        for (Region child : getAll()) {
            if (child.getParentRegion().equals(region)) {
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
        return containsRegion(location.getChunk().getX(), location.getChunk().getZ());
    }

    @Override
    public boolean useMultiFilesForJSON() {
        return true;
    }
}
