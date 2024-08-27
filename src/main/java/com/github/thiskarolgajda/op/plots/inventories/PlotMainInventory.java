package com.github.thiskarolgajda.op.plots.inventories;

import com.github.thiskarolgajda.op.permission.PermissionType;
import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotHighlighter;
import com.github.thiskarolgajda.op.plots.blockcounter.PlotBlockCounterInventory;
import com.github.thiskarolgajda.op.plots.blocklimits.PlotBlockLimitsInventory;
import com.github.thiskarolgajda.op.plots.effects.PlotEffectsInventory;
import com.github.thiskarolgajda.op.plots.expiration.PlotExpirationInventory;
import com.github.thiskarolgajda.op.plots.homes.PlotHomesInventory;
import com.github.thiskarolgajda.op.plots.members.ManageMembersInventory;
import com.github.thiskarolgajda.op.plots.members.PlotPermissionsType;
import com.github.thiskarolgajda.op.plots.settings.PlotSettingsInventory;
import com.github.thiskarolgajda.op.plots.upgrades.PlotUpgradesInventory;
import com.github.thiskarolgajda.op.plots.warp.inventories.PlotWarpManageInventory;
import com.github.thiskarolgajda.op.utils.HeadsType;
import me.opkarol.oplibrary.injection.formatter.LoreBuilder;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import me.opkarol.oplibrary.tools.Heads;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

import static me.opkarol.oplibrary.translations.Messages.sendMessage;

public class PlotMainInventory extends ChestInventory {
    public PlotMainInventory(@NotNull Plot plot, Player player) {
        super(5, plot.getName());

        setNameChange(plot, player);
        setMembersChange(plot, player);
        setIgnoredChange(plot, player);
        setUpgrades(plot, player);
        setEffects(plot, player);
        setSettings(plot, player);
        setExpiration(plot, player);
        setShowBorders(plot, player);
        setInformation(plot);
        setBlockLimits(plot, player);
        setBlockCounter(plot, player);
        setWarp(plot, player);
        setAdminTools(plot, player);
        setHomes(plot, player);

        fillEmptyWithBlank();
        open(player);
    }

    private void setHomes(@NotNull Plot plot, Player player) {
        setItem(item("Domy działki", getLore("Teleportuj się i zarządzaj swoimi domami!", "wyświetlić domy")), 30, HeadsType.HOME.getHead(), event -> {
            event.setCancelled(true);
            if (!plot.isFeatureAvailable(player, PlotPermissionsType.TELEPORT_HOME)) {
                sendMessage("youCantUseThat", player);
                return;
            }

            new PlotHomesInventory(player, plot);
        }, Map.of("%available%", plot.getAvailableFeature(player, PlotPermissionsType.TELEPORT_HOME)));
    }

    private void setAdminTools(@NotNull Plot plot, Player player) {
        if (PermissionType.ADMIN.hasPermission(player)) {
            setItem(item("Panel administratora", List.of("Dostępny tylko dla administratorów z permisją: %permission%", "Pozwala na rozszerzone zarządzanie działką oraz specjalne dodatki!")), 36, new ItemBuilder(Material.BARRIER), event -> {
                event.setCancelled(true);
                new PlotAdminPanelInventory(player, plot);
            }, Map.of("%permission%", PermissionType.ADMIN.getPermission()));
        }
    }

    private void setWarp(@NotNull Plot plot, Player player) {
        setItem(item("Warp", getLore("Warpy pozwalają na dzielenie się z innymi graczami swoimi budowlami i innymi rzeczami na swojej działce.", "zarządzać warpem")), 4, HeadsType.WARP.getHead(), event -> {
            event.setCancelled(true);
            if (!plot.isFeatureAvailable(player, PlotPermissionsType.USE_WARP)) {
                sendMessage("youCantUseThat", player);
                return;
            }

            new PlotWarpManageInventory(player, plot);
        }, Map.of("%available%", plot.getAvailableFeature(player, PlotPermissionsType.USE_WARP)));
    }

