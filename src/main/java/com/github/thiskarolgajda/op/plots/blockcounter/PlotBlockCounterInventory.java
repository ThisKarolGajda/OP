package com.github.thiskarolgajda.op.plots.blockcounter;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import com.github.thiskarolgajda.op.plots.inventories.PlotMainInventory;
import com.github.thiskarolgajda.op.utils.HeadsType;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.tools.Heads;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

import static com.github.thiskarolgajda.op.plots.inventories.ChoosePlot.getPlotHeadBasedOnPlayerStatus;

public class PlotBlockCounterInventory extends ChestInventory {
    @Inject
    private static PlotDatabase database;

    public PlotBlockCounterInventory(Player player, Plot plot) {
        super(6, "Statystyki działki");

        setItem(item("Działka %name%", List.of("Właściciel: %owner%", "Łączna wartość: %total%", "Blok żelaza: %iron_block_value%", "Blok netheritu: %netherite_block_value%", "Blok diamentu: %diamond_block_value%", "Blok emeraldu: %emerald_block_value%", "Blok złota: %gold_block_value%", "Beacony: %beacon_value%")), 48, getPlotHeadBasedOnPlayerStatus(player, plot), event -> {}, Map.of(
                "%name%", plot.getName(),
                "%owner%", plot.getOwnerName(),
                "%total%", String.valueOf(plot.getBlockCounter().getTotalValue()),
                "%iron_block_value%", String.valueOf(plot.getBlockCounter().get(PlotBlockCounterType.IRON_BLOCK)),
                "%netherite_block_value%", String.valueOf(plot.getBlockCounter().get(PlotBlockCounterType.NETHERITE_BLOCK)),
                "%diamond_block_value%", String.valueOf(plot.getBlockCounter().get(PlotBlockCounterType.DIAMOND_BLOCK)),
                "%emerald_block_value%", String.valueOf(plot.getBlockCounter().get(PlotBlockCounterType.EMERALD_BLOCK)),
                "%gold_block_value%", String.valueOf(plot.getBlockCounter().get(PlotBlockCounterType.GOLD_BLOCK)),
                "%beacon_value%", String.valueOf(plot.getBlockCounter().get(PlotBlockCounterType.BEACON))
        ));

        setItemHome(49, player, () -> new PlotMainInventory(plot, player));

        setItem(item("O co biega?", List.of("Każdy postawiony na działce specjalny blok dodaje wartość działki.", "Blok żelaza: %iron_block_value%", "Blok netheritu: %netherite_block_value%", "Blok diamentu: %diamond_block_value%", "Blok emeraldu: %emerald_block_value%", "Blok złota: %gold_block_value%", "Beacony: %beacon_value%")), 50, HeadsType.INFORMATION.getHead(), event -> event.setCancelled(true), Map.of(
                "%iron_block_value%", String.valueOf(PlotBlockCounterType.IRON_BLOCK.getValue()),
                "%netherite_block_value%", String.valueOf(PlotBlockCounterType.NETHERITE_BLOCK.getValue()),
                "%diamond_block_value%", String.valueOf(PlotBlockCounterType.DIAMOND_BLOCK.getValue()),
                "%emerald_block_value%", String.valueOf(PlotBlockCounterType.EMERALD_BLOCK.getValue()),
                "%gold_block_value%", String.valueOf(PlotBlockCounterType.GOLD_BLOCK.getValue()),
                "%beacon_value%", String.valueOf(PlotBlockCounterType.BEACON.getValue())
        ));

        setLeaderboardPosition(1, 13);
        setLeaderboardPosition(2, 21);
        setLeaderboardPosition(3, 23);
        setLeaderboardPosition(4, 28);
        setLeaderboardPosition(5, 29);
        setLeaderboardPosition(6, 33);
        setLeaderboardPosition(7, 34);

        fillEmptyWithBlank();
        open(player);
    }

    private void setLeaderboardPosition(int position, int slot) {
        Plot plot = database.getPlotInBlockCounterLeaderboard(position);
        if (plot != null) {
            setItem(item("%place% miejsce - %name%", List.of("Właściciel: %owner%", "Łączna wartość: %total%", "Blok żelaza: %iron_block_value%", "Blok netheritu: %netherite_block_value%", "Blok diamentu: %diamond_block_value%", "Blok emeraldu: %emerald_block_value%", "Blok złota: %gold_block_value%", "Beacony: %beacon_value%")), slot, Heads.get(plot.getOwnerId()), event -> event.setCancelled(true), Map.of(
                    "%name%", plot.getName(),
                    "%owner%", plot.getOwnerName(),
                    "%place%", String.valueOf(position),
                    "%total%", String.valueOf(plot.getBlockCounter().getTotalValue()),
                    "%iron_block_value%", String.valueOf(plot.getBlockCounter().get(PlotBlockCounterType.IRON_BLOCK)),
                    "%netherite_block_value%", String.valueOf(plot.getBlockCounter().get(PlotBlockCounterType.NETHERITE_BLOCK)),
                    "%diamond_block_value%", String.valueOf(plot.getBlockCounter().get(PlotBlockCounterType.DIAMOND_BLOCK)),
                    "%emerald_block_value%", String.valueOf(plot.getBlockCounter().get(PlotBlockCounterType.EMERALD_BLOCK)),
                    "%gold_block_value%", String.valueOf(plot.getBlockCounter().get(PlotBlockCounterType.GOLD_BLOCK)),
                    "%beacon_value%", String.valueOf(plot.getBlockCounter().get(PlotBlockCounterType.BEACON))
            ));
        } else {
            setItem(item("Tu nic jeszcze nie ma!"), slot, HeadsType.GREY_HEAD.getHead(), event -> event.setCancelled(true));
        }
    }
}
