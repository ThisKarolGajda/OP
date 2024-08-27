package com.github.thiskarolgajda.op.plots.specials;

import lombok.Data;
import org.bukkit.Material;

@Data
public class PlotSpecials {
    private boolean isFlyingEnabledForEveryone = false;
    private boolean isPlotChatDisabled = false;
    private boolean isPlotEnterDisabled = false;
    private boolean isPlotLeaveDisabled = false;
    private boolean areAllRegionPermissionsDisabled = false;
    private Material plotBorderHighlightMaterial = null;
    private boolean isPlotBossBarVisible = true;
}