    private void setBlockCounter(@NotNull Plot plot, Player player) {
        setItem(item("Statystyki działki", getLore("Porównaj swoją działkę z działkami innych graczy!", "wyświetlić statystyki działki")), 33, HeadsType.TROPHY.getHead(), event -> {
            event.setCancelled(true);
            if (!plot.isFeatureAvailable(player, PlotPermissionsType.BLOCK_COUNTER)) {
                sendMessage("youCantUseThat", player);
                return;
            }

            new PlotBlockCounterInventory(player, plot);
        }, Map.of("%available%", plot.getAvailableFeature(player, PlotPermissionsType.BLOCK_COUNTER)));
    }

    private void setBlockLimits(@NotNull Plot plot, Player player) {
        setItem(item("Limity bloków", getLore("Zwiększ limity bloków (redstone) na swojej działce!", "przejrzeć limity bloków")), 32, Heads.get("aea9e885e93f964e0075a75e9ae25cdabda2ffa5d12feedfab0f889b3edbbe6b"), event -> {
            event.setCancelled(true);
            if (!plot.isFeatureAvailable(player, PlotPermissionsType.LIMIT_BLOCKS)) {
                sendMessage("youCantUseThat", player);
                return;
            }

            new PlotBlockLimitsInventory(player, plot);
        }, Map.of("%available%", plot.getAvailableFeature(player, PlotPermissionsType.LIMIT_BLOCKS)));
    }

    private void setInformation(@NotNull Plot plot) {
        setItem(item("Informacje o działce", List.of("Nazwa: %plot%", "Właściciel: %owner%", "Data stworzenia: %creation_date%", "Członkowie: %members%", "Wygasa za: %expire%", "Środek działki: %center_location%")), 22, HeadsType.INFORMATION.getHead(), event -> event.setCancelled(true), Map.of("%plot%", plot.getName(), "%owner%", plot.getOwnerName(), "%creation_date%", plot.getFormattedCreationDate(), "%members%", plot.getMembersNames(), "%expire%", plot.getExpiration().getTimeLeft(), "%center_location%", plot.getRegion().getParentRegion().getCenter().toFamilyStringWithoutWorld()));
    }

    private void setShowBorders(@NotNull Plot plot, Player player) {
        setItem(item("Pokaż granice działki", getLore("Wyświetl na 10 sekund granicę swojej działki!", "wyświetlić granice")), 29, Heads.get("7c373b60c4804e8f851ba8829bc0250f2db03d5d9e9a010cc03a2d255ad7fc15"), event -> {
            event.setCancelled(true);
            PlotHighlighter.highlight(plot, List.of(player), 10);
            player.closeInventory();
        }, Map.of("%available%", plot.getAvailableFeature(player, PlotPermissionsType.DISPLAY_BORDER)));
    }

    private void setExpiration(@NotNull Plot plot, Player player) {
        setItem(item("Wygaśnięcie działki", getLore("Sprawdź długość swojej działki oraz ją przedłuż. Wygaśnięcie oznacza usunięcie działki i pojawienie się jej kordów na czacie", "przedłużyć działkę")), 40, Heads.get("3ca1a48d2d231fa71ba5f7c40fdc10d3f2e98c5a63c017321e6781308b8a5793"), event -> {
            event.setCancelled(true);
            if (!plot.isFeatureAvailable(player, PlotPermissionsType.MANAGE_EXPIRE)) {
                sendMessage("youCantUseThat", player);
                return;
            }

            new PlotExpirationInventory(player, plot);
        }, Map.of("%available%", plot.getAvailableFeature(player, PlotPermissionsType.MANAGE_EXPIRE)));
    }

    private void setSettings(@NotNull Plot plot, Player player) {
        setItem(item("Ustawienia działki", getLore("Sprawdź i zmień ustawienia swojej działki, które obejmują m.in: zmianę wyświetlania granicy działki, kosmetyczną porę dnia i pogode czy PVP i latanie.", "móc zmienić ustawienia")), 14, Heads.get("204a6fc8f0cdcb1332ad98354ecba1db595253642b6b6182258bb183625d1892"), event -> {
            event.setCancelled(true);
            if (!plot.isFeatureAvailable(player, PlotPermissionsType.SET_SETTINGS)) {
                sendMessage("youCantUseThat", player);
                return;
            }

            new PlotSettingsInventory(player, plot);
        }, Map.of("%available%", plot.getAvailableFeature(player, PlotPermissionsType.SET_SETTINGS)));
    }

