package com.github.thiskarolgajda.op.plots.listener;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import com.github.thiskarolgajda.op.plots.PlotHighlighter;
import com.github.thiskarolgajda.op.region.events.RegionEnterEvent;
import com.github.thiskarolgajda.op.region.events.RegionEventListener;
import com.github.thiskarolgajda.op.region.events.RegionLeaveEvent;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.listeners.Listener;
import me.opkarol.oplibrary.wrappers.OpBossBar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PlotListener extends Listener {
    @Inject
    private static PlotDatabase database;

    public PlotListener() {
        Plugin.runTimerAsync(() -> {
            for (Map.Entry<String, List<UUID>> entry : RegionEventListener.getPlayersInsideRegionData().entrySet()) {
                Optional<Plot> optional = database.getPlotFromRegionData(entry.getKey());
                if (optional.isPresent()) {
                    Plot plot = optional.get();
                    List<Player> players = entry.getValue().stream().map(Bukkit::getPlayer).toList();
                    if (plot.getSpecials().isPlotBossBarVisible()) {
                        OpBossBar bossBar = new OpBossBar("Jesteś na działce " + plot.getName());
                        bossBar.displayAndRemoveAfter(players, 10);
                    }

                    if (plot.getBorder().isDisplayBorderInsidePlot()) {
                        PlotHighlighter.highlight(plot, players, 1);
                    }
                }
            }
        }, 10L);
    }

    public static List<Player> getPlayersInsidePlot(@NotNull Plot plot) {
        return RegionEventListener.getPlayersInsideRegionData()
                .getOrDefault(plot.getPlotId().toString(), new ArrayList<>())
                .stream()
                .map(Bukkit::getPlayer)
                .toList();
    }

    @EventHandler(ignoreCancelled = true)
    public void playerEnterRegion(RegionEnterEvent event) {
        Optional<Plot> optional = database.getPlot(event.getRegion());
        if (optional.isPresent()) {
            Plot plot = optional.get();
            if (plot.getSpecials().isPlotEnterDisabled()) {
                event.setCancelled(true);
                if (event.getEnterType() == RegionEnterEvent.RegionEnterType.MOVE) {
                    pushPlayerBack(event.getPlayer());
                }
            }

            Bukkit.getPluginManager().callEvent(new PlayerEnterPlotEvent(event.getPlayer(), plot, event.getEnterType()));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void playerLeaveRegion(RegionLeaveEvent event) {
        Optional<Plot> optional = database.getPlot(event.getRegion());
        if (optional.isPresent()) {
            Plot plot = optional.get();
            if (plot.getSpecials().isPlotLeaveDisabled()) {
                event.setCancelled(true);
                if (event.getLeaveType() == RegionLeaveEvent.RegionLeaveType.MOVE) {
                    pushPlayerBack(event.getPlayer());
                }
            }

            Bukkit.getPluginManager().callEvent(new PlayerLeavePlotEvent(event.getPlayer(), plot, event.getLeaveType()));
        }
    }

    private void pushPlayerBack(Player player) {
        Plugin.run(() -> {
            Vector direction = player.getLocation().getDirection();
            direction.setY(0);
            Vector reverseDirection = direction.multiply(-1).normalize();
            reverseDirection.setY(0.5);
            player.setVelocity(reverseDirection.multiply(1.5));
            player.getWorld().createExplosion(player.getLocation(), 0F, false, false);
        });
    }
}
