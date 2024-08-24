package com.github.thiskarolgajda.op.core.user.invite;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.misc.Books;
import me.opkarol.oplibrary.translations.Messages;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.github.thiskarolgajda.op.utils.DateFormatter.STYLE_FORMATTER;

public class UserInvitesInventory extends ChestInventory {

    public UserInvitesInventory(Player player) {
        super(4, "user_invites");
        setListPattern(player);

        Set<UserInvite> invites = Plugin.get(UserInviteDatabase.class).getInvites(player.getUniqueId());
        for (UserInvite invite : invites) {
            UUID plotUUID = UUID.fromString(invite.object());
            Optional<Plot> village = Plugin.get(PlotDatabase.class).get(plotUUID);
            if (village.isEmpty()) {
                continue;
            }

            setNextFree(item("invite"), Books.getRandomHead(invite.type().name()), event -> {
                event.setCancelled(true);
                if (event.isLeftClick()) {
                    invite.accept();
                    Messages.sendMessage("player.acceptedInvite", player);
                } else if (event.isRightClick()) {
                    invite.delete();
                    Messages.sendMessage("player.declinedInvite", player);
                }

                new UserInvitesInventory(player);
            }, Map.of(
                    "%name%", invite.getSenderName(),
                    "%date%", invite.creationDate().format(STYLE_FORMATTER),
                    "%type%", invite.type().getName(),
                    "%value%", village.get().getName()
            ));}

        open(player);
    }
}
    