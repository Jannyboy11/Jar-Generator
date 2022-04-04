package com.janboerman.jargenerator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JarGeneratorLingo {

    private static final String FRESH_MESSAGE = "You have purchased: Jar generator x1";
    private static final String RUN_OUT_MESSAGE = "Your jar generator runs out of charges and disappears.";
    private static final String RECHARGE_MESSAGE = "Elnock recharges your magical jar generator. You now have one hundred charges.";
    private static final String CHARGES_MESSAGE_REGEX = "You have (?<amount>\\d+) charges? left in your jar generator.";
    private static final Pattern CHARGES_MESSAGE_PATTERN = Pattern.compile(CHARGES_MESSAGE_REGEX);

    private JarGeneratorLingo() {
    }

    public static Charges interpret(String text) {
        if (RUN_OUT_MESSAGE.equals(text)) {
            return Charges.empty();
        } else if (FRESH_MESSAGE.equals(text) || RECHARGE_MESSAGE.equals(text)) {
            return Charges.full();
        } else {
            Matcher matcher = CHARGES_MESSAGE_PATTERN.matcher(text);
            if (matcher.matches()) {
                String amount = matcher.group("amount");
                try {
                    return Charges.known(Integer.parseInt(amount));
                } catch (NumberFormatException e) {
                    return Charges.unknown();
                }
            } else {
                return Charges.unknown();
            }
        }
    }
}
