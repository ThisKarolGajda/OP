package com.github.thiskarolgajda.op.core.auctions;

import me.opkarol.oplibrary.commands.annotations.Command;
import me.opkarol.oplibrary.commands.annotations.Default;
import me.opkarol.oplibrary.commands.annotations.NoUse;
import org.bukkit.entity.Player;

@Command("aukcja")
public class AuctionCommand {

    @Default
    public void defaultCommand(Player player) {
        new AuctionPostInventory(player);
//        AuctionDatabase database = Plugin.get(AuctionDatabase.class);
//        ItemStack item = player.getInventory().getItemInMainHand();
//
//        AuctionObject object = new AuctionObject(database.getUnusedUUID(), player.getUniqueId(), new ItemStackWrapper(item), LocalDateTime.now(), LocalDateTime.MAX, 50);
//        database.save(object);
    }

    @NoUse
    public void noUse(Player player) {
        new AuctionsInventory(player);
    }

}
