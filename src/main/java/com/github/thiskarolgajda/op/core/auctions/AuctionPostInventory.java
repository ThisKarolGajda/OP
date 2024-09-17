package com.github.thiskarolgajda.op.core.auctions;

import com.github.thiskarolgajda.op.core.user.economy.MoneyTextFormatter;
import com.github.thiskarolgajda.op.utils.ConfirmationInventory;
import com.github.thiskarolgajda.op.utils.HeadsType;
import lombok.Data;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.gson.ItemStackWrapper;
import me.opkarol.oplibrary.injection.messages.StringMessage;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.misc.StringIconUtil;
import me.opkarol.oplibrary.misc.StringUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AuctionPostInventory extends ChestInventory {

    public static StringMessage invalidItemProvided = new StringMessage("Wprowadzono nieprawidłowy przedmiot!");
    public static StringMessage invalidPriceProvided = new StringMessage("Wprowadzono nieprawidłowy koszt!");
    public static StringMessage addedAuction = new StringMessage("Dodano aukcję!");

    public AuctionPostInventory(Player player, AuctionPostData data) {
        super(3, "Wystaw przedmiot");

        if (data.getItem() != null) {
            List<String> lore = new ArrayList<>();
            if (data.getItem().getItemMeta() != null && data.getItem().getItemMeta().getLore() != null && !data.getItem().getItemMeta().getLore().isEmpty()) {
                lore.addAll(data.getItem().getItemMeta().getLore());
            }

            setItem(new ItemStackTranslatable("%item%", lore), 10, data.getItem(), event -> {
                event.setCancelled(true);
            }, Map.of("%item%", data.getItem().getItemMeta() == null ? "" : data.getItem().getItemMeta().getDisplayName()));
        } else {
            setItem(item("Pusto!", List.of("Wybierz dowolny przedmiot ze swojego ekwipunku (naciskająć na niego LPM).")), 10, Material.LIGHT_GRAY_STAINED_GLASS_PANE, event -> {
                event.setCancelled(true);
            });
        }

        setItem(item("Rezerwacja kupna", List.of("Włączone: %enabled%", "Właczenie tej opcji wymusi ręcznego potwierdzenia sprzedaży.")), 13, HeadsType.SPECIALITY.getHead(), event -> {
            event.setCancelled(true);
            data.setReserved(!data.isReserved());
            new AuctionPostInventory(player, data);
        }, Map.of("%enabled%", StringIconUtil.getReturnedEmojiFromBoolean(data.isReserved())));

        setItem(item("Zmień cenę", List.of("Obecna: %price%")), 16, HeadsType.MONEY_BAG.getHead(), event -> {
            event.setCancelled(true);
            new AuctionPostPriceAnvilInventory(player, (stringPrice) -> {
                long price = StringUtil.getIntFromString(stringPrice);
                if (price < 0) {
                    new AuctionPostInventory(player, data);
                    return;
                }

                data.setPrice(price);
                new AuctionPostInventory(player, data);
            });
        }, Map.of("%price%", data.getPrice() < 0 ? "-" : MoneyTextFormatter.format(data.getPrice())));

        setItem(item("Wystaw aukcję"), 22, Material.GREEN_WOOL, event -> {
            new ConfirmationInventory(player, "Potwierdź aukcję", () -> {
                ItemStack item = player.getOpenInventory().getBottomInventory().getItem(data.slot);

                if (item == null || !item.equals(data.item)) {
                    data.setItem(null);
                    new AuctionPostInventory(player, data);
                    invalidItemProvided.error(player);
                    return;
                }

                if (data.price == -1) {
                    new AuctionPostInventory(player, data);
                    invalidPriceProvided.error(player);
                    return;
                }

                AuctionDatabase database = Plugin.get(AuctionDatabase.class);
                database.save(new AuctionObject(database.getUnusedUUID(), player.getUniqueId(), new ItemStackWrapper(item), LocalDateTime.now(), LocalDateTime.MAX, data.price));
                new AuctionsInventory(player);
                addedAuction.success(player);
            }, () -> new AuctionPostInventory(player, data));
        });

        setClickEventConsumer(event -> {
            if (event.getCurrentItem() == null || event.getClickedInventory() == null || event.getClickedInventory().getHolder() == null) {
                return;
            }

            if (event.getClickedInventory().getHolder().equals(player.getOpenInventory().getBottomInventory().getHolder())) {
                data.setItem(event.getCurrentItem().clone());
                data.setSlot(event.getSlot());
                new AuctionPostInventory(player, data);

            }
        });

        fillEmptyWithBlank();
        open(player);
    }

    public AuctionPostInventory(Player player) {
        this(player, new AuctionPostData());
    }

    @Data
    public static class AuctionPostData {
        private ItemStack item;
        private long price = -1;
        private boolean reserved = false;
        private int slot;
    }
}
