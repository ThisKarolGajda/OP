package com.github.thiskarolgajda.op.plots.blockcounter;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import com.github.thiskarolgajda.op.plots.inventories.PlotMainInventory;
import com.github.thiskarolgajda.op.utils.HeadsType;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.tools.Heads;
import org.bukkit.entity.Player;

import java.util.Map;

public class PlotBlockCounterInventory extends ChestInventory {
    @Inject
    private static PlotDatabase database;

    public PlotBlockCounterInventory(Player player, Plot plot) {
        super(6, "plot_block_counter");

        setItemHome(53, player, () -> new PlotMainInventory(plot, player));

        setItem(item("information"), 52, HeadsType.INFORMATION.getHead(), event -> event.setCancelled(true), Map.of(
                "%iron_block_value%", String.valueOf(PlotBlockCounterType.IRON_BLOCK.getValue()),
                "%netherite_block_value%", String.valueOf(PlotBlockCounterType.NETHERITE_BLOCK.getValue()),
                "%diamond_block_value%", String.valueOf(PlotBlockCounterType.DIAMOND_BLOCK.getValue()),
                "%emerald_block_value%", String.valueOf(PlotBlockCounterType.EMERALD_BLOCK.getValue()),
                "%gold_block_value%", String.valueOf(PlotBlockCounterType.GOLD_BLOCK.getValue()),
                "%beacon_value%", String.valueOf(PlotBlockCounterType.BEACON.getValue())
        ));

        for (int i = 51 - PlotBlockCounterType.values().length; i < 51; i++) {
            PlotBlockCounterType type = PlotBlockCounterType.values()[50 - i];
            setItem(item("block"), i, type.getMaterial(), event -> event.setCancelled(true), Map.of(
                    "%name%", type.getMaterial().name(),
                    "%amount%", String.valueOf(plot.getBlockCounter().get(type))
            ));
        }

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
            setItem(item("leaderboard"), slot, Heads.get(plot.getOwnerId()), event -> event.setCancelled(true), Map.of(
                    "%name%", plot.getOwnerName(),
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
            setItem(item("empty"), slot, HeadsType.GREY_HEAD.getHead(), event -> event.setCancelled(true));
        }
    }
}
