package com.github.thiskarolgajda.op.plots.settings.weather;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.listener.PlayerEnterPlotEvent;
import com.github.thiskarolgajda.op.plots.listener.PlayerLeavePlotEvent;
import com.github.thiskarolgajda.op.plots.listener.PlotListener;
import com.github.thiskarolgajda.op.plots.settings.daytime.PlotDayChangedEvent;
import me.opkarol.oplibrary.listeners.Listener;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlotWeatherListener extends Listener {

    @EventHandler
    public void onRegionEntered(@NotNull PlayerEnterPlotEvent event) {
        Plot plot = event.getPlot();
        Player player = event.getPlayer();
        setPlayerWeather(plot, player);
    }

    @EventHandler
    public void onRegionLeft(@NotNull PlayerLeavePlotEvent event) {
        event.getPlayer().resetPlayerWeather();
    }

    @EventHandler
    public void onSettingChange(@NotNull PlotDayChangedEvent event) {
        List<Player> players = PlotListener.getPlayersInsidePlot(event.getPlot());
        players.forEach(player -> setPlayerWeather(event.getPlot(), player));
    }

    private static void setPlayerWeather(@NotNull Plot plot, Player player) {
        PlotSettingWeatherType weatherType = plot.getSettings().getSelectedWeatherType();
        switch (weatherType) {
            case RAIN -> player.setPlayerWeather(WeatherType.DOWNFALL);
            case DEFAULT_WEATHER -> player.resetPlayerWeather();
            case CLEAR -> player.setPlayerWeather(WeatherType.CLEAR);
        }
    }
}
