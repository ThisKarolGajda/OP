package com.github.thiskarolgajda.op.plots.warp;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record PlotWarpReview(UUID reviewer, String review, int stars) {

    public @NotNull String getFormattedReview() {
        int maxCharactersPerLine = 45;
        String[] words = review.split("\\s+");
        int currentLineLength = 0;
        StringBuilder formattedReview = new StringBuilder();

        for (String word : words) {
            if (currentLineLength + word.length() > maxCharactersPerLine) {
                formattedReview.append("\n");
                currentLineLength = 0;
            }

            formattedReview.append(word).append(" ");
            currentLineLength += word.length() + 1;
        }

        return formattedReview.toString().trim();
    }

    public String getReviewerName() {
        return Bukkit.getOfflinePlayer(reviewer()).getName();
    }
}
