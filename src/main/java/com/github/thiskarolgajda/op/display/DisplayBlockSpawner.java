package com.github.thiskarolgajda.op.display;

import me.opkarol.oplibrary.listeners.Listener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.EntityType;
import org.joml.Matrix4f;

import java.util.List;
import java.util.UUID;

public class DisplayBlockSpawner extends Listener {

    public static ArmorStand spawnDisplayBlocks(Location location, List<DisplayBlock> displayBlocks) {
        // Create a parent entity to group all display blocks
        ArmorStand parentEntity = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        String name = UUID.randomUUID().toString();

        parentEntity.setCustomName(name);
        parentEntity.setCustomNameVisible(false);
        parentEntity.setInvisible(true);
        parentEntity.setInvulnerable(true);
        parentEntity.setSmall(true);
        parentEntity.setGravity(false);

        for (DisplayBlock displayBlock : displayBlocks) {
            BlockDisplay spawnedDisplay = (BlockDisplay) location.getWorld().spawnEntity(location, EntityType.BLOCK_DISPLAY);
            spawnedDisplay.setBlock(Bukkit.createBlockData(displayBlock.getStateBlock().getName()));
            applyTransformation(spawnedDisplay, displayBlock.getTransformation());
            parentEntity.addPassenger(spawnedDisplay);
        }

        return parentEntity;
    }

    private static void applyTransformation(BlockDisplay blockDisplay, float[] input) {
        Matrix4f transformationMatrix = new Matrix4f(
                input[0], input[4], input[8], input[12],
                input[1], input[5], input[9], input[13],
                input[2], input[6], input[10], input[14],
                input[3], input[7], input[11], input[15]
        );

        blockDisplay.setTransformationMatrix(transformationMatrix);
    }
}