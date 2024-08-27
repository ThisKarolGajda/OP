package com.github.thiskarolgajda.op.utils.playerrequest;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PlayerNameMatcher {

    public static @NotNull List<OfflinePlayer> getSortedPlayersByName(String name, String excluded) {
        List<OfflinePlayer> players = new ArrayList<>(Arrays.stream(Bukkit.getServer().getOfflinePlayers())
                .filter(player -> !Objects.equals(player.getName(), excluded))
                .distinct()
                .filter(offlinePlayer -> offlinePlayer.getName() != null)
                .sorted(Comparator.comparingDouble(player -> similarity(name, player.getName())))
                .toList());

        Collections.reverse(players);
        return players;
    }

    public static double similarity(@NotNull String longer, @NotNull String shorter) {
        if (longer.length() < shorter.length()) {
            String temp = longer;
            longer = shorter;
            shorter = temp;
        }
        int longerLength = longer.length();
        if (longerLength == 0) {
            return 1.0;
        }

        return (longerLength - editDistance(longer, shorter)) / (double) longerLength;

    }

    public static int editDistance(String s1, String s2) {
        s1 = s1.toLowerCase();
        s2 = s2.toLowerCase();

        int[] costs = new int[s2.length() + 1];
        for (int i = 0; i <= s1.length(); i++) {
            int lastValue = i;
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0)
                    costs[j] = j;
                else {
                    if (j > 0) {
                        int newValue = costs[j - 1];
                        if (s1.charAt(i - 1) != s2.charAt(j - 1))
                            newValue = Math.min(Math.min(newValue, lastValue),
                                    costs[j]) + 1;
                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
                costs[s2.length()] = lastValue;
        }
        return costs[s2.length()];
    }
}

