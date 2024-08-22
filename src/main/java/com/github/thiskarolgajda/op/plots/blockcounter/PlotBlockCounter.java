package com.github.thiskarolgajda.op.plots.blockcounter;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class PlotBlockCounter {
    private final Map<PlotBlockCounterType, Integer> amounts = new HashMap<>();

    public void increment(PlotBlockCounterType type) {
        amounts.put(type, get(type) + 1);
    }

    public void decrement(PlotBlockCounterType type) {
        amounts.put(type, get(type) - 1);
    }

    public int get(PlotBlockCounterType type) {
        return amounts.getOrDefault(type, 0);
    }

    public int getTotalValue() {
        int total = 0;
        for (PlotBlockCounterType type : amounts.keySet()) {
            total += amounts.get(type) * type.getValue();
        }

        return total;
    }
}
