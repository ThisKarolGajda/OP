package com.github.thiskarolgajda.op.core.auctions;

import com.github.thiskarolgajda.op.core.user.economy.MoneyTextFormatter;
import com.github.thiskarolgajda.op.utils.ConfirmationInventory;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.extensions.Vault;
import me.opkarol.oplibrary.injection.formatter.LoreBuilder;
import me.opkarol.oplibrary.injection.messages.StringMessage;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.github.thiskarolgajda.op.OP.notEnoughMoney;
import static com.github.thiskarolgajda.op.utils.DateFormatter.STYLE_FORMATTER;

public class AuctionsInventory extends ChestInventory {

    public static StringMessage cannotBuyOwnAuction = new StringMessage("Nie można zakupić swojego przedmiotu!");
    public static StringMessage auctionExpired = new StringMessage("Ta aukcja wygasła!");
    public static StringMessage auctionBought = new StringMessage("Kupiono tą aukcję!");

    public AuctionsInventory(Player player) {
        super(5, "Aukcje");

        setListPattern(player, player::closeInventory);

        AuctionDatabase database = Plugin.get(AuctionDatabase.class);
        for (AuctionObject auction : database.getAll()) {
            OfflinePlayer seller = Bukkit.getOfflinePlayer(auction.getUuid());
            if (seller.getName() == null) {
                continue;
            }

            ItemStack item = auction.getItemStack().getItemStack();

            List<String> lore = new ArrayList<>();
            if (item.getItemMeta() != null && item.getItemMeta().getLore() != null && !item.getItemMeta().getLore().isEmpty()) {
                lore.addAll(item.getItemMeta().getLore());
                lore.add("");
            }

            lore.addAll(new LoreBuilder().lines(List.of("Rodzaj: %type%", "Sprzedający: %seller%", "Cena: %price%", "Stworzono: %created%", "Wygasa: %expire%")).build());

            setNextFree(new ItemStackTranslatable("%item%", lore), new ItemBuilder(item), event -> {
                event.setCancelled(true);

                if (seller.getUniqueId().equals(player.getUniqueId())) {
                    cannotBuyOwnAuction.error(player);
                    return;
                }

                // Check if auction is still available
                Optional<AuctionObject> optional = database.get(auction.getId());
                if (optional.isEmpty() || !optional.get().equals(auction)) {
                    new AuctionsInventory(player);
                    auctionExpired.error(player);
                    return;
                }

                if (Vault.remove(player, auction.getPrice()) != Vault.VAULT_RETURN_INFO.WITHDRAW_SUCCESSFUL) {
                    notEnoughMoney.error(player, MoneyTextFormatter.format(auction.getPrice()));
                    return;
                }

                Vault.add(seller, auction.getPrice());
                new ConfirmationInventory(player, "Potwierdź zakup", () -> {
                    database.delete(auction.getId());
                    player.getInventory().addItem(item);
                    auctionBought.success(player);
                    new AuctionsInventory(player);
                }, () -> new AuctionsInventory(player));
            }, Map.of("%item%", item.getItemMeta() == null ? "" : item.getItemMeta().getDisplayName(), "%seller%", seller.getName(), "%type%", item.getType().name(), "%price%", MoneyTextFormatter.format(auction.getPrice()), "%created%", STYLE_FORMATTER.format(auction.getCreationDate()), "%expire%", STYLE_FORMATTER.format(auction.getExpirationDate())));
        }

        open(player);
    }
}
