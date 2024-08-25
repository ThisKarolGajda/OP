package com.github.thiskarolgajda.op.plots.effects;

import com.github.thiskarolgajda.op.plots.Plot;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class PlotEffectsChangeCurrentEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Plot plot;

    public PlotEffectsChangeCurrentEvent(Plot plot) {
        this.plot = plot;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
