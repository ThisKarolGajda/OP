package com.github.thiskarolgajda.op.core.marriage;

import java.util.Map;

import static com.github.thiskarolgajda.op.core.marriage.MarriageConfig.*;

public enum MarriageType {
    FORMAL,
    INFORMAL,
    ARRANGED,
    TEMPORARY,
    SPIRITUAL,
    SOULMATE,
    ;

    public Map<String, Object> getMap() {
        return switch (this) {
            case FORMAL -> formal;
            case INFORMAL -> informal;
            case ARRANGED -> arranged;
            case TEMPORARY -> temporary;
            case SPIRITUAL -> spiritual;
            case SOULMATE -> soulmate;
        };
    }

    public String getName() {
        return (String) getMap().get("name");
    }
}
