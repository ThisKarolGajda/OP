package com.github.thiskarolgajda.op.plots.expiration;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import com.github.thiskarolgajda.op.plots.PlotDeleter;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.runnable.OpRunnable;
import me.opkarol.oplibrary.tools.FormatTool;
import me.opkarol.oplibrary.tools.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static me.opkarol.oplibrary.translations.Messages.getTranslation;
import static me.opkarol.oplibrary.translations.Messages.sendMessage;

public class PlotExpirationManager {
    @Inject
    private static PlotDatabase database;

    @Inject
    public PlotExpirationManager() {
        // Remove plots on startup
        Plugin.runLater(() -> {
            List<Plot> toRemove = database.getAll()
                    .stream()
                    .filter(plot -> TimeUtils.hasTimePassed(plot.getExpiration().getExpirationUnix()))
                    .toList();

            if (!toRemove.isEmpty()) {
                for (Plot plot : toRemove) {
                    if (plot != null) {
                        removePlot(plot);
                    }
                }
            }
        }, 50L);


        // Task to expire plots
        for (Plot plot : database.getAll()) {
            registerPlotExpirationLeft(plot);
        }

        // Task to notice player that plot is expiring in less than 4 days
        new OpRunnable(() -> database
                .getAll().stream()
                .filter(plot -> TimeUtils.subtractFromTimestamp(plot.getExpiration().getExpirationUnix(), 4, TimeUtils.TimeUnit.DAY) < TimeUtils.getCurrent())
                .forEach(plot -> {
                    OfflinePlayer offlinePlayer = plot.getOwner();
                    if (offlinePlayer.isOnline()) {
//                        sendMessage("plot.plotWillExpireSoon", offlinePlayer.getPlayer(), Map.of("%expire%", plot.getExpirationLeftString()));
                    }
                })).runTaskTimerAsynchronously(0, TimeUtils.TimeUnit.MINUTE.toSeconds() * 20L * 15L);
    }

    public void registerPlotExpirationLeft(@NotNull Plot plot) {
        long timeLeft = Duration.between(LocalDateTime.now(), LocalDateTime.ofInstant(Instant.ofEpochMilli(plot.getExpiration().getExpirationUnix()), ZoneId.systemDefault())).toSeconds();
        Plugin.runLater(() -> {
            // Make sure it's the same plot, and it's not been extended
            if (database.get(plot.getId()).isEmpty()) {
                return;
            }

            long currentTimeMillis = System.currentTimeMillis();
            long timeDifferenceMillis = plot.getExpiration().getExpirationUnix() - currentTimeMillis;

            if (timeDifferenceMillis <= 0) {
                removePlot(plot);
            } else {
                // Player extended expiration
                registerPlotExpirationLeft(plot);
            }

        }, timeLeft * 20 + 20);
    }

    private void removePlot(Plot plot) {
        //TODO: sendPlotExpiredWebhook(plot);
        PlotDeleter.deletePlot(plot);
        String message = FormatTool.formatMessage(getTranslation("plot.expired").replace("%location%", plot.getHomes().getFirstHome().getLocation().toFamilyStringWithoutWorld()));
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(message);
        }

        if (plot.getOwner().getPlayer() != null) {
            sendMessage("plot.yourPlotExpired", plot.getOwner().getPlayer());
        }
    }
}
