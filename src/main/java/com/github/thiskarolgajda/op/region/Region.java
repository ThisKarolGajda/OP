package com.github.thiskarolgajda.op.region;

import com.github.thiskarolgajda.op.region.player.PlayerRegionRuleType;
import com.github.thiskarolgajda.op.region.player.PlayerRegionRules;
import com.github.thiskarolgajda.op.region.role.RegionRoleType;
import com.github.thiskarolgajda.op.region.rule.RegionRuleType;
import com.github.thiskarolgajda.op.region.rule.RegionRules;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.database.DatabaseEntity;
import me.opkarol.oplibrary.location.OpLocation;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@ToString
@Getter
public final class Region implements DatabaseEntity<String> {
    private String world;
    private UUID owner;
    private final int chunkX;
    private final int chunkZ;
    private Set<UUID> players;
    private String parentRegion;
    private final Map<RegionRoleType, PlayerRegionRules> playerRules;
    private final RegionRules regionRules;
    @Setter
    private String data;

    public Region(String world, UUID owner, int chunkX, int chunkZ) {
        this.world = world;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;

        // TODO: add default rules
        playerRules = new HashMap<>();
        this.regionRules = new RegionRules(new HashSet<>());

        players = new HashSet<>();
        this.owner = owner;
    }

    public Region(String parentRegion, int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;

        // TODO: add default rules
        playerRules = new HashMap<>();
        this.regionRules = new RegionRules(new HashSet<>());

        players = new HashSet<>();
        this.parentRegion = parentRegion;
    }

    public Region(UUID owner, @NotNull Location location) {
        this(location.getWorld().getName(), owner, location.getChunk().getX(), location.getChunk().getZ());
    }

    public Region(String parentRegion, @NotNull Location location) {
        this(parentRegion, location.getChunk().getX(), location.getChunk().getZ());
    }

    @Contract(" -> new")
    public @NotNull OpLocation getCenter() {
        World world1 = Bukkit.getWorld(getWorld());
        int centerX = (chunkX << 4) + 8;
        int centerZ = (chunkZ << 4) + 8;
        assert world1 != null;
        int centerY = world1.getHighestBlockYAt(centerX, centerZ);
        return new OpLocation(centerX, centerY, centerZ, 0, 0, world1);
    }

    public Set<UUID> getPlayers() {
        Region parentRegion = getParentRegion();
        if (parentRegion != null) {
            return parentRegion.getPlayers();
        }

        return players;
    }

    public void setPlayers(Set<UUID> players) {
        Region parentRegion = getParentRegion();
        if (parentRegion != null) {
            parentRegion.setPlayers(players);
            return;
        }

        this.players = players;
    }

    public boolean isInside(@NotNull Location location) {
        return location.getChunk().getX() == chunkX && location.getChunk().getZ() == chunkZ;
    }

    public boolean isInside(int chunkX, int chunkZ) {
        return this.chunkX == chunkX && this.chunkZ == chunkZ;
    }

    public boolean isInside(@NotNull Region region) {
        return isInside(region.getChunkX(), region.getChunkZ());
    }

    @Override
    public String getId() {
        return "x" + chunkX + "z" + chunkZ;
    }

    public String getParentRegionId() {
        return parentRegion;
    }

    public void setParentRegion(int chunkX, int chunkZ) {
        this.parentRegion = "x" + chunkX + "z" + chunkZ;
    }

    public void setParent(@NotNull Region region) {
        setParentRegion(region.getChunkX(), region.getChunkZ());
    }

    public @Nullable Region getParentRegion() {
        if (parentRegion == null) {
            return null;
        }

        return Plugin.get(RegionDatabase.class).getUnsafe(parentRegion);
    }

    public Map<RegionRoleType, PlayerRegionRules> getPlayerRules() {
        Region parentRegion = getParentRegion();
        if (parentRegion != null) {
            return parentRegion.getPlayerRules();
        }

        return playerRules;
    }

