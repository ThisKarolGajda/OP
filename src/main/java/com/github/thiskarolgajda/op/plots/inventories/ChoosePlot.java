package com.github.thiskarolgajda.op.plots.inventories;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.tools.Heads;
import org.bukkit.entity.Player;

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
            setNextFree(item("plot"), Heads.get("b26a0d7c23454ee0ba95bb31e41666f1631b91c2ee41711af2e9ef4b1165c4aa"), event -> {
                event.setCancelled(true);
                action.accept(plot);
            }, Map.of("%plot%", plot.getName(), "%name%", plot.getOwnerName(), "%creation_date%", plot.getFormattedCreationDate(), "%members%", plot.getMembersNames()));
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

        list.forEach(plot -> setNextFree(item("plot"), plot.isOwner(player.getUniqueId()) ? Heads.get("eb18cf9e1bf7ec57304ae92f2b00d91643cf0b65067dead34fb48baf18e3c385") : Heads.get("6fb68f0401181d0dc6d87f3da76196d3aa577298ba3351b0d32509ef495774db"), event -> {
            event.setCancelled(true);
            action.accept(plot);
        }, Map.of("%plot%", plot.getName(), "%name%", plot.getOwnerName(), "%creation_date%", plot.getFormattedCreationDate(), "%members%", plot.getMembersNames())));

        open(player);
    }

    public PlotChooseInventory(Player player, Consumer<Plot> action) {
        this(player, action, false, false);
    }
}
