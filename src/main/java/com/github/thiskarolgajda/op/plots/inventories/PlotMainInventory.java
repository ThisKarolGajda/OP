package com.github.thiskarolgajda.op.plots.inventories;

import com.github.thiskarolgajda.op.permission.PermissionType;
import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDeleter;
import com.github.thiskarolgajda.op.plots.blockcounter.PlotBlockCounterInventory;
import com.github.thiskarolgajda.op.plots.blocklimits.PlotBlockLimitsInventory;
import com.github.thiskarolgajda.op.plots.effects.PlotEffectsInventory;
import com.github.thiskarolgajda.op.plots.expiration.PlotExpirationInventory;
import com.github.thiskarolgajda.op.plots.homes.PlotHomesInventory;
import com.github.thiskarolgajda.op.plots.members.PlotPermissionsType;
import com.github.thiskarolgajda.op.plots.upgrades.PlotUpgradesInventory;
import com.github.thiskarolgajda.op.plots.warp.inventories.PlotWarpManageInventory;
import com.github.thiskarolgajda.op.utils.HeadsType;
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
//        setTeleportHome(plot, player);
        setBlockLimits(plot, player);
        setBlockCounter(plot, player);
        setWarp(plot, player);
        setAdminTools(plot, player);
        setHomes(plot, player);

        fillEmptyWithBlank();
        open(player);
    }

    private void setHomes(@NotNull Plot plot, Player player) {
        setItem(item("homes"), 30, HeadsType.HOME.getHead(), event -> {
            event.setCancelled(true);
            if (!plot.isFeatureAvailable(player, PlotPermissionsType.HOME)) {
                sendMessage("youCantUseThat", player);
                return;
            }

            new PlotHomesInventory(player, plot);
        }, Map.of("%available%", plot.getAvailableFeature(player, PlotPermissionsType.HOME)));
    }

    private void setAdminTools(@NotNull Plot plot, Player player) {
        if (PermissionType.ADMIN.hasPermission(player)) {
            setItem(item("admin_remove"), 36, new ItemBuilder(Material.BARRIER), event -> {
                event.setCancelled(true);
                player.closeInventory();
                PlotDeleter.deletePlot(plot);
            }, Map.of("%permission%", PermissionType.ADMIN.getPermission()));
            setItem(item("admin_reset_name"), 37, new ItemBuilder(Material.BARRIER), event -> {
                event.setCancelled(true);
                player.closeInventory();
//                plot.resetName(); fixme implement
            }, Map.of("%permission%", PermissionType.ADMIN.getPermission()));
        }
    }

    private void setWarp(@NotNull Plot plot, Player player) {
        setItem(item("-todo-"), 4, HeadsType.WARP.getHead(), event -> {
            event.setCancelled(true);
            if (!plot.isFeatureAvailable(player, PlotPermissionsType.WARP)) {
                sendMessage("youCantUseThat", player);
                return;
            }

            new PlotWarpManageInventory(player, plot);
        }, Map.of("%available%", plot.getAvailableFeature(player, PlotPermissionsType.WARP)));
    }

    private void setBlockCounter(@NotNull Plot plot, Player player) {
        setItem(item("-todo-"), 33, HeadsType.TROPHY.getHead(), event -> {
            event.setCancelled(true);
            if (!plot.isFeatureAvailable(player, PlotPermissionsType.BLOCK_COUNTER)) {
                sendMessage("youCantUseThat", player);
                return;
            }

            new PlotBlockCounterInventory(player, plot);
        }, Map.of("%available%", plot.getAvailableFeature(player, PlotPermissionsType.BLOCK_COUNTER)));
    }

    private void setBlockLimits(@NotNull Plot plot, Player player) {
        setItem(item("-todo-"), 32, Heads.get("aea9e885e93f964e0075a75e9ae25cdabda2ffa5d12feedfab0f889b3edbbe6b"), event -> {
            event.setCancelled(true);
            if (!plot.isFeatureAvailable(player, PlotPermissionsType.LIMIT_BLOCKS)) {
                sendMessage("youCantUseThat", player);
                return;
            }

            new PlotBlockLimitsInventory(player, plot);
        }, Map.of("%available%", plot.getAvailableFeature(player, PlotPermissionsType.LIMIT_BLOCKS)));
    }