    public @NotNull PlayerRegionRules getPlayerRules(@Nullable RegionRoleType type) {
        if (type == null) {
            return new PlayerRegionRules(new HashSet<>());
        }

        Region parentRegion = getParentRegion();
        if (parentRegion != null) {
            return parentRegion.getPlayerRules(type);
        }

        PlayerRegionRules rules = playerRules.get(type);
        if (rules == null) {
            return new PlayerRegionRules(new HashSet<>());
        }

        return rules;
    }

    public @NotNull PlayerRegionRules getRules(RegionRoleType roleType) {
        Region parentRegion = getParentRegion();
        if (parentRegion != null) {
            return parentRegion.getRules(roleType);
        }

        PlayerRegionRules playerRegionRules = playerRules.get(roleType);
        if (playerRegionRules == null) {
            playerRegionRules = new PlayerRegionRules(new HashSet<>());
            playerRules.put(roleType, playerRegionRules);
        }

        return playerRegionRules;
    }

    public void setRules(RegionRoleType roleType, PlayerRegionRules rules) {
        Region parentRegion = getParentRegion();
        if (parentRegion != null) {
            parentRegion.setRules(roleType, rules);
            return;
        }

        this.playerRules.put(roleType, rules);
        Plugin.get(RegionDatabase.class).save(this);
    }

    public UUID getOwner() {
        Region parentRegion = getParentRegion();
        if (parentRegion != null) {
            return parentRegion.getOwner();
        }

        return owner;
    }

    public String getData() {
        Region parentRegion = getParentRegion();
        if (parentRegion != null) {
            return parentRegion.getData();
        }

        return data;
    }

    public String getWorld() {
        Region parentRegion = getParentRegion();
        if (parentRegion != null) {
            return parentRegion.getWorld();
        }

        return world;
    }

    public @NotNull String getPlayerNames() {
        StringBuilder builder = new StringBuilder();
        for (UUID uuid : getPlayers()) {
            builder.append(Bukkit.getOfflinePlayer(uuid).getName()).append(", ");
        }
        if (builder.length() > 2) {
            builder.delete(builder.length() - 2, builder.length());
        }
        return builder.toString();
    }


    public boolean can(@NotNull Player player, PlayerRegionRuleType rule) {
        Region parentRegion = getParentRegion();
        if (parentRegion != null) {
            return parentRegion.can(player, rule);
        }

        if (owner.equals(player.getUniqueId())) {
            return true;
        }

        if (isMember(player)) {
            return getRules(RegionRoleType.MEMBER).isRuleAllowed(rule);
        }

        return getRules(RegionRoleType.GUEST).isRuleAllowed(rule);
    }

    public boolean can(RegionRuleType type) {
        Region parentRegion = getParentRegion();
        if (parentRegion != null) {
            return parentRegion.can(type);
        }

        return regionRules.contains(type);
    }

    private boolean isMember(Player player) {
        Region parentRegion = getParentRegion();
        if (parentRegion != null) {
            return parentRegion.isMember(player);
        }

        return players.stream().anyMatch(uuid -> uuid.equals(player.getUniqueId()));
    }

    public RegionRules getRegionRules() {
        Region parentRegion = getParentRegion();
        if (parentRegion != null) {
            return parentRegion.getRegionRules();
        }

        return regionRules;
    }

    public void setOwner(UUID owner) {
        Region parentRegion = getParentRegion();
        if (parentRegion != null) {
            parentRegion.setOwner(owner);
            return;
        }

        this.owner = owner;
    }

    public void removePlayer(UUID uuid) {
        Region parentRegion = getParentRegion();
        if (parentRegion != null) {
            parentRegion.removePlayer(uuid);
            return;
        }

        players.removeIf(uuid1 -> uuid1.equals(uuid));
    }

    public void addPlayer(UUID uuid) {
        Region parentRegion = getParentRegion();
        if (parentRegion != null) {
            parentRegion.addPlayer(uuid);
            return;
        }

        players.add(uuid);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Region region)) return false;
        return (chunkX == region.chunkX && chunkZ == region.chunkZ) || getData().equals(region.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(chunkX, chunkZ);
    }

    public @NotNull Chunk getChunk() {
        return Objects.requireNonNull(Bukkit.getWorld(getWorld())).getChunkAt(getChunkX(), getChunkZ());
    }
}