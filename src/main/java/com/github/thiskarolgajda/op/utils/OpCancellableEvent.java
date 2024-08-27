package com.github.thiskarolgajda.op.utils;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;

@Setter
@Getter
public class OpCancellableEvent extends OpEvent implements Cancellable {
    private boolean cancelled;
}
