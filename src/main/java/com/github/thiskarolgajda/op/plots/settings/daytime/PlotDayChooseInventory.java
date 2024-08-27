package com.github.thiskarolgajda.op.plots.settings.daytime;

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
import static com.github.thiskarolgajda.op.plots.settings.PlotSettingConfig.dayTypeBuyPrice;
import static com.github.thiskarolgajda.op.plots.settings.PlotSettingConfig.discountedSettingsCostPercentage;

public class PlotDayChooseInventory extends ChestInventory {
    @Inject
    private static PlotDatabase database;
    private final Plot plot;
    private final Player player;

    public static StringMessage boughtDayChooseSetting = new StringMessage("Zakupiono zmianę pory dnia!");
    public static StringMessage changedDayChooseSetting = new StringMessage("Zmieniono aktywną porę dnia!");

    public PlotDayChooseInventory(Player player, Plot plot) {
        super(3, "Wybór pory dnia");
        this.player = player;
        this.plot = plot;

        setOptionItem(9, PlotSettingDayType.DAWN, Material.BLACK_WOOL);
        setOptionItem(10, PlotSettingDayType.MORNING, Material.BROWN_WOOL);
        setOptionItem(11, PlotSettingDayType.NOON, Material.RED_WOOL);
        setOptionItem(12, PlotSettingDayType.SUNSET, Material.ORANGE_WOOL);
        setOptionItem(13, PlotSettingDayType.DUSK, Material.YELLOW_WOOL);
        setOptionItem(14, PlotSettingDayType.NIGHT, Material.LIME_WOOL);
        setOptionItem(15, PlotSettingDayType.MIDNIGHT, Material.CYAN_WOOL);
        setOptionItem(16, PlotSettingDayType.DEFAULT_DAY, Material.LIGHT_BLUE_WOOL);

        setItemHome(22, player, () -> new PlotSettingsInventory(player, plot));

        fillEmptyWithBlank();
        open(player);
    }

    private void setOptionItem(int slot, PlotSettingDayType dayType, Material material) {
        ItemBuilder itemBuilder = new ItemBuilder(material);
        boolean selected = plot.getSettings().getSelectedDayType() == dayType;
        if (selected) {
            itemBuilder.setEnchants(Map.of(Enchantment.LUCK_OF_THE_SEA, 1));
            itemBuilder.setFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        }

        boolean owned = plot.getSettings().getOwnedDayTypes().contains(dayType);
        setItem(item("%name%", LoreBuilder.create("Posiadane: %owned%", "Wybrane: %selected%").anyMouseButtonText(owned ? "wybrać" : "kupić")), slot, itemBuilder, event -> {
            event.setCancelled(true);
            if (owned) {
                plot.getSettings().setSelectedDayType(dayType);
                database.save(plot);
                new PlotDayChooseInventory(player, plot);
                changedDayChooseSetting.success(player);
                Bukkit.getPluginManager().callEvent(new PlotDayChangedEvent(plot, dayType));
            } else {
                double cost = dayTypeBuyPrice;
                if (plot.getOwner().getPlayer() != null && PermissionType.CHEAPER_SETTINGS_COST.hasPermission(plot.getOwner().getPlayer())) {
                    cost = cost * discountedSettingsCostPercentage;
                }

                if (Vault.remove(player, cost) != Vault.VAULT_RETURN_INFO.WITHDRAW_SUCCESSFUL) {
                    notEnoughMoney.error(player, cost);
                    return;
                }

                Set<PlotSettingDayType> set = plot.getSettings().getOwnedDayTypes();
                set.add(dayType);
                plot.getSettings().setOwnedDayTypes(set);
                plot.getSettings().setSelectedDayType(dayType);
                database.save(plot);
                new PlotDayChooseInventory(player, plot);
                boughtDayChooseSetting.success(player);
                Bukkit.getPluginManager().callEvent(new PlotDayChangedEvent(plot, dayType));
            }
        }, Map.of("%name%", dayType.getName(), "%owned%", StringIconUtil.getReturnedEmojiFromBoolean(owned), "%selected%", StringIconUtil.getReturnedEmojiFromBoolean(selected)));
    }
}
