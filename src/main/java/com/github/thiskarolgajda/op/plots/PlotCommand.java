package com.github.thiskarolgajda.op.plots;

import com.github.thiskarolgajda.op.plots.inventories.ChoosePlot;
import com.github.thiskarolgajda.op.plots.inventories.CreatePlot;
import com.github.thiskarolgajda.op.plots.inventories.PlotMainInventory;
import me.opkarol.oplibrary.commands.annotations.Command;
import me.opkarol.oplibrary.commands.annotations.Cooldown;
import me.opkarol.oplibrary.commands.annotations.Default;
import me.opkarol.oplibrary.commands.annotations.Subcommand;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.misc.Tuple;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

import static com.github.thiskarolgajda.op.plots.config.PlotConfig.*;

@Command("dzialka")
public class PlotCommand {
    @Inject
    private static PlotDatabase database;

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
}
