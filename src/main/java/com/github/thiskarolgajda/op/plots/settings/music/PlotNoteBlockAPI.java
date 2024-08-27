package com.github.thiskarolgajda.op.plots.settings.music;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.listener.PlotListener;
import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.RadioSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import lombok.Getter;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.injection.Inject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public class PlotNoteBlockAPI {
    private static boolean enabled;
    @Getter
    private static final Set<Song> songs = new HashSet<>();
    private static final Map<UUID, RadioSongPlayer> activePlotRadios = new HashMap<>();

    @Inject
    public PlotNoteBlockAPI() {
        enabled = Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI");
        if (!enabled) {
            return;
        }

        File musicDirectory = new File(Plugin.getInstance().getDataFolder(), "music");
        File[] musicFiles = musicDirectory.listFiles();

        if (musicFiles != null) {
            for (File musicFile : musicFiles) {
                if (musicFile.isFile() && musicFile.getName().endsWith(".nbs")) {
                    Song song = NBSDecoder.parse(musicFile);
                    if (song == null) {
                        continue;
                    }

                    if (songs.stream().anyMatch(song1 -> song1.getLength() == song.getLength() && song1.getTitle().equals(song.getTitle()) && song.getAuthor().equals(song1.getAuthor()))) {
                        continue;
                    }

                    songs.add(song);
                }
            }
        }
    }

    public static Set<SimpleSong> getSimpleSongs() {
        return songs.stream().map(song -> new SimpleSong(song.getTitle(), song.getLength())).collect(Collectors.toSet());
    }

    public static void playSong(Plot plot, SimpleSong selectedSong, Player player) {
        if (!enabled) {
            return;
        }

        RadioSongPlayer songPlayer = activePlotRadios.get(plot.getPlotId());
        if (songPlayer == null) {
            Optional<Song> optional = songs.stream().filter(song1 -> song1.getLength() == selectedSong.length() && song1.getTitle().equals(selectedSong.title())).findFirst();
            if (optional.isEmpty()) {
                return;
            }

            Song song = optional.get();
            RadioSongPlayer radioSong = new RadioSongPlayer(song);
            radioSong.addPlayer(player);
            radioSong.setPlaying(true);
            radioSong.setRepeatMode(RepeatMode.ONE);
            activePlotRadios.put(plot.getPlotId(), radioSong);
        } else {
            songPlayer.addPlayer(player);
            activePlotRadios.put(plot.getPlotId(), songPlayer);
        }
    }

    public static void stopSong(Plot plot, Player player) {
        if (!enabled) {
            return;
        }

        RadioSongPlayer radioSong = activePlotRadios.get(plot.getPlotId());
        if (radioSong != null) {
            radioSong.removePlayer(player);
            if (radioSong.getPlayerUUIDs().isEmpty()) {
                radioSong.destroy();
                activePlotRadios.remove(plot.getPlotId());
            } else {
                activePlotRadios.put(plot.getPlotId(), radioSong);
            }
        }
    }

    public static void stopSong(Plot plot) {
        if (!enabled) {
            return;
        }

        RadioSongPlayer radioSong = activePlotRadios.remove(plot.getPlotId());
        if (radioSong != null) {
            radioSong.destroy();
        }
    }

    public static void playSong(Plot plot, SimpleSong selectedSong) {
        if (!enabled) {
            return;
        }

        stopSong(plot);
        Optional<Song> optional = songs.stream().filter(song1 -> song1.getLength() == selectedSong.length() && song1.getTitle().equals(selectedSong.title())).findFirst();
        if (optional.isEmpty()) {
            return;
        }

        Song song = optional.get();
        RadioSongPlayer radioSong = new RadioSongPlayer(song);
        PlotListener.getPlayersInsidePlot(plot).forEach(radioSong::addPlayer);
        radioSong.setPlaying(true);
        radioSong.setRepeatMode(RepeatMode.ONE);
        activePlotRadios.put(plot.getPlotId(), radioSong);
    }
}