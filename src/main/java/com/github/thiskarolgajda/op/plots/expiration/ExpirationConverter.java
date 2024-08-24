package com.github.thiskarolgajda.op.plots.expiration;

import me.opkarol.oplibrary.injection.messages.Message;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class ExpirationConverter {

    @Message
    private static String expired = "Wygasło";
    @Message
    private static String days = " dni ";
    @Message
    private static String hours = " godzin ";
    @Message
    private static String minutes = " minut ";
    @Message
    private static String lessThanMinute = "Mniej niż minutę";

    public static @NotNull String getTimeLeftString(long expirationTimestamp) {
        long currentTimeMillis = System.currentTimeMillis();
        long timeDifferenceMillis = expirationTimestamp - currentTimeMillis;

        if (timeDifferenceMillis <= 0) {
            return expired;
        }

        long days = TimeUnit.MILLISECONDS.toDays(timeDifferenceMillis);
        long hours = TimeUnit.MILLISECONDS.toHours(timeDifferenceMillis) % 24;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeDifferenceMillis) % 60;

        StringBuilder builder = new StringBuilder();
        if (days > 0) {
            builder.append(days).append(ExpirationConverter.days);
        }
        if (hours > 0) {
            builder.append(hours).append(ExpirationConverter.hours);
        }
        if (minutes > 0) {
            builder.append(minutes).append(ExpirationConverter.minutes);
        }

        if (builder.isEmpty()) {
            return lessThanMinute;
        }

        return builder.toString();
    }
}