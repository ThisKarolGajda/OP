package com.github.thiskarolgajda.op.utils;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.Random;

import static com.github.thiskarolgajda.op.utils.RandomItemCollector.random;

public class IconGenerator {

    public static Material getRandomIcon() {
        Material[] materials = new Material[]{
                Material.GRASS_BLOCK,
                Material.STONE,
                Material.OAK_WOOD,
                Material.ENDER_CHEST,
                Material.DIAMOND_BLOCK,
                Material.NETHERITE_BLOCK,
                Material.BEACON,
                Material.DRAGON_EGG,
                Material.ELYTRA,
                Material.ENCHANTING_TABLE,
                Material.END_CRYSTAL,
                Material.GOLDEN_APPLE,
                Material.JUKEBOX,
                Material.NETHER_STAR,
                Material.SHULKER_BOX,
                Material.TOTEM_OF_UNDYING,
                Material.TRIDENT,
                Material.VILLAGER_SPAWN_EGG,
                Material.WITHER_SKELETON_SKULL,
                Material.ZOMBIE_HEAD
        };
        Random random = new Random();
        return materials[random.nextInt(materials.length)];
    }

    public static Material getRandomBlock() {
       return Arrays.stream(Material.values())
               .filter(material -> !material.isLegacy())
               .filter(Material::isItem)
               .filter(Material::isBlock)
               .collect(random());
    }
}
