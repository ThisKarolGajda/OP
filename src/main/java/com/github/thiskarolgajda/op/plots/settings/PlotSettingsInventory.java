package com.github.thiskarolgajda.op.plots.settings;

import com.github.thiskarolgajda.op.permission.PermissionType;
import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import com.github.thiskarolgajda.op.plots.border.BorderChangeInventory;
import com.github.thiskarolgajda.op.plots.border.PlotBorder;
import com.github.thiskarolgajda.op.plots.inventories.PlotMainInventory;
import com.github.thiskarolgajda.op.plots.listener.PlotListener;
import com.github.thiskarolgajda.op.plots.settings.animals.PlotSettingAnimalInventory;
import com.github.thiskarolgajda.op.plots.settings.daytime.PlotDayChooseInventory;
import com.github.thiskarolgajda.op.plots.settings.music.PlotSettingMusicInventory;
import com.github.thiskarolgajda.op.plots.settings.weather.PlotWeatherChooseInventory;
import com.github.thiskarolgajda.op.region.inventory.RegionPlayerRoleSelectInventory;
import com.github.thiskarolgajda.op.region.inventory.RegionRuleSelectInventory;
import com.github.thiskarolgajda.op.utils.HeadsType;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.injection.formatter.LoreBuilder;
import me.opkarol.oplibrary.injection.messages.StringMessage;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import me.opkarol.oplibrary.misc.StringIconUtil;
import me.opkarol.oplibrary.tools.Heads;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

import static com.github.thiskarolgajda.op.OP.youCantUseThat;

public class PlotSettingsInventory extends ChestInventory {

    public static StringMessage disabledPvp = new StringMessage("Wyłaczono PVP na działce.");
    public static StringMessage enabledPvp = new StringMessage("Włączono PVP na działce.");
    public static StringMessage cantEnablePvpBecauseOfFlyingEnabled = new StringMessage("Nie można włączyć PVP, ponieważ na działce jest włączone latanie!");
    public static StringMessage cantEnablePvpBecauseOfWarpEnabled = new StringMessage("Nie można włączyć PVP, ponieważ działka ma aktywny warp!");
    public static StringMessage cantEnablePvpBecauseOfPlayersOnPlot = new StringMessage("Nie można włączyć PVP, ponieważ na działce są gracze!");
    public static StringMessage disabledFlying = new StringMessage("Wyłaczono latanie na działce.");
    public static StringMessage enabledFlying = new StringMessage("Właczono latanie na działce.");