    private void setEffects(@NotNull Plot plot, Player player) {
        setItem(item("Efekty na działce", getLore("Dodaj efekty beacona do swojej działki!", "zobaczyć dostępne efekty")), 19, Heads.get("6b4a5c29d901721851d8868b9075f49c476a894098c7ef2665813c552bbc9add"), event -> {
            event.setCancelled(true);
            if (!plot.isFeatureAvailable(player, PlotPermissionsType.CHANGE_EFFECTS)) {
                sendMessage("youCantUseThat", player);
                return;
            }

            new PlotEffectsInventory(player, plot);
        }, Map.of("%available%", plot.getAvailableFeature(player, PlotPermissionsType.CHANGE_EFFECTS)));
    }

    private void setUpgrades(@NotNull Plot plot, Player player) {
        setItem(item("Ulepszenia działki", getLore("Zakup ulepszenia do swojej działki! Ulepszenia to m.in: zwiększenie limitu chunków, limitu graczy, limitu sklepów, szybszy rozwój zwierząt czy szybszy rozwój rosliń", "zobaczyć dostępne ulepszenia")), 15, Heads.get("399ad7a0431692994b6c412c7eafb9e0fc49975240b73a27d24ed797035fb894"), event -> {
            event.setCancelled(true);
            if (!plot.isFeatureAvailable(player, PlotPermissionsType.BUY_UPGRADES)) {
                sendMessage("youCantUseThat", player);
                return;
            }

            new PlotUpgradesInventory(player, plot);
        }, Map.of("%available%", plot.getAvailableFeature(player, PlotPermissionsType.BUY_UPGRADES)));
    }

    private void setIgnoredChange(@NotNull Plot plot, Player player) {
        setItem(item("Zarzadzanie ignorowanymi działki", getLore("Nieproszeni goście przeszkadzają na Twojej działce? Zablokuj im wejście na działkę!", "zarządzać ignorowanymi")), 12, Heads.get("ddc80c01f9db8d5a6b7c5a333824b0fa768e60ff4b5341dbc1e34329bb9cdc8c"), event -> {
            event.setCancelled(true);
            if (!plot.isFeatureAvailable(player, PlotPermissionsType.MANAGE_IGNORED)) {
                sendMessage("youCantUseThat", player);
                return;
            }

//            new ManageIgnorePagedInventory(plot, player);
        }, Map.of("%available%", plot.getAvailableFeature(player, PlotPermissionsType.MANAGE_IGNORED)));
    }

    private void setMembersChange(@NotNull Plot plot, Player player) {
        setItem(item("Zarządzanie członkami działki", getLore("Dodawaj, zarządzaj rolami i wyrzucaj członków Twojej działki!", "zarządzać członkami")), 11, Heads.get("a4d6dd99928e32b34596c60d6164535fd06d56d85fb3990ef3dcbbc939cf8034"), event -> {
            event.setCancelled(true);
            if (!plot.isFeatureAvailable(player, PlotPermissionsType.MANAGE_MEMBERS)) {
                sendMessage("youCantUseThat", player);
                return;
            }

            new ManageMembersInventory(plot, player);
        }, Map.of("%available%", plot.getAvailableFeature(player, PlotPermissionsType.MANAGE_MEMBERS)));
    }

    private void setNameChange(@NotNull Plot plot, Player player) {
        setItem(item("Zmień nazwę działki", getLore("Zmień nazwę swojej działki! Wulgarne nazwy mogą być ukarane usunięciem działki!", "zmienić nazwę")), 25, Heads.get("2ad8a3a3b36add5d9541a8ec970996fbdcdea9414cd754c50e48e5d34f1bf60a"), event -> {
            event.setCancelled(true);
            if (!plot.isFeatureAvailable(player, PlotPermissionsType.SET_NAME)) {
                sendMessage("youCantUseThat", player);
                return;
            }

            new PlotChangeName(player, plot);
        }, Map.of("%available%", plot.getAvailableFeature(player, PlotPermissionsType.SET_NAME)));
    }

    public LoreBuilder getLore(String description, String action) {
        return LoreBuilder.create("Dostępne: %available%", description).anyMouseButtonText(action);
    }
}