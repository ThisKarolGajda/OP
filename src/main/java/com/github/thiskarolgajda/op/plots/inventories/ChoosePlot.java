package com.github.thiskarolgajda.op.plots.inventories;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import com.github.thiskarolgajda.op.utils.HeadsType;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class ChoosePlot {

    public ChoosePlot(Player player, Consumer<Plot> onSelect) {
        new PlotChooseInventory(player, onSelect);
    }

    public ChoosePlot(Player player, Consumer<Plot> onSelect, boolean seeAllPlots) {
        new PlotChooseInventory(player, onSelect, seeAllPlots, false);
    }

    public ChoosePlot(Player player, Consumer<Plot> onSelect, boolean seeAllPlots, boolean skipAtOnePlot) {
        new PlotChooseInventory(player, onSelect, seeAllPlots, skipAtOnePlot);
    }

    public static ItemBuilder getPlotHeadBasedOnPlayerStatus(@NotNull Player player, @NotNull Plot plot) {
        return plot.isOwner(player.getUniqueId()) ? HeadsType.OWNER_PLOT.getHead() : plot.isMember(player.getUniqueId()) ? HeadsType.PLOT.getHead() : HeadsType.CURRENT_PLOT.getHead();
    }
}

class PlotChooseInventory extends ChestInventory {
    @Inject
    private static PlotDatabase database;

    public PlotChooseInventory(Player player, Consumer<Plot> action, boolean seeAllPlots, boolean skipAtOnePlot) {
        super(4, "Wybierz działkę");
        setListPattern(player);
        List<Plot> list;
        if (seeAllPlots) {
            list = database.getAll();
        } else {
            list = database.getAddedPlots(player.getUniqueId());
        }

        list = new ArrayList<>(list);
        Optional<Plot> plotAtLocation = database.getPlot(player.getLocation());
        if (plotAtLocation.isPresent()) {
            Plot plot = plotAtLocation.get();
            setItem(HeadsType.CURRENT_PLOT.getHead(), action, plot);
            list.removeIf(plot1 -> plot1.getId().equals(plot.getId()));
            if (list.isEmpty()) {
                action.accept(plot);
                return;
            }
        }

        if (skipAtOnePlot && list.size() == 1 && plotAtLocation.isEmpty()) {
            action.accept(list.getFirst());
            return;
        }

        list.forEach(plot -> setItem(ChoosePlot.getPlotHeadBasedOnPlayerStatus(player, plot), action, plot));

        open(player);
    }

    private void setItem(ItemBuilder player, Consumer<Plot> action, @NotNull Plot plot) {
        setNextFree(item("%plot%", List.of("Właściciel: %owner%", "Data stworzenia: %creation_date%", "Członkowie: %members%")), player, event -> {
            event.setCancelled(true);
            action.accept(plot);
        }, Map.of("%plot%", plot.getName(), "%owner%", plot.getOwnerName(), "%creation_date%", plot.getFormattedCreationDate(), "%members%", plot.getMembersNames()));
    }

    public PlotChooseInventory(Player player, Consumer<Plot> action) {
        this(player, action, false, false);
    }
}
