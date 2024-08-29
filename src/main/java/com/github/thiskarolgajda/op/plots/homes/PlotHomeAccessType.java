package com.github.thiskarolgajda.op.plots.homes;

import static com.github.thiskarolgajda.op.plots.config.PlotConfig.*;

public enum PlotHomeAccessType {
    OWNER,
    MEMBER,
    GUEST,
    ;

    public String getName() {
        return switch (this) {
            case OWNER -> plotOwnerName;
            case MEMBER -> plotMemberName;
            case GUEST -> plotGuestName;
        };
    }
}
