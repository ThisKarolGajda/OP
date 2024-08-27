package com.github.thiskarolgajda.op.plots.expiration;

import com.github.thiskarolgajda.op.core.user.economy.MoneyTextFormatter;
import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import com.github.thiskarolgajda.op.plots.inventories.PlotMainInventory;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.extensions.Vault;
import me.opkarol.oplibrary.injection.config.Config;
import me.opkarol.oplibrary.injection.messages.StringMessage;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import me.opkarol.oplibrary.tools.Heads;
import me.opkarol.oplibrary.tools.TimeUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

import static com.github.thiskarolgajda.op.OP.notEnoughMoney;

public class PlotExpirationInventory extends ChestInventory {
    @Config
    public static double expirationCostPerHour = 100;
    public static StringMessage extendedExpiration = new StringMessage("Przedłużono ważność działki");

    public PlotExpirationInventory(Player player, @NotNull Plot plot) {
        super(3, "Przedłuż ważność działki");

        setItem(item("Przedłuż o 1 dzień"), 10, Heads.get("71bc2bcfb2bd3759e6b1e86fc7a79585e1127dd357fc202893f9de241bc9e530"), event -> {
            event.setCancelled(true);
            tryToBuy(player, 1, plot);
        }, Map.of("%cost%", MoneyTextFormatter.format(expirationCostPerHour * 1)));
        setItem(item("Przedłuż o 12 godzin"), 11, Heads.get("916a7b8ba79a2efe5d9711f9f36cc6b28e31b474ac5a4924b4adf9fe9ea19a"), event -> {
            event.setCancelled(true);
            tryToBuy(player, 12, plot);
        }, Map.of("%cost%", MoneyTextFormatter.format(expirationCostPerHour * 12)));
        setItem(item("Przedłuż o dzień"), 12, Heads.get("71bc2bcfb2bd3759e6b1e86fc7a79585e1127dd357fc202893f9de241bc9e530"), event -> {
            event.setCancelled(true);
            tryToBuy(player, 24, plot);
        }, Map.of("%cost%", MoneyTextFormatter.format(expirationCostPerHour * 24)));
        setItem(item("Przedłuż o 3 dni"), 13, Heads.get("1d4eae13933860a6df5e8e955693b95a8c3b15c36b8b587532ac0996bc37e5"), event -> {
            event.setCancelled(true);
            tryToBuy(player, 72, plot);
        }, Map.of("%cost%", MoneyTextFormatter.format(expirationCostPerHour * 72)));
        setItem(item("Przedłuż o tydzień"), 14, Heads.get("6db6eb25d1faabe30cf444dc633b5832475e38096b7e2402a3ec476dd7b9"), event -> {
            event.setCancelled(true);
            tryToBuy(player, 168, plot);
        }, Map.of("%cost%", MoneyTextFormatter.format(expirationCostPerHour * 168)));
        setItem(item("Informacje", List.of("Pamiętaj o przedłużaniu! Jak nie przedłużysz, to twoja działka zostanie usunięta!", "Wygasa za: %expire_date%")), 16, new ItemBuilder(Material.ENCHANTED_BOOK), event -> event.setCancelled(true), Map.of("%expire_date%", plot.getExpiration().getTimeLeft()));

        setItemHome(22, player, () -> new PlotMainInventory(plot, player));

        fillEmptyWithBlank();
        open(player);
    }

    public void tryToBuy(Player player, int hours, Plot plot) {
        Vault vault = Vault.getInstance();

        double cost = expirationCostPerHour * hours;
        Vault.VAULT_RETURN_INFO returnInfo = vault.withdraw(player, cost);
        if (returnInfo == Vault.VAULT_RETURN_INFO.WITHDRAW_TOO_BROKE) {
            notEnoughMoney.error(player, cost);
            return;
        }

        if (returnInfo == Vault.VAULT_RETURN_INFO.WITHDRAW_SUCCESSFUL) {
            plot.getExpiration().addExpiration(TimeUtils.TimeUnit.HOUR.toMilliseconds() * hours);
            extendedExpiration.success(player);
            Plugin.get(PlotDatabase.class).save(plot);
        }

        player.closeInventory();
        new PlotExpirationInventory(player, plot);
    }
}
