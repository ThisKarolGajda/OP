package com.github.thiskarolgajda.op.core.user.economy;

public class MoneyTextFormatter {

    public static String format(Object object) {
        if (object instanceof Double number) {
            if (number >= 1_000_000) {
                return String.format("%.1fM", number / 1_000_000) + " \uD83D\uDCB0";
            } else if (number >= 1_000) {
                return String.format("%.1fK", number / 1_000) + " \uD83D\uDCB0";
            } else {
                return String.format("%.2f", number) + " \uD83D\uDCB0";
            }
        }

        if (object instanceof Integer number) {
            if (number >= 1_000_000) {
                return String.format("%dM", number / 1_000_000) + " \uD83D\uDCB0";
            } else if (number >= 1_000) {
                return String.format("%dK", number / 1_000) + " \uD83D\uDCB0";
            } else {
                return String.format("%d", number) + " \uD83D\uDCB0";
            }
        }

        return object.toString() + " \uD83D\uDCB0";
    }
}
