package com.github.thiskarolgajda.op.core.user.homes;

import lombok.Getter;
import lombok.Setter;
import me.opkarol.oplibrary.Plugin;
import me.opkarol.oplibrary.database.DatabaseEntity;
import me.opkarol.oplibrary.injection.config.Config;
import me.opkarol.oplibrary.location.OpLocation;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserHomes implements DatabaseEntity<UUID> {
    @Config
    public static int defaultHomeLimit = 1;
    @Config
    public static int costPerHomeLimitUpgrade = 10000;
    @Config
    public static String defaultHouseName = "Mój domek";
    private final UUID uuid;
    @Getter
    @Setter
    private List<UserHome> homes;
    @Setter
    @Getter
    private int homesLimit;

    public UserHomes(UUID uuid) {
        this.uuid = uuid;
        homesLimit = defaultHomeLimit;
        homes = new ArrayList<>();
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    public void addHome(Location location, String name) {
        List<UserHome> homes = getHomes();
        homes.add(new UserHome(getUnusedUUID(), new OpLocation(location), name));
        setHomes(homes);
    }

    private UUID getUnusedUUID() {
        UUID uuid;
        do {
            uuid = UUID.randomUUID();
        } while (isUUIDUsed(uuid));
        return uuid;
    }

    private boolean isUUIDUsed(UUID uuid) {
        return homes.stream().anyMatch(home -> home.getUuid().equals(uuid));
    }

    public void saveHome(UserHome home) {
        List<UserHome> homes = getHomes();
        homes.removeIf(home1 -> home1.getUuid().equals(home.getUuid()));
        homes.add(home);
        setHomes(homes);
        Plugin.get(UserHomesDatabase.class).save(this);
    }

    public UserHome getHome(String name) {
        return homes.stream()
                .filter(home -> home.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public String getHomesNames() {
        List<String> names = new ArrayList<>();
        homes.forEach(home -> names.add(home.getName()));
        return String.join(", ", names);
    }

    public void delete(UserHome home) {
        List<UserHome> homes = getHomes();
        homes.removeIf(home1 -> home1.getUuid().equals(home.getUuid()));
        setHomes(homes);
    }
}
