package com.github.thiskarolgajda.op.plots.warp.inventories;

import com.github.thiskarolgajda.op.permission.PermissionType;
import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import com.github.thiskarolgajda.op.plots.inventories.PlotMainInventory;
import com.github.thiskarolgajda.op.plots.warp.PlotWarp;
import com.github.thiskarolgajda.op.plots.warp.review.WarpReviewsInventory;
import com.github.thiskarolgajda.op.utils.HeadsType;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import me.opkarol.oplibrary.location.OpLocation;
import me.opkarol.oplibrary.misc.StringIconUtil;
import me.opkarol.oplibrary.translations.Messages;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static me.opkarol.oplibrary.translations.Messages.sendMessage;

public class PlotWarpManageInventory extends ChestInventory {
    public PlotWarpManageInventory(Player player, @NotNull Plot plot) {
        super(3, "plot_warp_manage");

        setItemHome(22, player, () -> new PlotMainInventory(plot, player));

        PlotWarp warp = plot.getWarp();

        setItem(item("change_location"), 13, new ItemBuilder(Material.CRIMSON_ROOTS), event -> {
            event.setCancelled(true);

            Location location = player.getLocation();
            if (!plot.canLocationBeHome(location)) {
                Messages.sendMessage("plot.invalidHomeLocation", player);
                return;
            }

            warp.setLocation(new OpLocation(location));
            player.closeInventory();
            Messages.sendMessage("plot.warp.changedLocation", player, Map.of(
                    "%location%", warp.getLocation().toFamilyStringWithoutWorld()
            ));
        });

        setItem(item("change_name"), 10, HeadsType.NAME_TAG.getHead(), event -> {
            event.setCancelled(true);
            new PlotWarpChangeNameAnvilInventory(player, plot);
        }, Map.of("%name%", !warp.isEnabled() ? "---" : warp.getName(), "%color%", (!warp.isEnabled() ? "" : warp.getColor().getCode())));

        setItem(item("set_active"), 15, HeadsType.WARP.getHead(), event -> {
            event.setCancelled(true);
            if (warp.isEnabled()) {
                warp.setLocation(null);
                sendMessage("plot.warp.disabled", player);
            } else {
                warp.setLocation(new OpLocation(player.getLocation()));
                sendMessage("plot.warp.enabled", player);
            }

            Plugin.get(PlotDatabase.class).save(plot);
            new PlotWarpManageInventory(player, plot);
        }, Map.of("%available%", StringIconUtil.getReturnedEmojiFromBoolean(warp.isEnabled())));

        setItem(item("change_color_name"), 11, HeadsType.COLOR_BOX.getHead(), event -> {
            event.setCancelled(true);
            player.closeInventory();

            if (!PermissionType.WARP_SET_COLOR_NAME.hasPermission(player)) {
                sendMessage("youCantUseThat", player);
                return;
            }

            new PlotWarpColorNameInventory(player, plot);
        });

        setItem(item("reviews"), 16, HeadsType.SMILING_STEVE.getHead(), event -> {
            event.setCancelled(true);
            new WarpReviewsInventory(player, plot);
        });

        fillEmptyWithBlank();
        open(player);
    }
}
