package com.github.thiskarolgajda.op.core.marriage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.opkarol.oplibrary.database.DatabaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class Marriage implements DatabaseEntity<UUID> {
    private final UUID id;
    private final LocalDateTime date;
    private final UUID[] users;
    private final UUID priest;
}
