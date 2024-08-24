package com.github.thiskarolgajda.op.core.user.economy;

import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.listeners.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class UserEconomyManager extends Listener {
    private final UserEconomyDatabase database = Plugin.get(UserEconomyDatabase.class);

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if (!database.contains(uuid)) {
            database.save(new UserEconomy(uuid));
        }
    }

    public int getPlayerPositionInEconomyRanking(UUID uuid) {
        UserEconomy userEconomy = database.getSafe(uuid);
        List<UserEconomy> list = database.getAll();
        int position = 1;
        for (UserEconomy economy : list) {
            if (economy.getGold() > userEconomy.getGold()) {
                position++;
            }
        }

        return position;
    }

    public UUID getPlayerFromPosition(int position) {
        List<UserEconomy> list = database.getAll();
        list.sort(Comparator.comparingDouble(UserEconomy::getGold).reversed());

        if (position <= 0 || position > list.size()) {
            return null;
        }

        UserEconomy targetEconomy = list.get(position - 1);
        return targetEconomy.getId();
    }

    public int getPlayersInRanking() {
        return database.getAll().size();
    }
}
