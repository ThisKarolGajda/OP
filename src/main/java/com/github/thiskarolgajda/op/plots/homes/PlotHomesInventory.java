package com.github.thiskarolgajda.op.plots.homes;

import com.github.thiskarolgajda.op.core.teleportation.TeleportationManager;
import com.github.thiskarolgajda.op.core.user.economy.MoneyTextFormatter;
import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import com.github.thiskarolgajda.op.plots.inventories.PlotMainInventory;
import com.github.thiskarolgajda.op.plots.warp.PlotWarp;
import com.github.thiskarolgajda.op.plots.warp.inventories.PlotWarpManageInventory;
import com.github.thiskarolgajda.op.utils.HeadsType;
import me.opkarol.oplibrary.extensions.Vault;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import me.opkarol.oplibrary.location.OpLocation;
import me.opkarol.oplibrary.misc.StringIconUtil;
import me.opkarol.oplibrary.translations.Messages;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Map;

import static com.github.thiskarolgajda.op.OP.notEnoughMoney;
import static com.github.thiskarolgajda.op.plots.config.PlotConfig.costForPlotHomeLimitUpgrade;
import static com.github.thiskarolgajda.op.plots.config.PlotConfig.defaultPlotHouseName;

public class PlotHomesInventory extends ChestInventory {
    @Inject
    private static PlotDatabase database;

    public PlotHomesInventory(Player player, Plot plot) {
        super(3, "Domy działki");

        setItemHome(26, player, () -> new PlotMainInventory(plot, player));

        PlotHomes homes = plot.getHomes();
        for (int i = 0; i < 3; i++) {
            int slot = 10 + i;
            if (homes.getHomes().size() > i) {
                PlotHome home = homes.getHomes().get(i);
                setItem(item("%name%"), slot, new ItemBuilder(home.getIcon()), event -> {
                    event.setCancelled(true);
                    if (event.isShiftClick()) {
                        return;
                    }

                    if (event.isLeftClick()) {
                        TeleportationManager.teleport(player, home);
                        player.closeInventory();
                        return;
                    }

                    new PlotHomeManageInventory(player, plot, home);
                }, Map.of("%name%", home.getName(), "%location%", home.getLocation().toFamilyStringWithoutWorld(), "%available%", StringIconUtil.getReturnedEmojiFromBoolean(home.isAvailable(plot, player))));
            } else {
                if (homes.getHomesLimit() <= i) {
                    setItem(item("Zwiększ limit"), slot, new ItemBuilder(Material.BARRIER), event -> {
                        event.setCancelled(true);
                        if (Vault.remove(player, costForPlotHomeLimitUpgrade) != Vault.VAULT_RETURN_INFO.WITHDRAW_SUCCESSFUL) {
                            notEnoughMoney.error(player, costForPlotHomeLimitUpgrade);
                            return;
                        }

                        homes.setHomesLimit(homes.getHomesLimit() + 1);
                        player.closeInventory();
                        database.save(plot);
                        new PlotHomesInventory(player, plot);
                    }, Map.of("%limit%", String.valueOf(homes.getHomesLimit()), "%price%", MoneyTextFormatter.format(costForPlotHomeLimitUpgrade)));
                    continue;
                }

                setItem(item("Pusto"), slot, HeadsType.GREY_HEAD.getHead(), event -> {
                    event.setCancelled(true);
                    if (homes.getHomesLimit() <= homes.getHomes().size()) {
                        Messages.sendMessage("homes.reachedLimit", player);
                        return;
                    }

                    Location location = player.getLocation();

                    if (!plot.canLocationBeHome(location)) {
                        Messages.sendMessage("plot.invalidHomeLocation", player);
                        return;
                    }

                    homes.saveHome(new PlotHome(homes.getUnusedUUID(), new OpLocation(location), defaultPlotHouseName));
                    database.save(plot);
                    new PlotHomesInventory(player, plot);
                });
            }
        }

        PlotWarp warp = plot.getWarp();
        if (warp.isEnabled()) {
            setItem(item("warp_home"), 16, HeadsType.WARP.getHead(), event -> {
                event.setCancelled(true);
                if (event.isShiftClick()) {
                    return;
                }

                if (event.isLeftClick()) {
                    TeleportationManager.teleport(player, warp);
                    return;
                }

                new PlotWarpManageInventory(player, plot);
            }, Map.of("%name%", warp.getName()));
        } else {
            setItem(item("warp_disabled"), 16, Material.BARRIER, event -> {
                event.setCancelled(true);
                new PlotWarpManageInventory(player, plot);
            }, Map.of("%name%", warp.getName()));
        }

        fillEmptyWithBlank();
        open(player);
    }
}
