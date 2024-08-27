package com.github.thiskarolgajda.op.plots.settings.music;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.listener.PlayerEnterPlotEvent;
import com.github.thiskarolgajda.op.plots.listener.PlayerLeavePlotEvent;
import me.opkarol.oplibrary.listeners.Listener;
import org.bukkit.event.EventHandler;

public class PlotMusicListener extends Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerEnterPlot(PlayerEnterPlotEvent event) {
        Plot plot = event.getPlot();
        PlotSettingMusic music = plot.getSettings().getMusic();
        if (music.getSelectedSong() == null) {
            return;
        }

        PlotNoteBlockAPI.playSong(event.getPlot(), music.getSelectedSong(), event.getPlayer());
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerLeavePlot(PlayerLeavePlotEvent event) {
        Plot plot = event.getPlot();
        PlotSettingMusic music = plot.getSettings().getMusic();
        if (music.getSelectedSong() == null) {
            return;
        }

        PlotNoteBlockAPI.stopSong(event.getPlot(), event.getPlayer());
    }

    @EventHandler(ignoreCancelled = true)
    public void onChangedMusic(PlotMusicChangedEvent event) {
        Plot plot = event.getPlot();
        PlotSettingMusic music = plot.getSettings().getMusic();
        if (music.getSelectedSong() == null) {
            PlotNoteBlockAPI.stopSong(event.getPlot());
        } else {
            PlotNoteBlockAPI.playSong(event.getPlot(), music.getSelectedSong());
        }
    }
}
