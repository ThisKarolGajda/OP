package com.github.thiskarolgajda.op.plots.inventories;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotFactory;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.misc.Tuple;
import me.opkarol.oplibrary.tools.Heads;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.github.thiskarolgajda.op.plots.config.PlotConfig.*;

public class CreatePlot {

    public CreatePlot(Player player) {
        new PlotCreateInventory(player);
    }
}

class PlotCreateInventory extends ChestInventory {

    public PlotCreateInventory(@NotNull Player player) {
        super(3, "Stwórz działkę");

        setItem(item("create_plot"), 13, Heads.get("686e588c12ce23d27482c6d5c1a331a729bce404254610b00f1951a45a733ae6"), event -> {
            player.closeInventory();

            Location location = player.getLocation();
            PlotFactory.PlotLocationResponse locationResponse = PlotFactory.isValidNewPlotLocation(location);
            if (locationResponse == PlotFactory.PlotLocationResponse.INVALID_WORLD) {
                invalidPlotWorld.error(player);
                return;
            }

            if (!locationResponse.isValid()) {
                invalidPlotLocation.error(player);
                return;
            }

            Tuple<PlotFactory.PlotCreationResponse, Plot> tuple = PlotFactory.createPlot(player);
            PlotFactory.PlotCreationResponse response = tuple.first();
            if (response == PlotFactory.PlotCreationResponse.REACHED_MAX_PLOTS_LIMIT) {
                reachedMaxPlotLimit.error(player);
                return;
            }

            createdPlot.success(player, tuple.second().getName());
        });

        fillEmptyWithBlank();
        open(player);
    }
}
