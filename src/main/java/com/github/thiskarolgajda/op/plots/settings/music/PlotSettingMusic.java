package com.github.thiskarolgajda.op.plots.settings.music;

import lombok.Data;

@Data
public class PlotSettingMusic {
    private SimpleSong selectedSong = null;

    public String getSelectedSongName() {
        if (selectedSong != null) {
            return selectedSong.title();
        }

        return "---";
    }
}
