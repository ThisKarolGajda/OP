package com.github.thiskarolgajda.op.region.events;

import com.github.thiskarolgajda.op.region.Region;
import com.github.thiskarolgajda.op.utils.OpCancellableEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
@Setter
@RequiredArgsConstructor
public class RegionLeaveEvent extends OpCancellableEvent {
    private final Region region;
    private final Player player;
    private final RegionLeaveEvent.RegionLeaveType leaveType;

    public enum RegionLeaveType {
        MOVE,
        TELEPORT,
        QUIT,
        PORTAL,
        RESPAWN,
    }
}
