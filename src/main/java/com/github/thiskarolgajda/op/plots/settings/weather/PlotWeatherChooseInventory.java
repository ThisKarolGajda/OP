package com.github.thiskarolgajda.op.plots.settings.weather;

import com.github.thiskarolgajda.op.permission.PermissionType;
import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import com.github.thiskarolgajda.op.plots.settings.PlotSettingsInventory;
import me.opkarol.oplibrary.extensions.Vault;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.injection.formatter.LoreBuilder;
import me.opkarol.oplibrary.injection.messages.StringMessage;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import me.opkarol.oplibrary.misc.StringIconUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import java.util.Map;
import java.util.Set;

import static com.github.thiskarolgajda.op.OP.notEnoughMoney;
import static com.github.thiskarolgajda.op.plots.settings.PlotSettingConfig.*;

public class PlotWeatherChooseInventory extends ChestInventory {
    @Inject
    private static PlotDatabase database;
    private final Plot plot;
    private final Player player;

    public static StringMessage boughtWeatherChooseSetting = new StringMessage("Zakupiono zmianę pogody!");
    public static StringMessage changedWeatherChooseSetting = new StringMessage("Zmieniono aktywną pogodę!");

    public PlotWeatherChooseInventory(Player player, Plot plot) {
        super(3, "Wybór pogody");
        this.player = player;
        this.plot = plot;

        setOptionItem(12, PlotSettingWeatherType.DEFAULT_WEATHER, Material.BLACK_WOOL);
        setOptionItem(13, PlotSettingWeatherType.CLEAR, Material.BROWN_WOOL);
        setOptionItem(14, PlotSettingWeatherType.RAIN, Material.RED_WOOL);

        setItemHome(22, player, () -> new PlotSettingsInventory(player, plot));

        fillEmptyWithBlank();
        open(player);
    }

    private void setOptionItem(int slot, PlotSettingWeatherType weatherType, Material material) {
        ItemBuilder itemBuilder = new ItemBuilder(material);
        boolean selected = plot.getSettings().getSelectedWeatherType() == weatherType;
        if (selected) {
            itemBuilder.setEnchants(Map.of(Enchantment.LUCK_OF_THE_SEA, 1));
            itemBuilder.setFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        }

        boolean owned = plot.getSettings().getOwnedWeatherTypes().contains(weatherType);
        setItem(item("%name%", LoreBuilder.create("Posiadane: %owned%", "Wybrane: %selected%").anyMouseButtonText(owned ? "wybrać" : "kupić")), slot, itemBuilder, event -> {
            event.setCancelled(true);
            if (owned) {
                plot.getSettings().setSelectedWeatherType(weatherType);
                database.save(plot);
                new PlotWeatherChooseInventory(player, plot);
                changedWeatherChooseSetting.success(player);
                Bukkit.getPluginManager().callEvent(new PlotWeatherChangedEvent(plot, weatherType));
            } else {
                double cost = weatherTypeBuyPrice;
                if (plot.getOwner().getPlayer() != null && PermissionType.CHEAPER_SETTINGS_COST.hasPermission(plot.getOwner().getPlayer())) {
                    cost = cost * discountedSettingsCostPercentage;
                }

                if (Vault.remove(player, cost) != Vault.VAULT_RETURN_INFO.WITHDRAW_SUCCESSFUL) {
                    notEnoughMoney.error(player, cost);
                    return;
                }

                Set<PlotSettingWeatherType> set = plot.getSettings().getOwnedWeatherTypes();
                set.add(weatherType);
                plot.getSettings().setOwnedWeatherTypes(set);
                plot.getSettings().setSelectedWeatherType(weatherType);
                database.save(plot);
                new PlotWeatherChooseInventory(player, plot);
                boughtWeatherChooseSetting.success(player);
                Bukkit.getPluginManager().callEvent(new PlotWeatherChangedEvent(plot, weatherType));
            }
        }, Map.of("%name%", weatherType.getName(), "%owned%", StringIconUtil.getReturnedEmojiFromBoolean(owned), "%selected%", StringIconUtil.getReturnedEmojiFromBoolean(selected)));
    }
}
