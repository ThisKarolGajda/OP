package com.github.thiskarolgajda.op.core.auctions;

import com.github.thiskarolgajda.op.utils.AbstractGetStringAnvilInventory;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Consumer;

public class AuctionPostPriceAnvilInventory extends AbstractGetStringAnvilInventory {
    public AuctionPostPriceAnvilInventory(Player player, Consumer<String> onNameChange) {
        super(player, onNameChange);
    }

    @Override
    public String getTitle() {
        return "Wprowadź cenę aukcji";
    }

    @Override
    public String getItemName() {
        return "";
    }

    @Override
    public List<String> getItemLore() {
        return List.of();
    }
}
