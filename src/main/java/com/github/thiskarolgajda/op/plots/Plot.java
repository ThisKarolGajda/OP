package com.github.thiskarolgajda.op.plots;

import com.github.thiskarolgajda.op.plots.blockcounter.PlotBlockCounter;
import com.github.thiskarolgajda.op.plots.blocklimits.PlotBlockLimits;
import com.github.thiskarolgajda.op.plots.border.PlotBorder;
import com.github.thiskarolgajda.op.plots.effects.PlotEffects;
import com.github.thiskarolgajda.op.plots.expiration.PlotExpiration;
import com.github.thiskarolgajda.op.plots.hologram.PlotHologram;
import com.github.thiskarolgajda.op.plots.homes.PlotHomes;
import com.github.thiskarolgajda.op.plots.ignored.PlotIgnored;
import com.github.thiskarolgajda.op.plots.logs.PlotLogs;
import com.github.thiskarolgajda.op.plots.members.PlotMembers;
import com.github.thiskarolgajda.op.plots.regions.PlotRegions;
import com.github.thiskarolgajda.op.plots.settings.PlotSettings;
import com.github.thiskarolgajda.op.plots.shopchests.PlotShopChests;
import com.github.thiskarolgajda.op.plots.specials.PlotSpecials;
import com.github.thiskarolgajda.op.plots.upgrades.PlotUpgrades;
import com.github.thiskarolgajda.op.plots.warp.PlotWarp;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.opkarol.oplibrary.database.DatabaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Plot implements DatabaseEntity<UUID> {
    private final UUID plotId;
    private String name;
    private PlotWarp warp;
    private UUID ownerId;
    private LocalDateTime creationDate;
    private PlotExpiration expiration;
    private PlotMembers members;
    private PlotIgnored ignored;
    private PlotHomes homes;
    private PlotEffects effects;
    private PlotBorder border;
    private PlotUpgrades upgrades;
    private PlotSettings settings;
    private PlotBlockLimits blockLimits;
    private PlotBlockCounter blockCounter;
    private PlotHologram hologram;
    private PlotSpecials specials;
    private PlotShopChests shopChests;
    private PlotRegions region;
    private PlotLogs logs;

    @Override
    public UUID getId() {
        return plotId;
    }
}
