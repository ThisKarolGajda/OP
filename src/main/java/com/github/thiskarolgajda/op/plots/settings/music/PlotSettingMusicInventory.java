package com.github.thiskarolgajda.op.plots.settings.music;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import com.github.thiskarolgajda.op.plots.settings.PlotSettingsInventory;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.injection.formatter.LoreBuilder;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import me.opkarol.oplibrary.misc.Books;
import me.opkarol.oplibrary.misc.StringIconUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

import java.util.Map;

public class PlotSettingMusicInventory extends ChestInventory {

    public PlotSettingMusicInventory(Player player, Plot plot) {
        super(5, "Muzyka na działce");
        setListPattern(player, () -> new PlotSettingsInventory(player, plot));

        for (SimpleSong song : PlotNoteBlockAPI.getSimpleSongs()) {
            boolean selected = plot.getSettings().getMusic().getSelectedSong() != null && plot.getSettings().getMusic().getSelectedSong().equals(song);
            ItemBuilder builder = Books.getRandomHead(song.title());
            builder.setFlags(ItemFlag.HIDE_ATTRIBUTES);
            builder.glow(selected);

            setNextFree(item("Piosenka %name%", LoreBuilder.create("Wybrana: %selected%").anyMouseButtonText(selected ? "usunąć" : "wybrać")), builder, event -> {
                event.setCancelled(true);
                if (selected) {
                    plot.getSettings().getMusic().setSelectedSong(null);
                } else {
                    plot.getSettings().getMusic().setSelectedSong(song);
                }

                Bukkit.getPluginManager().callEvent(new PlotMusicChangedEvent(plot, song));
                Plugin.get(PlotDatabase.class).save(plot);
                new PlotSettingMusicInventory(player, plot);
            }, Map.of("%name%", song.title(), "%selected%", StringIconUtil.getReturnedEmojiFromBoolean(selected)));
        }

        open(player);
    }
}
