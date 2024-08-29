package com.github.thiskarolgajda.op.region.events;

import com.github.thiskarolgajda.op.region.Region;
import com.github.thiskarolgajda.op.utils.OpEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
@Setter
@RequiredArgsConstructor
public class ExternalRegionEnterEvent extends OpEvent {
    private final Region region;
    private final Player player;
    private final RegionEnterEvent.RegionEnterType enterType;
}
