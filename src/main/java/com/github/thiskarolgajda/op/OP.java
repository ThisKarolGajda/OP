package com.github.thiskarolgajda.op;

import com.github.thiskarolgajda.op.core.user.economy.MoneyTextFormatter;
import com.github.thiskarolgajda.op.core.user.economy.UserEconomyManager;
import com.github.thiskarolgajda.op.core.user.economy.VaultImpl;
import com.github.thiskarolgajda.op.core.user.tags.UserTagsDatabase;
import com.github.thiskarolgajda.op.core.warps.WarpsDatabase;
import com.github.thiskarolgajda.op.core.warps.custom.SpawnWarp;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import com.github.thiskarolgajda.op.region.RegionListener;
import com.github.thiskarolgajda.op.region.RegionDatabase;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.extensions.Vault;
import me.opkarol.oplibrary.injection.DependencyInjection;
import me.opkarol.oplibrary.injection.messages.StringMessage;
import org.bukkit.plugin.ServicePriority;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public final class OP extends Plugin {

    public static StringMessage notEnoughMoney = StringMessage.arg("Nie masz wystarczająco pieniędzy, potrzeba %money%!", object -> {
        if (object instanceof String string) {
            return Map.of("%money%", string);
        }

        if (object instanceof Number number) {
            return Map.of("%money%", MoneyTextFormatter.format(number));
        }

        return Map.of("%money%", object.toString());
    });
    public static StringMessage playerNotOnline = new StringMessage("Gracz nie jest na serwerze!");
    public static StringMessage youCantUseThat = new StringMessage("Nie możesz tego zrobić!");

    @Override
    public void enable() {
        DependencyInjection.registerInject(new RegionDatabase());
        DependencyInjection.registerInject(new RegionListener());

        DependencyInjection.registerInject(new UserEconomyManager());

        setupEconomy();
        DependencyInjection.registerInject(new Vault());

        DependencyInjection.registerInject(new WarpsDatabase());

        DependencyInjection.registerInject(new UserTagsDatabase());

        DependencyInjection.registerInject(new PlotDatabase());

        DependencyInjection.registerInject(new SpawnWarp());
    }

    private void setupEconomy() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            return;
        }

        this.getServer().getServicesManager().register(net.milkbowl.vault.economy.Economy.class, new VaultImpl(), this, ServicePriority.Highest);
    }

    @Override
    public void disable() {

    }

    @Override
    public @Nullable Integer registerBStatsOnStartup() {
        return null;
    }
}
