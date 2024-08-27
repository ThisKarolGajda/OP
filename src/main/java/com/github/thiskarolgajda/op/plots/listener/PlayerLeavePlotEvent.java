package com.github.thiskarolgajda.op.plots.listener;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.region.events.RegionLeaveEvent;
import com.github.thiskarolgajda.op.utils.OpEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

@AllArgsConstructor
@Getter
public class PlayerLeavePlotEvent extends OpEvent {
    private final Player player;
    private final Plot plot;
    private final RegionLeaveEvent.RegionLeaveType leaveType;
}
