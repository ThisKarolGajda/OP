package com.github.thiskarolgajda.op.plots.homes;

import com.github.thiskarolgajda.op.core.user.homes.inventories.HomesInventory;
import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.injection.messages.StringMessage;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import me.opkarol.oplibrary.location.OpLocation;
import me.opkarol.oplibrary.translations.Messages;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Map;

public class PlotHomeManageInventory extends ChestInventory {

    public static StringMessage cantDeleteSingleHome = new StringMessage("Nie można usunąć jedynego domu działki");
    public static StringMessage changedPlotHomeName = new StringMessage("Zmieniono nazwę domu działki");
    public static StringMessage invalidHomeLocation = new StringMessage("Nieprawidłowa lokalizacja domu!");
    public static StringMessage changedPlotHomeLocation = StringMessage.arg("Zmieniono lokalizację domu na %location%", object -> Map.of("%location%", object.toString()));
    public static StringMessage removedPlotHome = new StringMessage("Usunięto dom działki!");
    public static StringMessage changedPlotIcon = new StringMessage("Zmieniono ikonę działki.");
    public static StringMessage changedPlotAccess = new StringMessage("Zmieniono dostęp do domu działki!");
    @Inject
    public static PlotDatabase database;

    public PlotHomeManageInventory(Player player, Plot plot, PlotHome home) {
        super(3, "Zarzadzanie domem działki");

        setItemHome(26, player, () -> new HomesInventory(player));

        PlotHomes homes = plot.getHomes();

        setItem(item("Usuń dom"), 25, new ItemBuilder(Material.BARRIER), event -> {
            event.setCancelled(true);

            if (homes.getHomes().size() <= 1) {
                cantDeleteSingleHome.error(player);
                return;
            }

            homes.delete(home.getId());
            player.closeInventory();
            database.save(plot);
            new PlotHomesInventory(player, plot);
            removedPlotHome.success(player);
        });

        setItem(item("Zmień nazwę"), 10, new ItemBuilder(Material.NAME_TAG), event -> {
            event.setCancelled(true);
            new PlotHomeChangeNameAnvilInventory(player, (string) -> {
                player.closeInventory();
                home.setName(string);
                homes.saveHome(home);
                new PlotHomeManageInventory(player, plot, home);
                changedPlotHomeName.success(player);
            });
        }, Map.of("%name%", home.getName()));

        setItem(item("Zmień lokalizację"), 12, new ItemBuilder(Material.CRIMSON_ROOTS), event -> {
            event.setCancelled(true);

            Location location = player.getLocation();
            if (!plot.canLocationBeHome(location)) {
                invalidHomeLocation.error(player);
                Messages.sendMessage("plot.invalidHomeLocation", player);
                return;
            }

            home.setLocation(new OpLocation(location));
            homes.saveHome(home);
            new PlotHomeManageInventory(player, plot, home);
            changedPlotHomeLocation.success(player, home.getLocation().toFamilyStringWithoutWorld());
        }, Map.of("%location%", home.getLocation().toFamilyStringWithoutWorld()));

        setItem(item("Zmień ikonę"), 14, new ItemBuilder(home.getIcon()), event -> {
            event.setCancelled(true);
            home.setRandomIcon();
            homes.saveHome(home);
            new PlotHomeManageInventory(player, plot, home);
            Messages.sendMessage("plot.changedIcon", player);
            changedPlotIcon.success(player);
        });

        setItem(item("Zmień dostęp"), 16, new ItemBuilder(Material.BAMBOO_DOOR), event -> {
            event.setCancelled(true);
            home.changeAccess();
            homes.saveHome(home);
            new PlotHomeManageInventory(player, plot, home);
            changedPlotAccess.success(player);
        }, Map.of("%access%", home.getAccessType().name()));

        fillEmptyWithBlank();
        open(player);
    }
}
