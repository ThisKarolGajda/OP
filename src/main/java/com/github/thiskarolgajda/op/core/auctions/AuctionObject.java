package com.github.thiskarolgajda.op.core.auctions;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.opkarol.oplibrary.database.DatabaseEntity;
import me.opkarol.oplibrary.gson.ItemStackWrapper;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class AuctionObject implements DatabaseEntity<UUID> {
    private final UUID id;
    private final UUID uuid;
    private final ItemStackWrapper itemStack;
    private final LocalDateTime creationDate;
    private final LocalDateTime expirationDate;
    private final double price;
}
