package com.github.thiskarolgajda.op.plots.ignored;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class PlotIgnored {
    private final List<UUID> ignored = new ArrayList<>();

    public void add(UUID uuid) {
        ignored.add(uuid);
    }

    public void remove(UUID uuid) {
        ignored.removeIf(uuid1 -> uuid1.equals(uuid));
    }
}
