package com.github.thiskarolgajda.op.plots.homes;

import lombok.Getter;
import lombok.Setter;
import me.opkarol.oplibrary.injection.config.Config;
import me.opkarol.oplibrary.location.OpLocation;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static com.github.thiskarolgajda.op.plots.config.PlotConfig.defaultPlotHomeLimit;
import static com.github.thiskarolgajda.op.plots.config.PlotConfig.defaultPlotHouseName;

@Getter
@Config(path = "plot")
public class PlotHomes {
    private List<PlotHome> homes;
    @Setter
    private int homesLimit;

    public PlotHomes(OpLocation centerLocation) {
        setHomes(new LinkedList<>());
        homes.add(new PlotHome(getUnusedUUID(), centerLocation, defaultPlotHouseName));
        homesLimit = defaultPlotHomeLimit;
    }

    public boolean canHaveMore() {
        return homes.size() < homesLimit;
    }

    public void set(@NotNull PlotHome home) {
        if (!delete(home.getId())) {
            return;
        }

        homes.add(home);
    }

    public boolean delete(UUID uuid) {
        if (homes.size() <= 1) {
            return false;
        }

        homes.removeIf(plotHome -> plotHome.getId().equals(uuid));
        return true;
    }

    public boolean contains(UUID uuid) {
        return homes.stream().anyMatch(plotHome -> plotHome.getId().equals(uuid));
    }

    public UUID getUnusedUUID() {
        UUID uuid;

        do {
            uuid = UUID.randomUUID();
        } while (contains(uuid));

        return uuid;
    }

    public PlotHome getFirstHome() {
        return homes.stream().findFirst().orElseThrow();
    }

    private void setHomes(List<PlotHome> homes) {
        this.homes = homes;
    }

    public void saveHome(PlotHome home) {
        List<PlotHome> homes = getHomes();
        homes.removeIf(home1 -> home1.getId().equals(home.getId()));
        homes.add(home);
        setHomes(homes);
    }
}
