package com.janboerman.jargeneratorinfobox;

import java.util.OptionalInt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JarGeneratorLingo {

    private static final String MESSAGE_REGEX = "You have (?<amount>\\d+) charges? left in your jar generator.";
    private static final Pattern MESSAGE_PATTERN = Pattern.compile(MESSAGE_REGEX);

    private JarGeneratorLingo() {
    }

    public static OptionalInt interpret(String text) {
        Matcher matcher = MESSAGE_PATTERN.matcher(text);
        if (matcher.matches()) {
            String amount = matcher.group("amount");
            try {
                return OptionalInt.of(Integer.parseInt(amount));
            } catch (NumberFormatException e) {
                return OptionalInt.empty();
            }
        } else {
            return OptionalInt.empty();
        }
    }
}
