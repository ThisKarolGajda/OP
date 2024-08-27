package com.github.thiskarolgajda.op.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@Getter
public abstract class OpEvent extends Event {
    @Getter
    private static final HandlerList handlerList = new HandlerList();

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
