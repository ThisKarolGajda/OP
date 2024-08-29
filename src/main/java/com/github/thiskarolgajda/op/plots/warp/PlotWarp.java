package com.github.thiskarolgajda.op.plots.warp;

import lombok.Data;
import me.opkarol.oplibrary.location.OpLocation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.github.thiskarolgajda.op.plots.config.PlotConfig.defaultPlotWarpName;
import static com.github.thiskarolgajda.op.utils.RandomItemCollector.random;

@Data
public class PlotWarp {
    private int visits = 0;
    private String name;
    private String description;
    private PlotWarpNameColorType color = Arrays.stream(PlotWarpNameColorType.values()).collect(random());
    private Set<PlotWarpReview> reviews = new HashSet<>();
    private OpLocation location;
    private boolean enabled;

    public PlotWarp(String plotName, OpLocation opLocation) {
        name = defaultPlotWarpName.replace("%plot%", plotName);
        description = "";
        this.location = opLocation;
        enabled = false;
    }

    public double getAverageReviewStars() {
        if (reviews == null || reviews.isEmpty()) {
            return 0;
        }

        int reviewStars = 0;
        for (PlotWarpReview review : reviews) {
            reviewStars += review.stars();
        }

        return (double) reviewStars / reviews.size();
    }

    public String getName() {
        return color == null ? name : color.getCode() + name;
    }
}
