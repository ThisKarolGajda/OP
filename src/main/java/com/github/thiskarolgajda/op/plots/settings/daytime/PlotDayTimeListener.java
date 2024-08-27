package com.github.thiskarolgajda.op.plots.settings.daytime;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.listener.PlayerEnterPlotEvent;
import com.github.thiskarolgajda.op.plots.listener.PlayerLeavePlotEvent;
import com.github.thiskarolgajda.op.plots.listener.PlotListener;
import me.opkarol.oplibrary.listeners.Listener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlotDayTimeListener extends Listener {

    @EventHandler(ignoreCancelled = true)
    public void onRegionEntered(@NotNull PlayerEnterPlotEvent event) {
        Plot plot = event.getPlot();
        Player player = event.getPlayer();
        setPlayerDayTime(plot, player);
    }

    @EventHandler(ignoreCancelled = true)
    public void onRegionLeft(@NotNull PlayerLeavePlotEvent event) {
        event.getPlayer().resetPlayerTime();
    }

    @EventHandler(ignoreCancelled = true)
    public void onSettingChange(@NotNull PlotDayChangedEvent event) {
        List<Player> players = PlotListener.getPlayersInsidePlot(event.getPlot());
        players.forEach(player -> setPlayerDayTime(event.getPlot(), player));
    }

    private static void setPlayerDayTime(@NotNull Plot plot, Player player) {
        PlotSettingDayType dayType = plot.getSettings().getSelectedDayType();
        if (dayType.getValue() == -1) {
            player.resetPlayerTime();
            return;
        }

        player.setPlayerTime(dayType.getValue(), false);
    }
}
