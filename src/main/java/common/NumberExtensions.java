package common;

import app.RootLogger;

import java.math.BigDecimal;
import java.util.logging.Logger;

public final class NumberExtensions {
    private static final Logger log = RootLogger.get();

    public final static int tryParseNumber(String text, int defaultValue) {
        try {
            return Integer.valueOf(text);
        } catch (NumberFormatException ex) {
            log.severe(() -> String.format("Failed to parse '%s' to INTEGER!", text));
            return defaultValue;
        }
    }

    public final static BigDecimal tryParseNumber(String text, BigDecimal defaultValue) {
        try {
            return new BigDecimal(text);
        } catch (NumberFormatException ex) {
            log.severe(() -> String.format("Failed to parse '%s' to DECIMAL!", text));
            return defaultValue;
        }
    }
}
