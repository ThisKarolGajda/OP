package com.github.thiskarolgajda.op.plots;

import com.github.thiskarolgajda.op.permission.PermissionType;
import com.github.thiskarolgajda.op.plots.blockcounter.PlotBlockCounter;
import com.github.thiskarolgajda.op.plots.blocklimits.PlotBlockLimits;
import com.github.thiskarolgajda.op.plots.border.PlotBorder;
import com.github.thiskarolgajda.op.plots.effects.PlotEffects;
import com.github.thiskarolgajda.op.plots.expiration.PlotExpiration;
import com.github.thiskarolgajda.op.plots.hologram.PlotHologram;
import com.github.thiskarolgajda.op.plots.homes.PlotHomes;
import com.github.thiskarolgajda.op.plots.ignored.PlotIgnored;
import com.github.thiskarolgajda.op.plots.logs.PlotLogs;
import com.github.thiskarolgajda.op.plots.members.PlotMember;
import com.github.thiskarolgajda.op.plots.members.PlotMembers;
import com.github.thiskarolgajda.op.plots.members.PlotPermissionsType;
import com.github.thiskarolgajda.op.plots.regions.PlotRegions;
import com.github.thiskarolgajda.op.plots.settings.PlotSettings;
import com.github.thiskarolgajda.op.plots.shopchests.PlotShopChests;
import com.github.thiskarolgajda.op.plots.specials.PlotSpecials;
import com.github.thiskarolgajda.op.plots.upgrades.PlotUpgradeType;
import com.github.thiskarolgajda.op.plots.upgrades.PlotUpgrades;
import com.github.thiskarolgajda.op.plots.warp.PlotWarp;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.opkarol.oplibrary.database.DatabaseEntity;
import me.opkarol.oplibrary.misc.StringIconUtil;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.github.thiskarolgajda.op.plots.config.PlotConfig.startPlotMaxMembers;
import static com.github.thiskarolgajda.op.utils.DateFormatter.STYLE_FORMATTER;

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

    public boolean canLocationBeHome(@NotNull Location location) {
        Chunk chunk = location.getChunk();
        return region.containsChunk(chunk);
    }

    public boolean isOwner(UUID uuid) {
        return ownerId.equals(uuid);
    }

    public boolean isMember(UUID uuid) {
        return members.getMembers().stream().anyMatch(member -> member.uuid().equals(uuid));
    }

    public boolean isAdded(UUID uuid) {
        return isOwner(uuid) || isMember(uuid);
    }

    public OfflinePlayer getOwner() {
        return Bukkit.getOfflinePlayer(ownerId);
    }

    public boolean isIgnored(UUID uuid) {
        return ignored.getIgnored().contains(uuid);
    }

    public String getOwnerName() {
        return getOwner().getName();
    }

    public String getFormattedCreationDate() {
        return STYLE_FORMATTER.format(creationDate);
    }

    public String getMembersNames() {
        return getMembers().getMembers()
                .stream()
                .map(PlotMember::getName)
                .collect(Collectors.joining(", "));
    }

    public String getAvailableFeature(Player player, PlotPermissionsType permission) {
        return StringIconUtil.getReturnedEmojiFromBoolean(isFeatureAvailable(player, permission));
    }

    public boolean isFeatureAvailable(OfflinePlayer player, PlotPermissionsType permission) {
        return !getSpecials().isAreAllRegionPermissionsDisabled() && (PermissionType.ADMIN.hasPermission(player) ||
                isOwner(player.getUniqueId()) ||
                getMembers().hasPermission(player.getUniqueId(), permission));
    }

    public boolean isNameIllegal(String name) {
        return false; //fixme implement
    }

    public int getMaxPlayers() {
        return upgrades.getLevel(PlotUpgradeType.PLAYER_LIMIT) + startPlotMaxMembers;
    }

    public boolean hasSpaceForNewMember() {
        return members.getMembers().size() < getMaxPlayers();
    }
}
