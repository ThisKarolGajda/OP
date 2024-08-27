package com.github.thiskarolgajda.op.plots.inventories;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import com.github.thiskarolgajda.op.plots.PlotDeleter;
import com.github.thiskarolgajda.op.utils.ConfirmationInventory;
import com.github.thiskarolgajda.op.utils.HeadsType;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.injection.formatter.LoreBuilder;
import me.opkarol.oplibrary.injection.messages.StringMessage;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import me.opkarol.oplibrary.misc.StringIconUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

import static com.github.thiskarolgajda.op.plots.PlotFactory.generateRandomPlotName;
import static com.github.thiskarolgajda.op.utils.IconGenerator.getRandomBlock;

public class PlotAdminPanelInventory extends ChestInventory {

    public static StringMessage adminDeletedPlot = new StringMessage("Usunięto działkę");
    public static StringMessage adminChangedPlotName = StringMessage.arg("Zresetowano nazwę działki na %name%!", object -> Map.of("%name%", object.toString()));
    public static StringMessage adminChangedFlyingForEveryone = new StringMessage("Zmieniono latanie dla wszystkich na działce!");
    public static StringMessage adminChangedPlotChatForEveryone = new StringMessage("Zmieniono dostępność czatu dla wszystkich na działce!");
    public static StringMessage adminChangedPlotEnter = new StringMessage("Zmieniono wejście na działkę!");
    public static StringMessage adminChangedPlotLeave = new StringMessage("Zmieniono wyjścię z działki!");
    public static StringMessage adminChangedPlotBossBarDisplayed = new StringMessage("Zmieniono wyświetlanie boss baru na działce!");
    public static StringMessage adminChangedPlotAllPermissions = new StringMessage("Zmieniono działanie permisji na działce!");
    public static StringMessage adminChangedPlotBorderDisplay = new StringMessage("Zmieniono wygląd granicy działki!");

