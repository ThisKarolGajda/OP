package com.github.thiskarolgajda.op.plots.homes;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import com.github.thiskarolgajda.op.utils.ConfirmationInventory;
import me.opkarol.oplibrary.injection.Inject;
import me.opkarol.oplibrary.injection.formatter.LoreBuilder;
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

        setItemHome(22, player, () -> new PlotHomesInventory(player, plot));

        PlotHomes homes = plot.getHomes();

        setItem(item("Usuń dom", LoreBuilder.create("Tego nie da się cofnąć!").anyMouseButtonText("usunąć dom")), 23, new ItemBuilder(Material.BARRIER), event -> {
            event.setCancelled(true);

            if (homes.getHomes().size() <= 1) {
                cantDeleteSingleHome.error(player);
                return;
            }

            new ConfirmationInventory(player, "Potwierdź usunięcie domu", () -> {
                homes.delete(home.getId());
                database.save(plot);
                new PlotHomesInventory(player, plot);
                removedPlotHome.success(player);
            }, () -> new PlotHomeManageInventory(player, plot, home));
        });

        setItem(item("Zmień nazwę", LoreBuilder.create("Obecna nazwa: %name%").anyMouseButtonText("zmienić nazwę")), 10, new ItemBuilder(Material.NAME_TAG), event -> {
            event.setCancelled(true);
            new PlotHomeChangeNameAnvilInventory(player, (string) -> {
                player.closeInventory();
                home.setName(string);
                homes.saveHome(home);
                new PlotHomeManageInventory(player, plot, home);
                changedPlotHomeName.success(player);
            });
        }, Map.of("%name%", home.getName()));

        setItem(item("Zmień lokalizację", LoreBuilder.create("Obecna lokalizacja: %location%").anyMouseButtonText("przypisać obecną lokalizację")), 12, new ItemBuilder(Material.CRIMSON_ROOTS), event -> {
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

        setItem(item("Zmień ikonę", LoreBuilder.create("Obecna ikona: %icon%").anyMouseButtonText("wylosować ikonę działki")), 14, new ItemBuilder(home.getIcon()), event -> {
            event.setCancelled(true);
            home.setRandomIcon();
            homes.saveHome(home);
            new PlotHomeManageInventory(player, plot, home);
            Messages.sendMessage("plot.changedIcon", player);
            changedPlotIcon.success(player);
        }, Map.of("%icon%", home.getIcon().name()));

        setItem(item("Zmień dostęp", LoreBuilder.create("Obecny dostęp: %access%").anyMouseButtonText("zmienić dostęp do domu")), 16, new ItemBuilder(Material.BAMBOO_DOOR), event -> {
            event.setCancelled(true);
            home.changeAccess();
            homes.saveHome(home);
            new PlotHomeManageInventory(player, plot, home);
            changedPlotAccess.success(player);
        }, Map.of("%access%", home.getAccessType().getName()));

        fillEmptyWithBlank();
        open(player);
    }
}
