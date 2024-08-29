package com.github.thiskarolgajda.op.plots.warp;

import lombok.Getter;
import org.bukkit.Material;

import java.io.Serializable;
import java.util.Map;

import static com.github.thiskarolgajda.op.plots.warp.PlotWarpNameColorConfig.*;

@Getter
public enum PlotWarpNameColorType implements Serializable {
    BLACK("&0", Material.BLACK_WOOL),
    DARK_BLUE("&1", Material.BLUE_WOOL),
    DARK_GREEN("&2", Material.GREEN_WOOL),
    DARK_AQUA("&3", Material.LIGHT_BLUE_WOOL),
    DARK_RED("&4", Material.RED_WOOL),
    DARK_PURPLE("&5", Material.PURPLE_WOOL),
    GOLD("&6", Material.YELLOW_WOOL),
    GRAY("&7", Material.LIGHT_GRAY_WOOL),
    DARK_GRAY("&8", Material.GRAY_WOOL),
    BLUE("&9", Material.BLUE_WOOL),
    GREEN("&a", Material.GREEN_WOOL),
    AQUA("&b", Material.BLUE_WOOL),
    RED("&c", Material.RED_WOOL),
    LIGHT_PURPLE("&d", Material.PURPLE_WOOL),
    YELLOW("&e", Material.YELLOW_WOOL),
    WHITE("&f", Material.WHITE_WOOL),
    CLASSIC_BLUE("#<34568B>", Material.BLUE_WOOL),
    LIVING_CORAL("#<FF6F61>", Material.ORANGE_WOOL),
    ULTRA_VIOLET("#<6B5B95>", Material.BLUE_WOOL),
    GREENERY("#<88B04B>", Material.GREEN_WOOL)
    ;

    private final String code;
    private final Material material;

    PlotWarpNameColorType(String code, Material material) {
        this.code = code;
        this.material = material;
    }

    public Map<String, Object> getMap() {
        return switch (this) {
            case BLACK -> black;
            case DARK_BLUE -> darkBlue;
            case DARK_GREEN -> darkGreen;
            case DARK_AQUA -> darkAqua;
            case DARK_RED -> darkRed;
            case DARK_PURPLE -> darkPurple;
            case GOLD -> gold;
            case GRAY -> gray;
            case DARK_GRAY -> darkGray;
            case BLUE -> blue;
            case GREEN -> green;
            case AQUA -> aqua;
            case RED -> red;
            case LIGHT_PURPLE -> lightPurple;
            case YELLOW -> yellow;
            case WHITE -> white;
            case CLASSIC_BLUE -> classicBlue;
            case LIVING_CORAL -> livingCoral;
            case ULTRA_VIOLET -> ultraViolet;
            case GREENERY -> greenery;
        };
    }

    public String getName() {
        return (String) getMap().get("name");
    }
}
