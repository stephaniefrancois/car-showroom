package common;

import java.math.BigDecimal;

public final class NumberExtensions {
    public final static int tryParseNumber(String text, int defaultValue) {
        try {
            return Integer.valueOf(text);
        } catch (NumberFormatException ex) {
            System.err.println(String.format("Failed to parse '%s' to INTEGER!", text));
            // TODO: log conversion failure
            return defaultValue;
        }
    }

    public final static BigDecimal tryParseNumber(String text, BigDecimal defaultValue) {
        try {
            return new BigDecimal(text);
        } catch (NumberFormatException ex) {
            // TODO: log conversion failure
            System.err.println(String.format("Failed to parse '%s' to DECIMAL!", text));
            return defaultValue;
        }
    }
}
