package com.github.thiskarolgajda.op.plots.blockcounter;

import me.opkarol.oplibrary.injection.inventories.GlobalInventory;
import me.opkarol.oplibrary.injection.inventories.items.ItemClick;
import me.opkarol.oplibrary.tools.Heads;

import java.util.List;
import java.util.Map;

public class PlotBlockCounterInventory {

    public static GlobalInventory plotBlockCounter = GlobalInventory.row6("Statystyki działek")
            .item("information", "Jak to liczyć?", List.of("Bloki specjalne mają poniższe wartości:",
                    "- Blok żelaza: %iron_block_value%",
                    "- Blok złota: %gold_block_value%",
                    "- Blok emeraldu: %emerald_block_value%",
                    "- Blok diamentu: %diamond_block_value%",
                    "- Blok netheritu: %netherite_block_value%",
                    "- Beacon: %beacon_value%"), 52, Heads.get("d01afe973c5482fdc71e6aa10698833c79c437f21308ea9a1a095746ec274a0f"), player -> Map.of(
                    "%iron_block_value%", String.valueOf(PlotBlockCounterType.IRON_BLOCK.getValue()),
                    "%netherite_block_value%", String.valueOf(PlotBlockCounterType.NETHERITE_BLOCK.getValue()),
                    "%diamond_block_value%", String.valueOf(PlotBlockCounterType.DIAMOND_BLOCK.getValue()),
                    "%emerald_block_value%", String.valueOf(PlotBlockCounterType.EMERALD_BLOCK.getValue()),
                    "%gold_block_value%", String.valueOf(PlotBlockCounterType.GOLD_BLOCK.getValue()),
                    "%beacon_value%", String.valueOf(PlotBlockCounterType.BEACON.getValue())
            ), ItemClick::cancel)
            .builder(inventory -> {

            })
            .fillAllEmpty();
}
