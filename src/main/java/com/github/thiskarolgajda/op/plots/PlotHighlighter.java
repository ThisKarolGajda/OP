package com.github.thiskarolgajda.op.plots;

import me.opkarol.oplibrary.runnable.OpTimerRunnable;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlotHighlighter {

    public static @NotNull List<Location> getBorderLocations(List<Chunk> chunks) {
        Set<Location> locations = new HashSet<>();
        Set<Chunk> chunkSet = new HashSet<>(chunks);

        for (Chunk chunk : chunks) {
            World world = chunk.getWorld();
            int chunkX = chunk.getX();
            int chunkZ = chunk.getZ();
            int minX = chunkX << 4;
            int minZ = chunkZ << 4;
            int maxX = minX + 15;
            int maxZ = minZ + 15;

            addBorderLocations(locations, world, chunkX, chunkZ, minX, minZ, maxX, maxZ, chunkSet);
        }

        return new ArrayList<>(locations);
    }

    private static void addBorderLocations(Set<Location> locations, World world, int chunkX, int chunkZ,
                                           int minX, int minZ, int maxX, int maxZ, Set<Chunk> chunkSet) {
        if (!chunkSet.contains(world.getChunkAt(chunkX - 1, chunkZ))) {
            for (int z = minZ; z <= maxZ; z++) {
                locations.add(new Location(world, minX, 0, z));
            }
        }
        if (!chunkSet.contains(world.getChunkAt(chunkX + 1, chunkZ))) {
            for (int z = minZ; z <= maxZ; z++) {
                locations.add(new Location(world, maxX, 0, z));
            }
        }
        if (!chunkSet.contains(world.getChunkAt(chunkX, chunkZ - 1))) {
            for (int x = minX; x <= maxX; x++) {
                locations.add(new Location(world, x, 0, minZ));
            }
        }
        if (!chunkSet.contains(world.getChunkAt(chunkX, chunkZ + 1))) {
            for (int x = minX; x <= maxX; x++) {
                locations.add(new Location(world, x, 0, maxZ));
            }
        }
    }

    private static void spawnParticle(@NotNull Player player, double x, double y, double z, @NotNull Material material) {
        Location location = new Location(player.getWorld(), x + 0.5, y, z + 0.5);
        Particle particle = Particle.BLOCK_MARKER;
        player.spawnParticle(particle, location, 1, 0, 0, 0, 1, material.createBlockData());
    }

    private static void spawnParticle(@NotNull Player player, double x, double y, double z, Color color) {
        player.spawnParticle(Particle.DUST, x + 0.5, y, z + 0.5, 0, 0, 0, 0, 0, new Particle.DustOptions(color, 2f));
    }

    public static void highlightWithBlocks(@NotNull Plot plot, @NotNull List<Player> players, int duration, Material material) {
        final List<Location> locations = getBorderLocations(plot.getRegion().getChunks());

        for (Player player : players) {
            final double y = player.getLocation().getBlockY();
            for (int i = 0; i < 5; i++) {
                final double height = y + (i - 2) * 5;
                new OpTimerRunnable(() -> {
                    for (Location location : locations) {
                        spawnParticle(player, location.getX(), height, location.getZ(), material);
                    }
                }, duration);
            }
        }
    }

    public static void highlightWithParticles(@NotNull Plot plot, @NotNull List<Player> players, int duration) {
        final List<Location> locations = getBorderLocations(plot.getRegion().getChunks());

        for (Player player : players) {
            final double y = player.getLocation().getBlockY();
            for (int i = 0; i < 7; i++) {
                final double height = y + (i - 2) * 3;
                new OpTimerRunnable(() -> {
                    for (Location location : locations) {
                        spawnParticle(player, location.getX(), height, location.getZ(), plot.getBorder().getParticleColor(plot, player));
                    }
                }, duration);
            }
        }
    }

    public static void highlight(@NotNull Plot plot, @NotNull List<Player> players, int duration) {
        if (plot.getSpecials().getPlotBorderHighlightMaterial() != null) {
            highlightWithBlocks(plot, players, duration, plot.getSpecials().getPlotBorderHighlightMaterial());
        } else {
            highlightWithParticles(plot, players, duration);
        }
    }
}