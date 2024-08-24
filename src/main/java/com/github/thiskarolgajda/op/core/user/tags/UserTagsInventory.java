package com.github.thiskarolgajda.op.core.user.tags;

import com.github.thiskarolgajda.op.core.user.economy.MoneyTextFormatter;
import com.github.thiskarolgajda.op.core.user.profile.UserProfileInventory;
import com.github.thiskarolgajda.op.utils.HeadsType;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.extensions.Vault;
import me.opkarol.oplibrary.injection.formatter.LoreBuilder;
import me.opkarol.oplibrary.injection.messages.StringMessage;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import me.opkarol.oplibrary.misc.StringIconUtil;
import me.opkarol.oplibrary.misc.VillagerHeadGenerator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static com.github.thiskarolgajda.op.OP.notEnoughMoney;

public class UserTagsInventory extends ChestInventory {
    public static StringMessage setTag = StringMessage.arg("Ustawiono aktywny tag na %tag%!", object -> Map.of("%tag%", object.toString()));

    public UserTagsInventory(@NotNull Player player) {
        super(5, "Tagi użytkownika");

        UserTags userTags = Plugin.get(UserTagsDatabase.class).getSafe(player.getUniqueId());

        setGlobalItem(item("take_out"), 41, new ItemBuilder(Material.BARRIER), event -> {
            userTags.setSelected(null);
            Plugin.get(UserTagsDatabase.class).save(userTags);
            new UserTagsInventory(player);
            setTag.success(player, "---");
        });
        setListPattern(player, () -> new UserProfileInventory(player));

        for (UserTagType type : UserTagType.values()) {
            if (userTags.isOwned(type)) {
                setNextFree(item(type.getName(), LoreBuilder.create().anyMouseButtonText("wybrać")), VillagerHeadGenerator.getRandomHead(type.getName()), event -> {
                    event.setCancelled(true);
                    userTags.setSelected(type);
                    Plugin.get(UserTagsDatabase.class).save(userTags);
                    new UserTagsInventory(player);
                    setTag.success(player, type.getName());
                }, Map.of(
                        "%name%", type.getName(),
                        "%price%", MoneyTextFormatter.format(type.getPrice()),
                        "%current%", StringIconUtil.getReturnedEmojiFromBoolean(userTags.isSelected(type))
                ));
            } else {
                setNextFree(item(type.getName(), LoreBuilder.create("Cena: %price%").anyMouseButtonText("kupić")), HeadsType.GREY_HEAD.getHead(), event -> {
                    event.setCancelled(true);
                    if (Vault.remove(player, type.getPrice()) != Vault.VAULT_RETURN_INFO.WITHDRAW_SUCCESSFUL) {
                        notEnoughMoney.error(player, type.getPrice());
                        return;
                    }

                    userTags.addOwned(type);
                    userTags.setSelected(type);
                    Plugin.get(UserTagsDatabase.class).save(userTags);
                    new UserTagsInventory(player);
                    setTag.success(player, type.getName());
                }, Map.of(
                        "%name%", type.getName(),
                        "%price%", MoneyTextFormatter.format(type.getPrice())
                ));
            }
        }

        open(player);
    }
}
    