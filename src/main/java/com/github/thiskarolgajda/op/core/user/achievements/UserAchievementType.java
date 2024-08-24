package com.github.thiskarolgajda.op.core.user.achievements;

import com.github.thiskarolgajda.op.utils.HeadsType;
import lombok.Getter;
import me.opkarol.oplibrary.inventories.ItemBuilder;
import org.bukkit.Material;

import java.util.Map;

import static com.github.thiskarolgajda.op.core.user.achievements.UserAchievementConfig.*;

@Getter
public enum UserAchievementType {
    MASTER_MINER(new ItemBuilder(Material.GOLDEN_PICKAXE)),
    CREATE_PLOT(HeadsType.HOME.getHead()),
    HAVE_A_PARTY(new ItemBuilder(Material.DRAGON_HEAD)),
    TIME_TASTER(new ItemBuilder(Material.IRON_BLOCK)),
    CHRONOS_APPRENTICE(new ItemBuilder(Material.DIAMOND_BLOCK)),
    MINECRAFT_TIME_BENDER(new ItemBuilder(Material.NETHERITE_BLOCK)),
    ;

    private final ItemBuilder item;

    UserAchievementType(ItemBuilder item) {
        this.item = item;
    }

    private Map<String, Object> getMap() {
        return switch (this) {
            case MASTER_MINER -> masterMiner;
            case CREATE_PLOT -> createPlot;
            case HAVE_A_PARTY -> haveAParty;
            case TIME_TASTER -> timeTaster;
            case CHRONOS_APPRENTICE -> chronosApprentice;
            case MINECRAFT_TIME_BENDER -> minecraftTimeBender;
        };
    }

    public String getName() {
        return (String) getMap().get("name");
    }

    public String getDescription() {
        return (String) getMap().get("description");
    }

}
