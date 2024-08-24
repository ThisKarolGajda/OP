package com.github.thiskarolgajda.op.plots.members;

import java.util.Set;
import java.util.UUID;

public record PlotMember(UUID uuid, Set<PlotPermissions> allowedPermissions) {
}
