package com.github.thiskarolgajda.op.plots.members;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class PlotMembers {
    private Set<PlotMember> members = new HashSet<>();

    public boolean hasPermission(UUID uuid, PlotPermissionsType permission) {
        for (PlotMember member : members) {
            if (member.uuid().equals(uuid)) {
                if (member.allowedPermissions().contains(permission)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void remove(UUID uuid) {
        members.removeIf(member -> member.uuid().equals(uuid));
    }

    public void add(UUID uuid) {
        members.add(new PlotMember(uuid, new HashSet<>(PlotPermissionsType.getDefaultPermissions())));
    }
}
