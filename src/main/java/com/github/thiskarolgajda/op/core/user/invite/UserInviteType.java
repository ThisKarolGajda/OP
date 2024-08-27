package com.github.thiskarolgajda.op.core.user.invite;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.translations.Messages;
import org.bukkit.OfflinePlayer;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.github.thiskarolgajda.op.core.user.invite.UserInviteConfig.plotInvite;

public enum UserInviteType {
    // PLOT_UUID,
    PLOT_INVITE,
    ;

    public void accept(UserInvite userInvite) {
        if (this == UserInviteType.PLOT_INVITE) {
            UUID plotId = UUID.fromString(userInvite.object());
            PlotDatabase database = Plugin.get(PlotDatabase.class);
            Optional<Plot> optional = database.get(plotId);
            if (optional.isPresent()) {
                Plot plot = optional.get();
                if (plot.hasSpaceForNewMember()) {
                    plot.getMembers().add(userInvite.receiver());
                    database.save(plot);
                } else {
                    OfflinePlayer player = userInvite.getReceiver();
                    if (player.getPlayer() != null) {
                        Messages.sendMessage("player.cantJoinPlot", player.getPlayer());
                    }
                }
            }
        }
    }

    public String getName() {
        return (String) getMap().get("name");
    }

    public Map<String, Object> getMap() {
        return switch (this) {
            case PLOT_INVITE -> plotInvite;
        };
    }
}
