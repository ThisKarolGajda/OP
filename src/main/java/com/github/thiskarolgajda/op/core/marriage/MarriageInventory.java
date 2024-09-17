package com.github.thiskarolgajda.op.core.marriage;

import com.github.thiskarolgajda.op.utils.ConfirmationInventory;
import com.github.thiskarolgajda.op.utils.HeadsType;
import com.github.thiskarolgajda.op.utils.playerrequest.PlayerRequest;
import me.opkarol.oplibrary.injection.formatter.LoreBuilder;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.tools.Heads;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import static com.github.thiskarolgajda.op.utils.RandomItemCollector.random;

public class MarriageInventory extends ChestInventory {

    public MarriageInventory(Player player, MarriageData data) {
        super(3, "Ślub");

        UUID[] users = data.getUsers();
        for (int i = 0; i < users.length; i++) {
            int index = i;
            if (users[i] == null) {
                setItem(item("Wybierz " + (index == 0 ? "pierwszego" : "drugiego") + " gracza"), index == 0 ? 12 : 14, HeadsType.GREY_HEAD.getHead(), event -> {
                    event.setCancelled(true);
                    new PlayerRequest(player, player1 -> {
                        if (player1.getPlayer() != null) {
                            users[index] = player1.getUniqueId();
                            data.setUsers(users);
                        }

                        new MarriageInventory(player, data);
                    }, () -> new MarriageInventory(player, data));
                });
            } else {
                UUID user = users[i];
                Player target = Bukkit.getPlayer(user);
                if (target == null) {
                    users[i] = null;
                    new MarriageInventory(player, data);
                    return;
                }

                setItem(item(target.getName()), index == 0 ? 12 : 14, Heads.get(target), event -> {
                    event.setCancelled(true);
                });
            }
        }

        if (users.length == 2) {
            setItem(item("Ślub %name%", LoreBuilder.create().anyMouseButtonText("wylosować inny")), 25, HeadsType.COLOR_BOX.getHead(), event -> {
                event.setCancelled(true);
                data.setType(Arrays.stream(MarriageType.values()).collect(random()));
                new MarriageInventory(player, data);
            }, Map.of("%name%", data.getType().getName()));

            setItem(item("Wysłać żądania"), 26, HeadsType.TROPHY.getHead(), event -> {
                event.setCancelled(true);
                new ConfirmationInventory(player, "Czy na pewno chcesz to zrobić?", () -> {
                    //todo send marriage confirmations to users
                }, () -> new MarriageInventory(player, data));
            });
        }

        fillEmptyWithBlank();
        open(player);
    }

    public MarriageInventory(Player player) {
        this(player, new MarriageData(new UUID[2], Arrays.stream(MarriageType.values()).collect(random())));
    }
}
