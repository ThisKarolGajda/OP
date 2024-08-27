package com.github.thiskarolgajda.op.plots.members;

import com.github.thiskarolgajda.op.permission.PermissionType;
import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.misc.StringIconUtil;
import me.opkarol.oplibrary.tools.Heads;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class PlotMemberRolesInventory extends ChestInventory {

    public PlotMemberRolesInventory(Player player, @NotNull Plot plot, PlotMember member) {
        super(4, "Role gracza");

        if (!plot.isOwner(player.getUniqueId()) && !PermissionType.ADMIN.hasPermission(player)) {
            return;
        }

        setListPattern(player, () -> new ManageMembersInventory(plot, player));
        for (PlotPermissionsType permission : PlotPermissionsType.values()) {
            setNextFree(item("%name%", List.of("Dostępne: %available%")), Heads.get(permission.getTexture()), event -> {
                event.setCancelled(true);

                member.toggle(permission);
                Plugin.get(PlotDatabase.class).save(plot);

                new PlotMemberRolesInventory(player, plot, member);
            }, Map.of(
                    "%name%", permission.getName(),
                    "%available%", StringIconUtil.getReturnedEmojiFromBoolean(plot.isFeatureAvailable(Bukkit.getOfflinePlayer(member.uuid()), permission))
            ));
        }

        open(player);
    }
}
    