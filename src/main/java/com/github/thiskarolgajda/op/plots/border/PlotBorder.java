package com.github.thiskarolgajda.op.plots.border;

import com.github.thiskarolgajda.op.plots.Plot;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class PlotBorder {
    private boolean displayBorderInsidePlot;
    private String ownerColorHex;
    private String memberColorHex;
    private String normalColorHex;
    private String ignoredColorHex;

    public PlotBorder() {
        this.displayBorderInsidePlot = true;
        this.ownerColorHex = colorToHex(Color.BLUE);
        this.memberColorHex = colorToHex(Color.GREEN);
        this.normalColorHex = colorToHex(Color.AQUA);
        this.ignoredColorHex = colorToHex(Color.RED);
    }

    public Color getMemberColor() {
        return hexToColor(memberColorHex);
    }

    public void setMemberColor(Color memberColor) {
        this.memberColorHex = colorToHex(memberColor);
    }

    public Color getNormalColor() {
        return hexToColor(normalColorHex);
    }

    public void setNormalColor(Color normalColor) {
        this.normalColorHex = colorToHex(normalColor);
    }

    public Color getIgnoredColor() {
        return hexToColor(ignoredColorHex);
    }

    public void setIgnoredColor(Color ignoredColor) {
        this.ignoredColorHex = colorToHex(ignoredColor);
    }

    public Color getOwnerColor() {
        return hexToColor(ownerColorHex);
    }

    public void setOwnerColor(Color ownerColor) {
        this.ownerColorHex = colorToHex(ownerColor);
    }

    private Color hexToColor(@NotNull String hex) {
        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        }

        try {
            int red = Integer.parseInt(hex.substring(0, 2), 16);
            int green = Integer.parseInt(hex.substring(2, 4), 16);
            int blue = Integer.parseInt(hex.substring(4, 6), 16);

            return Color.fromRGB(red, green, blue);
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            return Color.BLACK;
        }
    }

    private @NotNull String colorToHex(@NotNull Color color) {
        return String.format("%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
    }

    public Color getParticleColor(@NotNull Plot plot, @NotNull Player player) {
        if (plot.isOwner(player.getUniqueId())) {
            return getOwnerColor();
        }

        if (plot.isMember(player.getUniqueId())) {
            return getMemberColor();
        }

        if (plot.isIgnored(player.getUniqueId())) {
            return getIgnoredColor();
        }

        return getNormalColor();
    }
}