    public PlotSettingsInventory(Player player, Plot plot) {
        super(3, "Ustawienia działki");
        setItemHome(22, player, () -> new PlotMainInventory(plot, player));

        setItem(item("Zmień zasady graczy na regionie", List.of("Zaawansowana opcja! Jeśli nie wiesz co robisz, lepiej tutaj nie wchodź. Możesz przypadkowo włączyć opcję niszczenia/stawiania bloków przez gości!")), 9, HeadsType.VILLAGE.getHead(), event -> {
            event.setCancelled(true);
            new RegionPlayerRoleSelectInventory(player, plot.getRegion().getParentRegion(), () -> new PlotSettingsInventory(player, plot));
        });

        setItem(item("Zmień zasady regionu", List.of("Zaawansowana opcja! Jeśli nie wiesz co robisz, lepiej tutaj nie wchodź. Możesz przypadkowo włączyć opcję zniszczenia działki przez innych graczy (TNT).")), 17, HeadsType.VILLAGE.getHead(), event -> {
            event.setCancelled(true);
            new RegionRuleSelectInventory(player, plot.getRegion().getParentRegion(), () -> new PlotSettingsInventory(player, plot));
        });

        setItem(item("Zmień pogodę", List.of("Wybrana pogoda: %setting%", "Uwaga: to jest efekt czysto kosmetyczny, nie ma on wpływu na respawn zwierząt czy inne mechaniki, które podlegają serwerowi!")), 10, Heads.get("c465c121958c0522e3dccb3d14d68612d6317cd380b0e646b61b7420b904af02"), event -> {
            event.setCancelled(true);
            new PlotWeatherChooseInventory(player, plot);
        }, Map.of("%setting%", plot.getSettings().getSelectedWeatherType().getName()));

        setItem(item("Zmień porę dnia", List.of("Wybrana pora dnia: %setting%", "Uwaga: to jest efekt czysto kosmetyczny, nie ma on wpływu na respawn zwierząt czy inne mechaniki, które podlegają serwerowi!")), 11, Heads.get("71a11a03d6bc75b144be85848556a15f358ee6a65e0466f6c8b706e7f9bcf14"), event -> {
            event.setCancelled(true);
            new PlotDayChooseInventory(player, plot);
        }, Map.of("%setting%", plot.getSettings().getSelectedDayType().getName()));

        setItem(item("Zmień naturalny respawn zwierząt", List.of("Wyłączony respawn: %amount% zwierząt")), 12, new ItemBuilder(Material.COW_SPAWN_EGG), event -> {
            event.setCancelled(true);
            new PlotSettingAnimalInventory(player, plot);
        }, Map.of("%amount%", String.valueOf(plot.getSettings().getAnimalSpawn().getDisabledSpawns().size())));

        PlotBorder border = plot.getBorder();
        setItem(item("Zmień granicę", LoreBuilder.create("Pokazywanie granicy na działce: %showing_border%", "Kolor granicy właściciela: #<" + border.getOwnerColorHex() + ">&m---------", "Kolor granicy członka: #<" + border.getMemberColorHex() + ">&m---------", "Kolor granicy gościa: #<" + border.getNormalColorHex() + ">&m---------", "Kolor granicy ignorowanego: #<" + border.getIgnoredColorHex() + ">&m---------").anyMouseButtonText("przejść do konfiguracji granicy")), 13, HeadsType.BORDER.getHead(), event -> {
            event.setCancelled(true);
            new BorderChangeInventory(player, plot);
        }, Map.of("%showing_border%", StringIconUtil.getReturnedEmojiFromBoolean(border.isDisplayBorderInsidePlot())));

        setItem(item("Zmień muzykę", LoreBuilder.create("Aktywna muzyka: %music%").anyMouseButtonText("przejść do konfiguracji muzyki")), 14, new ItemBuilder(Material.JUKEBOX), event -> {
            event.setCancelled(true);
            new PlotSettingMusicInventory(player, plot);
        }, Map.of("%music%", plot.getSettings().getMusic().getSelectedSongName()));

        setItem(item("Zmień pvp", List.of("Włączone: %enabled%", "PVP na działce można włączyć, gdy działka nie ma aktywnego warpu, nie ma włączonego latania i w danym momencie nie ma żadnego innego gracza na działce!")), 15, Heads.get("1765341353c029e9b655f4f57931ae6adc2c7a73c657945d945a307641d3778"), event -> {
            event.setCancelled(true);
            if (plot.getSettings().isPvp()) {
                plot.getSettings().setPvp(false);
                disabledPvp.success(player);
            } else {
                if (plot.getSettings().isFlying()) {
                    cantEnablePvpBecauseOfFlyingEnabled.error(player);
                    return;
                }

                if (plot.getWarp().isEnabled()) {
                    cantEnablePvpBecauseOfWarpEnabled.error(player);
                    return;
                }

                if (isAnyoneNotOwnerAtPlot(plot)) {
                    cantEnablePvpBecauseOfPlayersOnPlot.error(player);
                    return;
                }

                plot.getSettings().setPvp(true);
                enabledPvp.success(player);
            }

            Plugin.get(PlotDatabase.class).save(plot);
            new PlotSettingsInventory(player, plot);
        }, Map.of("%enabled%", StringIconUtil.getReturnedEmojiFromBoolean(plot.getSettings().isPvp())));

        setItem(item("Zmień latanie", List.of("Włączone: %enabled%", "Tylko gracze z rangą premium mogą włączyć i korzystać z funkcji latania!")), 16, HeadsType.FLYING_PIG.getHead(), event -> {
            event.setCancelled(true);
            if (!PermissionType.FLYING_ON_PLOT_SETTING.hasPermission(player)) {
                youCantUseThat.error(player);
                return;
            }

            if (plot.getSettings().isFlying()) {
                plot.getSettings().setFlying(false);
                disabledFlying.success(player);
            } else {
                plot.getSettings().setFlying(true);
                plot.getSettings().setPvp(false);
                enabledFlying.success(player);
            }

            Plugin.get(PlotDatabase.class).save(plot);
            new PlotSettingsInventory(player, plot);
        }, Map.of("%enabled%", StringIconUtil.getReturnedEmojiFromBoolean(plot.getSettings().isFlying())));

        fillEmptyWithBlank();
        open(player);
    }

    public boolean isAnyoneNotOwnerAtPlot(@NotNull Plot plot) {
        List<Player> players = PlotListener.getPlayersInsidePlot(plot).stream().filter(player -> !plot.isOwner(player.getUniqueId())).toList();

        return !players.isEmpty();
    }
}
