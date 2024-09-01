package com.github.thiskarolgajda.op.plots;

import com.github.thiskarolgajda.op.plots.inventories.ChoosePlot;
import com.github.thiskarolgajda.op.plots.inventories.CreatePlot;
import com.github.thiskarolgajda.op.plots.inventories.PlotMainInventory;
import com.github.thiskarolgajda.op.region.RegionDatabase;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.commands.annotations.Command;
import me.opkarol.oplibrary.commands.annotations.Cooldown;
import me.opkarol.oplibrary.commands.annotations.Default;
import me.opkarol.oplibrary.commands.annotations.Subcommand;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.injection.messages.StringMessage;
import me.opkarol.oplibrary.location.OpLocation;
import me.opkarol.oplibrary.misc.Tuple;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

import static com.github.thiskarolgajda.op.plots.config.PlotConfig.*;

@Command("dzialka")
public class PlotCommand {
    @Inject
    private static PlotDatabase database;

    public static StringMessage notFoundAnyFreeRegionNearby = new StringMessage("Nie znaleziono żadnego regionu w pobliżu (10 chunków), spróbuj przemiesćić się w inne miejsce.");
    public static StringMessage foundFreeRegionAt = StringMessage.arg("Znaleziono wolny region (co najmniej 3x3 chunki) na lokalizacji: %location%! (%blocks% bloków od ciebie)", object -> {
        Map<String, Object> map = (Map<String, Object>) object;
        return Map.of("%location%", ((OpLocation) map.get("location")).toFamilyStringWithoutWorld(), "%blocks%", String.valueOf(map.get("blocks")));
    });

    @Default
    @Cooldown(1)
    public void mainCommand(@NotNull Player player) {
        Optional<Plot> optional = database.getPlot(player.getLocation());
        if (optional.isEmpty() && database.getOwnerPlots(player.getUniqueId()).isEmpty()) {
            new CreatePlot(player);
            return;
        }

        new ChoosePlot(player, plot -> new PlotMainInventory(plot, player));
    }

    @Subcommand("stworz")
    public void createPlot(Player player) {
        Tuple<PlotFactory.PlotCreationResponse, Plot> tuple = PlotFactory.createPlot(player);

        switch (tuple.first()) {
            case SUCCESS -> createdPlot.success(player, tuple.second().getName());
            case INVALID_LOCATION -> invalidPlotLocation.error(player);
            case REACHED_MAX_PLOTS_LIMIT -> reachedMaxPlotLimit.error(player);
        }
    }

    @Subcommand("usun")
    public void deletePlot(Player player) {
        Plot plot = database.getOwnerPlots(player.getUniqueId()).getFirst();
        if (plot != null) {
            PlotDeleter.deletePlot(plot);
            removedPlot.success(player, plot.getName());
        }
    }

    @Subcommand("wyszukaj")
    public void searchPlot(@NotNull Player player) {
        RegionDatabase regionDatabase = Plugin.get(RegionDatabase.class);
        Chunk centerChunk = player.getLocation().getChunk();

        if (checkSurroundingChunks(centerChunk, regionDatabase) || findSpiralEmptyRegion(centerChunk, regionDatabase) != null) {
            Chunk foundChunk = regionDatabase.containsRegion(centerChunk) ? findSpiralEmptyRegion(centerChunk, regionDatabase) : centerChunk;
            if (foundChunk != null) {
                World world = centerChunk.getWorld();
                int centerX = centerChunk.getX() * 16 + 8;
                int centerZ = centerChunk.getZ() * 16 + 8;
                int centerY = world.getHighestBlockYAt(centerX, centerZ);
                Location chunkCenter = new Location(world, centerX, centerY, centerZ);

                foundFreeRegionAt.success(player, Map.of("location", new OpLocation(chunkCenter), "blocks", (int) chunkCenter.distance(player.getLocation())));
                return;
            }
        }

        notFoundAnyFreeRegionNearby.error(player);
    }

    private boolean checkSurroundingChunks(Chunk centerChunk, RegionDatabase regionDatabase) {
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                Chunk currentChunk = centerChunk.getWorld().getChunkAt(centerChunk.getX() + x, centerChunk.getZ() + z);
                if (regionDatabase.containsRegion(currentChunk)) {
                    return false;
                }
            }
        }
        return true;
    }

    private @Nullable Chunk findSpiralEmptyRegion(Chunk centerChunk, RegionDatabase regionDatabase) {
        int radius = 1;
        while (radius < 10) {
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    if (Math.abs(x) == radius || Math.abs(z) == radius) {
                        Chunk currentChunk = centerChunk.getWorld().getChunkAt(centerChunk.getX() + x, centerChunk.getZ() + z);
                        if (!regionDatabase.containsRegion(currentChunk)) {
                            return currentChunk;
                        }
                    }
                }
            }
            radius++;
        }

        return null;
    }
}