    public PlotAdminPanelInventory(Player player, Plot plot) {
        super(3, "Panel administratora działki");

        setItemHome(22, player, () -> new PlotMainInventory(plot, player));

        PlotDatabase database = Plugin.get(PlotDatabase.class);

        setItem(item("Usuń działkę"), 9, new ItemBuilder(Material.RED_WOOL), event -> {
            event.setCancelled(true);
            new ConfirmationInventory(player, "Potwierdź usunięcie działki", () -> {
                PlotDeleter.deletePlot(plot);
                player.closeInventory();
                adminDeletedPlot.success(player);
            }, () -> new PlotAdminPanelInventory(player, plot));
        });
        setItem(item("Zresetuj nazwę", List.of("Obecna nazwa: %name%")), 10, new ItemBuilder(Material.NAME_TAG), event -> {
            event.setCancelled(true);
            plot.setName(generateRandomPlotName(plot.getOwnerName()));
            database.save(plot);
            adminChangedPlotName.success(player, plot.getName());
            new PlotAdminPanelInventory(player, plot);
        }, Map.of("%name%", plot.getName()));
        setItem(item("Zmień latanie dla wszystkich", List.of("Włączone: %enabled%")), 11, HeadsType.FLYING_PIG.getHead(), event -> {
            event.setCancelled(true);
            plot.getSpecials().setFlyingEnabledForEveryone(!plot.getSpecials().isFlyingEnabledForEveryone());
            database.save(plot);
            adminChangedFlyingForEveryone.success(player);
            new PlotAdminPanelInventory(player, plot);
        }, Map.of("%enabled%", StringIconUtil.getReturnedEmojiFromBoolean(plot.getSpecials().isFlyingEnabledForEveryone())));
        setItem(item("Zablokuj czat na działce", List.of("Zablokowane: %enabled%")), 12, HeadsType.COLOR_BOX.getHead(), event -> {
            event.setCancelled(true);
            plot.getSpecials().setPlotChatDisabled(!plot.getSpecials().isPlotChatDisabled());
            database.save(plot);
            adminChangedPlotChatForEveryone.success(player);
            new PlotAdminPanelInventory(player, plot);
        }, Map.of("%enabled%", StringIconUtil.getReturnedEmojiFromBoolean(plot.getSpecials().isPlotChatDisabled())));
        setItem(item("Zablokuj wejście na działkę", List.of("Zablokowane: %enabled%")), 13, HeadsType.EXPERIENCE_CUBE.getHead(), event -> {
            event.setCancelled(true);
            plot.getSpecials().setPlotEnterDisabled(!plot.getSpecials().isPlotEnterDisabled());
            database.save(plot);
            adminChangedPlotEnter.success(player);
            new PlotAdminPanelInventory(player, plot);
        }, Map.of("%enabled%", StringIconUtil.getReturnedEmojiFromBoolean(plot.getSpecials().isPlotEnterDisabled())));
        setItem(item("Zablokuj wyjście z działki", List.of("Zablokowane: %enabled%")), 14, HeadsType.WAX_SEAL_RED_CHECKMARK.getHead(), event -> {
            event.setCancelled(true);
            plot.getSpecials().setPlotLeaveDisabled(!plot.getSpecials().isPlotLeaveDisabled());
            database.save(plot);
            adminChangedPlotLeave.success(player);
            new PlotAdminPanelInventory(player, plot);
        }, Map.of("%enabled%", StringIconUtil.getReturnedEmojiFromBoolean(plot.getSpecials().isPlotLeaveDisabled())));
        setItem(item("Wyświetlaj boss bar na działce", List.of("Właczone: %enabled%")), 15, HeadsType.WAX_SEAL_RED_CIRCLE.getHead(), event -> {
            event.setCancelled(true);
            plot.getSpecials().setPlotBossBarVisible(!plot.getSpecials().isPlotBossBarVisible());
            database.save(plot);
            adminChangedPlotBossBarDisplayed.success(player);
            new PlotAdminPanelInventory(player, plot);
        }, Map.of("%enabled%", StringIconUtil.getReturnedEmojiFromBoolean(plot.getSpecials().isPlotBossBarVisible())));
        setItem(item("Zablokuj wszystkie permisje na działce", List.of("Zablokowane: %enabled%")), 16, HeadsType.SPECIALITY.getHead(), event -> {
            event.setCancelled(true);
            plot.getSpecials().setAreAllRegionPermissionsDisabled(!plot.getSpecials().isAreAllRegionPermissionsDisabled());
            database.save(plot);
            adminChangedPlotAllPermissions.success(player);
            new PlotAdminPanelInventory(player, plot);
        }, Map.of("%enabled%", StringIconUtil.getReturnedEmojiFromBoolean(plot.getSpecials().isAreAllRegionPermissionsDisabled())));
        setItem(item("[TROLL] Zmień wygląd granicy działki na blok", LoreBuilder.create("Włączone: %enabled%").leftMouseButtonText("wylosować losowy blok").rightMouseButtonText("usunąć")), 17, new ItemBuilder(plot.getSpecials().getPlotBorderHighlightMaterial() == null ? Material.STONE_HOE : plot.getSpecials().getPlotBorderHighlightMaterial()), event -> {
            event.setCancelled(true);
            if (event.isLeftClick()) {
                Material material = getRandomBlock();
                plot.getSpecials().setPlotBorderHighlightMaterial(material);
            } else {
                plot.getSpecials().setPlotBorderHighlightMaterial(null);
            }

            database.save(plot);
            adminChangedPlotBorderDisplay.success(player);
            new PlotAdminPanelInventory(player, plot);
        }, Map.of("%enabled%", StringIconUtil.getReturnedEmojiFromBoolean(plot.getSpecials().getPlotBorderHighlightMaterial() != null)));

        fillEmptyWithBlank();
        open(player);
    }
}
