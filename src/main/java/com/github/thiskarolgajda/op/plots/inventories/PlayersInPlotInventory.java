package com.github.thiskarolgajda.op.plots.inventories;

import com.github.thiskarolgajda.op.plots.Plot;
import me.opkarol.oplibrary.inventories.ChestInventory;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayersInPlotInventory extends ChestInventory {

    public PlayersInPlotInventory(Player player, @NotNull Plot plot) {
        super(4, "Gracze na działce");
        setListPattern(player);

        //fixme use regions
//        for (UUID uuid : PlotEventListener.PLAYERS_INSIDE_REGIONS.getOrDefault(plot.getRegionIdentifier(), new ArrayList<>())) {
//            Player target = Bukkit.getPlayer(uuid);
//            if (target == null) {
//                continue;
//            }
//
//            Location location = target.getLocation();
//            boolean isMember = plot.isAdded(target.getUniqueId());
//            setNextFree("player", Heads.get(target), event -> event.setCancelled(true), Map.of(
//                    "%name%", target.getName(),
//                    "%location%", new OpSerializableLocation(location).toFamilyStringWithoutWorld(),
//                    "%member%", StringIconUtil.getReturnedEmojiFromBoolean(isMember)
//            ));
//        }

        open(player);
    }
}
    