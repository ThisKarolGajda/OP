package com.github.thiskarolgajda.op.utils;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatter {
    final public static DateTimeFormatter STYLE_FORMATTER = DateTimeFormatter.ofPattern("EEEE, d MMMM, HH:mm", new Locale("pl", "PL"));
    final public static DateTimeFormatter DATA_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss yyyy-MM-dd");
}