//    private void setTeleportHome(@NotNull Plot plot, Player player) {
//        setItem("teleport_home", 32, Heads.get("c3a8e402dad1b7dad9aae6f4015932183429ce87bbbeced3119026f8296336c2"), event -> {
//            event.setCancelled(true);
//            TeleportationManager.teleport(player, plot);
//        }, Map.of("%available%", plot.getAvailableFeature(player, PlotPermissionType.HOME)));
//    }

    private void setInformation(@NotNull Plot plot) {
        setItem(item("Informacje o działce", List.of("Nazwa: %plot%", "Właściciel: %owner%", "Data stworzenia: %creation_date%", "Członkowie: %members%", "Wygasa za: %expire%")), 22, HeadsType.INFORMATION.getHead(), event -> event.setCancelled(true),
                Map.of("%plot%", plot.getName(), "%owner%", plot.getOwnerName(), "%creation_date%", plot.getFormattedCreationDate(), "%members%", plot.getMembersNames(), "%expire%", plot.getExpiration().getTimeLeft()));
    }

    private void setShowBorders(@NotNull Plot plot, Player player) {
        setItem(item("-todo-"), 29, Heads.get("7c373b60c4804e8f851ba8829bc0250f2db03d5d9e9a010cc03a2d255ad7fc15"), event -> {
            event.setCancelled(true);
//            plot.displayBorder(player, 10);
            player.closeInventory();
        }, Map.of("%available%", plot.getAvailableFeature(player, PlotPermissionsType.BORDER)));
    }

    private void setExpiration(@NotNull Plot plot, Player player) {
        setItem(item("-todo-"), 40, Heads.get("3ca1a48d2d231fa71ba5f7c40fdc10d3f2e98c5a63c017321e6781308b8a5793"), event -> {
            event.setCancelled(true);
            if (!plot.isFeatureAvailable(player, PlotPermissionsType.EXPIRE)) {
                sendMessage("youCantUseThat", player);
                return;
            }

            new PlotExpirationInventory(player, plot);
        }, Map.of("%available%", plot.getAvailableFeature(player, PlotPermissionsType.EXPIRE)));
    }

    private void setSettings(@NotNull Plot plot, Player player) {
        setItem(item("-todo-"), 14, Heads.get("204a6fc8f0cdcb1332ad98354ecba1db595253642b6b6182258bb183625d1892"), event -> {
            event.setCancelled(true);
            if (!plot.isFeatureAvailable(player, PlotPermissionsType.SETTINGS)) {
                sendMessage("youCantUseThat", player);
                return;
            }

//            new PlotSettingsInventory(player, plot);
        }, Map.of("%available%", plot.getAvailableFeature(player, PlotPermissionsType.SETTINGS)));
    }

    private void setEffects(@NotNull Plot plot, Player player) {
        setItem(item("-todo-"), 19, Heads.get("6b4a5c29d901721851d8868b9075f49c476a894098c7ef2665813c552bbc9add"), event -> {
            event.setCancelled(true);
            if (!plot.isFeatureAvailable(player, PlotPermissionsType.EFFECTS)) {
                sendMessage("youCantUseThat", player);
                return;
            }

            new PlotEffectsInventory(player, plot);
        }, Map.of("%available%", plot.getAvailableFeature(player, PlotPermissionsType.EFFECTS)));
    }

    private void setUpgrades(@NotNull Plot plot, Player player) {
        setItem(item("-todo-"), 15, Heads.get("399ad7a0431692994b6c412c7eafb9e0fc49975240b73a27d24ed797035fb894"), event -> {
            event.setCancelled(true);
            if (!plot.isFeatureAvailable(player, PlotPermissionsType.UPGRADES)) {
                sendMessage("youCantUseThat", player);
                return;
            }

            new PlotUpgradesInventory(player, plot);
        }, Map.of("%available%", plot.getAvailableFeature(player, PlotPermissionsType.UPGRADES)));
    }

    private void setIgnoredChange(@NotNull Plot plot, Player player) {
        setItem(item("-todo-"), 12, Heads.get("ddc80c01f9db8d5a6b7c5a333824b0fa768e60ff4b5341dbc1e34329bb9cdc8c"), event -> {
            event.setCancelled(true);
            if (!plot.isFeatureAvailable(player, PlotPermissionsType.IGNORED)) {
                sendMessage("youCantUseThat", player);
                return;
            }

//            new ManageIgnorePagedInventory(plot, player);
        }, Map.of("%available%", plot.getAvailableFeature(player, PlotPermissionsType.IGNORED)));
    }

    private void setMembersChange(@NotNull Plot plot, Player player) {
        setItem(item("-todo-"), 11, Heads.get("a4d6dd99928e32b34596c60d6164535fd06d56d85fb3990ef3dcbbc939cf8034"), event -> {
            event.setCancelled(true);
            if (!plot.isFeatureAvailable(player, PlotPermissionsType.MEMBERS)) {
                sendMessage("youCantUseThat", player);
                return;
            }

//            new ManageMembersPagedInventory(plot, player);
        }, Map.of("%available%", plot.getAvailableFeature(player, PlotPermissionsType.MEMBERS)));
    }

    private void setNameChange(@NotNull Plot plot, Player player) {
        setItem(item("-todo-"), 25, Heads.get("2ad8a3a3b36add5d9541a8ec970996fbdcdea9414cd754c50e48e5d34f1bf60a"), event -> {
            event.setCancelled(true);
            if (!plot.isFeatureAvailable(player, PlotPermissionsType.NAME)) {
                sendMessage("youCantUseThat", player);
                return;
            }

            new PlotChangeName(player, plot);
        }, Map.of("%available%", plot.getAvailableFeature(player, PlotPermissionsType.NAME)));
    }
}