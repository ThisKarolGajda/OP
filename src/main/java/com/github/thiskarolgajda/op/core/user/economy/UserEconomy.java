package com.github.thiskarolgajda.op.core.user.economy;

import lombok.Getter;
import lombok.Setter;
import me.opkarol.oplibrary.database.DatabaseEntity;

import java.util.UUID;

public class UserEconomy implements DatabaseEntity<UUID> {
    private final UUID uuid;
    @Setter
    @Getter
    private double gold;

    public UserEconomy(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public UUID getId() {
        return uuid;
    }

    public boolean hasGold(double amount) {
        return getGold() >= amount;
    }

    public String getFormattedGold() {
        return MoneyTextFormatter.format(getGold());
    }

    public void remove(double amount) {
        setGold(getGold() - amount);
    }

    public void add(double amount) {
        setGold(getGold() + amount);
    }
}
