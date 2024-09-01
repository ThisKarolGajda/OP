package com.github.thiskarolgajda.op.utils;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class OpEvent extends Event {
    @Getter
    private static final HandlerList handlerList = new HandlerList();

    public OpEvent(boolean isAsync) {
        super(isAsync);
    }

    public OpEvent() {
        this(false);
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
