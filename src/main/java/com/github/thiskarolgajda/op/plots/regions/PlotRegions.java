package com.github.thiskarolgajda.op.plots.regions;

import com.github.thiskarolgajda.op.region.Region;
import com.github.thiskarolgajda.op.region.RegionsDatabase;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.opkarol.oplibrary.injection.Inject;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
@AllArgsConstructor
public class PlotRegions {
    @Inject
    private static RegionsDatabase database;
    private final Region parentRegion;

    public List<Region> getChildrenRegions() {
        return database.getChildren(parentRegion);
    }

    public void add(@NotNull Region region) {
        region.setParent(parentRegion);
        database.save(region);
    }
}
