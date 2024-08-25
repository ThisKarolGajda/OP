package com.github.thiskarolgajda.op.plots.warp;

import lombok.Getter;
import org.bukkit.Material;

import java.io.Serializable;
import java.util.Map;

import static com.github.thiskarolgajda.op.plots.warp.PlotWarpNameColorConfig.*;

@Getter
public enum PlotWarpNameColorType implements Serializable {
    BLACK("black", "&0", Material.BLACK_WOOL),
    DARK_BLUE("dark_blue", "&1", Material.BLUE_WOOL),
    DARK_GREEN("dark_green", "&2", Material.GREEN_WOOL),
    DARK_AQUA("dark_aqua", "&3", Material.LIGHT_BLUE_WOOL),
    DARK_RED("dark_red", "&4", Material.RED_WOOL),
    DARK_PURPLE("dark_purple", "&5", Material.PURPLE_WOOL),
    GOLD("gold", "&6", Material.YELLOW_WOOL),
    GRAY("gray", "&7", Material.LIGHT_GRAY_WOOL),
    DARK_GRAY("dark_gray", "&8", Material.GRAY_WOOL),
    BLUE("blue", "&9", Material.BLUE_WOOL),
    GREEN("green", "&a", Material.GREEN_WOOL),
    AQUA("aqua", "&b", Material.BLUE_WOOL),
    RED("red", "&c", Material.RED_WOOL),
    LIGHT_PURPLE("light_purple", "&d", Material.PURPLE_WOOL),
    YELLOW("yellow", "&e", Material.YELLOW_WOOL),
    WHITE("white", "&f", Material.WHITE_WOOL),
    ;

    private final String path;
    private final String code;
    private final Material material;

    PlotWarpNameColorType(String path, String code, Material material) {
        this.path = path;
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
        };
    }

    public String getName() {
        return (String) getMap().get("name");
    }
}
