package com.github.thiskarolgajda.op.core.marriage;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public final class MarriageData {
    private UUID[] users;
    private MarriageType type;
}
