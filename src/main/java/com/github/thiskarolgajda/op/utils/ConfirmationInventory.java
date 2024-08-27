package com.github.thiskarolgajda.op.utils;

import lombok.Getter;
import me.opkarol.oplibrary.injection.formatter.LoreBuilder;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@Getter
public class ConfirmationInventory extends ChestInventory {
    protected final Player player;

    public ConfirmationInventory(Player player, String title, Runnable onConfirm, Runnable onCancel) {
        super(3, title);
        this.player = player;

        setConfirmationItem(onConfirm);
        setCancellationItem(onCancel);
        fillEmptyWithBlank();
        open(player);
    }

    private void setConfirmationItem(Runnable onConfirm) {
        setItem(item("Potwierdź", LoreBuilder.create().anyMouseButtonText("potwierdzić")), 15, new ItemBuilder(Material.GREEN_WOOL), event -> {
            event.setCancelled(true);
            onConfirm.run();
        });
    }

    private void setCancellationItem(Runnable onCancel) {
        setItem(item("Anuluj", LoreBuilder.create().anyMouseButtonText("anulować")), 11, new ItemBuilder(Material.RED_WOOL), event -> {
            event.setCancelled(true);
            onCancel.run();
        });
    }
}