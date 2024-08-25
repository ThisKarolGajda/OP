package com.github.thiskarolgajda.op.plots.warp.review;

import com.github.thiskarolgajda.op.plots.Plot;
import com.github.thiskarolgajda.op.plots.PlotDatabase;
import com.github.thiskarolgajda.op.plots.warp.PlotWarpReview;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.injection.messages.StringMessage;
import me.opkarol.oplibrary.inventories.ChestInventory;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import me.opkarol.oplibrary.listeners.ChatHandler;
import me.opkarol.oplibrary.tools.Heads;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.github.thiskarolgajda.op.plots.config.PlotConfig.plotWarpReviewExitCommand;
import static com.github.thiskarolgajda.op.plots.config.PlotConfig.plotWarpReviewMaxLength;
import static com.github.thiskarolgajda.op.plots.warp.review.SelectReviewStarsInventory.getItemBasedOnStars;

public class WarpReviewsInventory extends ChestInventory {

    public static StringMessage plotWarpReviewView = StringMessage.arg("%review% %stars% %player%", object -> {
        if (object instanceof PlotWarpReview(UUID reviewer, String review, int stars)) {
            String name = Bukkit.getOfflinePlayer(reviewer).getName();
            if (name != null) {
                return Map.of(
                        "%review%", review,
                        "%stars%", String.valueOf(stars),
                        "%player%", name
                );
            }
        }

        return Map.of();
    });
    public static StringMessage cantAddReviewToOwnPlot = new StringMessage("Nie mozna dodać recenzji do własnej działki!");
    public static StringMessage alreadyAddedReview = new StringMessage("Już dodałeś recenzję do tej działki!");
    public static StringMessage enterPlotReview = StringMessage.arg("Wprowadź recenzję działki lub wpisz %exit% aby wyjść!", object -> Map.of("%exit%", object.toString()));
    public static StringMessage exitedPlotReview = new StringMessage("Opuszczono wprowadzanie recenzji.");
    public static StringMessage tooLongPlotReview = new StringMessage("Recenzja jest za długa!");
    public static StringMessage addedPlotReview = new StringMessage("Dodano recenzję!");

    public WarpReviewsInventory(Player player, @NotNull Plot plot) {
        super(4, "Recenzje warpu");

        setGlobalItem(item("Dodaj recenzję"), 27, Heads.get("7438d08bd0405c05f47ea86d66643434fdd2e8c46ff1e6f882bb9bf891c7d3a5"), event -> {
            event.setCancelled(true);
            player.closeInventory();

            askForReview(player, plot);
        });
        setListPattern(player);

        Set<PlotWarpReview> reviews = plot.getWarp().getReviews();
        reviews.forEach(review -> setNextFree(item("Recenzja %player%a", List.of("%review%")), new ItemBuilder(getItemBasedOnStars(review.stars())), event -> {
            event.setCancelled(true);
            plotWarpReviewView.success(player, review);
            player.closeInventory();
        }, Map.of(
                "%player%", review.getReviewerName(),
                "%stars%", String.valueOf(review.stars()),
                "%review%", review.getFormattedReview()
        )));

        open(player);
    }

    private void askForReview(Player player, @NotNull Plot plot) {
        if (plot.getOwnerId().equals(player.getUniqueId())) {
            cantAddReviewToOwnPlot.error(player);
            return;
        }

        if (plot.getWarp().getReviews().stream().anyMatch(warpReview -> warpReview.reviewer().equals(player.getUniqueId()))) {
            alreadyAddedReview.error(player);
            return;
        }

        new SelectReviewStarsInventory(player, stars -> {
            if (stars < 1 || stars > 5) {
                askForReview(player, plot);
                return;
            }

            enterPlotReview.send(player, plotWarpReviewExitCommand);
            ChatHandler.add(player, message -> {
                if (message.equalsIgnoreCase(plotWarpReviewExitCommand)) {
                    exitedPlotReview.send(player);
                    return;
                }

                if (message.length() > plotWarpReviewMaxLength) {
                    tooLongPlotReview.error(player);
                    return;
                }

                PlotWarpReview review = new PlotWarpReview(player.getUniqueId(), message, stars);
                plot.getWarp().getReviews().add(review);
                Plugin.get(PlotDatabase.class).save(plot);
                addedPlotReview.success(player);
            });
        });
    }
}
