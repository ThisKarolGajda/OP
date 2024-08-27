package com.github.thiskarolgajda.op.plots.settings.animals;

import com.github.thiskarolgajda.op.core.user.economy.MoneyTextFormatter;
import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import com.github.thiskarolgajda.op.plots.settings.PlotSettingsInventory;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.extensions.Vault;
import me.opkarol.oplibrary.injection.formatter.LoreBuilder;
import me.opkarol.oplibrary.injection.messages.StringMessage;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.misc.Books;
import me.opkarol.oplibrary.misc.StringIconUtil;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Set;

import static com.github.thiskarolgajda.op.OP.notEnoughMoney;
import static com.github.thiskarolgajda.op.plots.config.PlotConfig.costForAnimalType;

public class PlotSettingAnimalInventory extends ChestInventory {
    public static StringMessage changedPlotAnimalSpawn = new StringMessage("Zmieniono spawn zwierząt na działce!");

    public PlotSettingAnimalInventory(Player player, Plot plot) {
        super(5, "Zarządzaj respawnem zwierzat");

        setListPattern(player, () -> new PlotSettingsInventory(player, plot));

        for (PlotSettingAnimalType animalType : PlotSettingAnimalType.values()) {
            boolean selected = plot.getSettings().getAnimalSpawn().getDisabledSpawns().contains(animalType);
            boolean owned = plot.getSettings().getAnimalSpawn().getOwnedDisabledSpawns().contains(animalType);

            setNextFree(item("%name%", LoreBuilder.create(owned ? "Posiadane: %owned%" : "Cena: %cost%", "Włączony spawn: %selected%").anyMouseButtonText(owned ? selected ? "włączyć spawn" : "wyłączyć spawn" : "kupić opcję wyłączenia")), Books.getRandomHead(animalType.name()), event -> {
                event.setCancelled(true);

                if (!owned) {
                    if (Vault.remove(player, costForAnimalType) != Vault.VAULT_RETURN_INFO.WITHDRAW_SUCCESSFUL) {
                        notEnoughMoney.error(player, costForAnimalType);
                        return;
                    }

                    Set<PlotSettingAnimalType> disabledSpawnsOwned = plot.getSettings().getAnimalSpawn().getOwnedDisabledSpawns();
                    disabledSpawnsOwned.add(animalType);
                    plot.getSettings().getAnimalSpawn().setOwnedDisabledSpawns(disabledSpawnsOwned);
                    Set<PlotSettingAnimalType> disabledSpawns = plot.getSettings().getAnimalSpawn().getDisabledSpawns();
                    disabledSpawns.add(animalType);
                    plot.getSettings().getAnimalSpawn().setDisabledSpawns(disabledSpawns);
                } else {
                    if (selected) {
                        Set<PlotSettingAnimalType> disabledSpawns = plot.getSettings().getAnimalSpawn().getDisabledSpawns();
                        disabledSpawns.remove(animalType);
                        plot.getSettings().getAnimalSpawn().setDisabledSpawns(disabledSpawns);
                    } else {
                        Set<PlotSettingAnimalType> disabledSpawns = plot.getSettings().getAnimalSpawn().getDisabledSpawns();
                        disabledSpawns.add(animalType);
                        plot.getSettings().getAnimalSpawn().setDisabledSpawns(disabledSpawns);
                    }
                }

                changedPlotAnimalSpawn.success(player);
                Plugin.get(PlotDatabase.class).save(plot);
                new PlotSettingAnimalInventory(player, plot);
            }, Map.of(
                    "%name%", animalType.getName(),
                    "%owned%", StringIconUtil.getReturnedEmojiFromBoolean(owned),
                    "%selected%", StringIconUtil.getReturnedEmojiFromBoolean(!selected),
                    "%cost%", MoneyTextFormatter.format(costForAnimalType)
            ));
        }

        open(player);
    }
}
