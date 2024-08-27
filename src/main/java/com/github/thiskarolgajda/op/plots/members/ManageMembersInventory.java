package com.github.thiskarolgajda.op.plots.members;

import com.github.thiskarolgajda.op.core.user.invite.UserInviteDatabase;
import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import com.github.thiskarolgajda.op.plots.inventories.PlotMainInventory;
import com.github.thiskarolgajda.op.utils.ConfirmationInventory;
import com.github.thiskarolgajda.op.utils.playerrequest.PlayerRequest;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.injection.formatter.LoreBuilder;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import me.opkarol.oplibrary.tools.Heads;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static me.opkarol.oplibrary.translations.Messages.sendMessage;

public class ManageMembersInventory extends ChestInventory {

    public ManageMembersInventory(@NotNull Plot plot, Player player) {
        super(4, "Zarządzaj członkami działki");
        setListPattern(player, () -> new PlotMainInventory(plot, player));
        for (PlotMember member : plot.getMembers().getMembers()) {
            UUID uuid = member.uuid();
            OfflinePlayer clicked = Bukkit.getOfflinePlayer(uuid);
            ItemStack item;
            if (clicked.isOnline()) {
                item = Heads.get(clicked);
            } else {
                item = new ItemStack(Material.RED_WOOL);
            }

            setNextFree(item("%name%", LoreBuilder.create().leftMouseButtonText("usunąć członka").rightMouseButtonText("zarządzać rolami członka")), item, event -> {
                event.setCancelled(true);

                if (event.isLeftClick()) {
                    new ConfirmationInventory(player, "Potwierdź usunięcie członka", () -> {
                        plot.getMembers().remove(member.uuid());
                        Plugin.get(PlotDatabase.class).save(plot);

                        if (clicked.isOnline()) {
                            sendMessage("plot.users.removedFromPlot", clicked.getPlayer(), Map.of("%plot%", plot.getName(), "%owner%", player.getName()));
                        }

                        sendMessage("plot.users.removedPlayerFromPlot", player, Map.of("%plot%", plot.getName(), "%player%", Objects.requireNonNull(clicked.getName())));
                        new ManageMembersInventory(plot, player);
                    }, () -> new ManageMembersInventory(plot, player));
                }

                if (event.isRightClick()) {
                    new PlotMemberRolesInventory(player, plot, member);
                }
            }, Map.of("%name%", clicked.getName() == null ? "" : clicked.getName()));
        }

        setNextFree(item("Zaproś członka"), new ItemBuilder(Material.GREEN_TERRACOTTA), event -> {
            new PlayerRequest(player, (target) -> {
                if (!plot.hasSpaceForNewMember()) {
                    sendMessage("plot.users.notEnoughSpace", player);
                    return;
                }

                if (plot.isIgnored(target.getUniqueId())) {
                    sendMessage("plot.users.ignoredCantBeMember", player);
                    return;
                }

                if (plot.isAdded(target.getUniqueId())) {
                    sendMessage("plot.users.alreadyMember", player);
                    return;
                }

                Plugin.get(UserInviteDatabase.class).savePlotInvite(player.getUniqueId(), target.getUniqueId(), plot.getId());
                player.closeInventory();
                new ManageMembersInventory(plot, player);
            }, () -> new ManageMembersInventory(plot, player));
        });

        open(player);
    }
}
