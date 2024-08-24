package com.github.thiskarolgajda.op.plots.effects;

import me.opkarol.oplibrary.injection.config.Config;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Config(path = "plot.block.effect.type")
public class PlotEffectConfig {

    @Config
    public static Map<String, Object> regeneration = getDefaultObject("Regeneracja", 5, "337a4fa5e9c4fe40891e5ac06a0337adf02005c0039ef885ea63c3528a6a74b1");
    @Config
    public static Map<String, Object> resistance = getDefaultObject("Odporność", 5, "7af6dc67ce75b7222ac39e0536e0907249f85516efd1385bec90be33dd53de07");
    @Config
    public static Map<String, Object> strength = getDefaultObject("Siła", 5, "4157f934d33819ea7cc9152a5dc758c382d0f7f6dae94af83fd796a2dcb603ad");
    @Config
    public static Map<String, Object> speed = getDefaultObject("Prędkość", 5, "ead163c4fffb310db988560ec2b618fed0a210b480791054390ad6281605bb89");
    @Config
    public static Map<String, Object> jumpBoost = getDefaultObject("Zwiększony podskok", 5, "6d8e0b26942a8009105420ff5cbd4096017706402b0e4950805ced07e1b84c68");
    @Config
    public static Map<String, Object> haste = getDefaultObject("Przyspieszenie kopania", 5, "89daef7ef611057afd9f940444c30ffbd5d54658d3b1c67362a579765e287235");
    @Config
    public static Map<String, Object> nightVision = getDefaultObject("Widzenie w ciemności", 1, "42f57c9eed9f90b7c33b0a447568150cb7b5ec62afddf280b4f981ffd480a766");
    @Config
    public static Map<String, Object> waterBreathing = getDefaultObject("Oddychanie pod wodą", 1, "5dc9ff184ae767d3cbfd9c3aa2c7e88b10f9b591297ff676da639fb4446238c8");
    @Config
    public static Map<String, Object> fireResistance = getDefaultObject("Odporność na ogień", 5, "f01c293df4c9db3b6f63e3434201e6f454dd2ddf01092d1a247673d0300b22b8");
    @Config
    public static Map<String, Object> saturation = getDefaultObject("Nasycenie", 5, "33fa91de31e2ddda29ca419c08b34a5a2d6b4b1a1b59b236cdc14cb131dcb807");
    @Config
    public static Map<String, Object> luck = getDefaultObject("Szczęście", 5, "5c05bfae995eada34d14968ddbfb601b6b22c5975b5176e61cd223e31e3aef80");

    @Contract(value = "_, _, _ -> new", pure = true)
    private static @NotNull Map<String, Object> getDefaultObject(String name, int maxLevel, String texture) {
        return Map.of(
                "name", name,
                "maxLevel", maxLevel,
                "texture", texture,
                "pricePerLevel", 10000
        );
    }
}
