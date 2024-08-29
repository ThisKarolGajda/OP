package com.github.thiskarolgajda.op.plots.warp.inventories;

import com.github.thiskarolgajda.op.permission.PermissionType;
import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import com.github.thiskarolgajda.op.plots.inventories.PlotMainInventory;
import com.github.thiskarolgajda.op.plots.warp.PlotWarp;
import com.github.thiskarolgajda.op.plots.warp.review.WarpReviewsInventory;
import com.github.thiskarolgajda.op.utils.HeadsType;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.injection.formatter.LoreBuilder;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import me.opkarol.oplibrary.location.OpLocation;
import me.opkarol.oplibrary.misc.StringIconUtil;
import me.opkarol.oplibrary.translations.Messages;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

import static com.github.thiskarolgajda.op.OP.youCantUseThat;
import static me.opkarol.oplibrary.translations.Messages.sendMessage;

public class PlotWarpManageInventory extends ChestInventory {
    public PlotWarpManageInventory(Player player, @NotNull Plot plot) {
        super(3, "Zarządzanie warpem działki");

        setItemHome(22, player, () -> new PlotMainInventory(plot, player));

        PlotWarp warp = plot.getWarp();

        setItem(item("Zmień lokalizację", LoreBuilder.create("Obecna lokalizacja: %location%").anyMouseButtonText("przypisać obecną lokalizację")), 13, new ItemBuilder(Material.CRIMSON_ROOTS), event -> {
            event.setCancelled(true);

            Location location = player.getLocation();
            if (!plot.canLocationBeHome(location)) {
                Messages.sendMessage("plot.invalidHomeLocation", player);
                return;
            }

            warp.setLocation(new OpLocation(location));
            Messages.sendMessage("plot.warp.changedLocation", player, Map.of("%location%", warp.getLocation().toFamilyStringWithoutWorld()));
        }, Map.of("%location%", warp.getLocation().toFamilyStringWithoutWorld()));

        setItem(item("Zmień nazwę", LoreBuilder.create("Obecna nazwa: %name%").anyMouseButtonText("zmienić nazwę")), 10, HeadsType.NAME_TAG.getHead(), event -> {
            event.setCancelled(true);
            new PlotWarpChangeNameAnvilInventory(player, plot);
        }, Map.of("%name%", warp.getName()));

        setItem(item(warp.isEnabled() ? "Wyłącz" : "Włącz", List.of("Włączony: %enabled%")), 15, HeadsType.WARP.getHead(), event -> {
            event.setCancelled(true);
            if (warp.isEnabled()) {
                warp.setEnabled(false);
                sendMessage("plot.warp.disabled", player);
            } else {
                warp.setEnabled(true);
                sendMessage("plot.warp.enabled", player);
            }

            Plugin.get(PlotDatabase.class).save(plot);
            new PlotWarpManageInventory(player, plot);
        }, Map.of("%enabled%", StringIconUtil.getReturnedEmojiFromBoolean(warp.isEnabled())));

        setItem(item("Zmień kolor warpu", List.of("Obecny kolor: %color%")), 11, HeadsType.COLOR_BOX.getHead(), event -> {
            event.setCancelled(true);
            if (!PermissionType.WARP_SET_COLOR_NAME.hasPermission(player)) {
                youCantUseThat.error(player);
                return;
            }

            new PlotWarpColorNameInventory(player, plot);
        }, Map.of("%color%", warp.getColor().getName()));

        setItem(item("Oceny warpu", List.of("Ilość opini: %reviews%", "Średnia ocena: %average%")), 16, HeadsType.SMILING_STEVE.getHead(), event -> {
            event.setCancelled(true);
            new WarpReviewsInventory(player, plot);
        }, Map.of("%reviews%", String.valueOf(warp.getReviews().size()), "%average%", String.valueOf(warp.getAverageReviewStars())));

        fillEmptyWithBlank();
        open(player);
    }
}
