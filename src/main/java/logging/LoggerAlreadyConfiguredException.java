package logging;

public final class LoggerAlreadyConfiguredException extends Exception {
    public LoggerAlreadyConfiguredException() {
        super(buildMessage());
    }

    private static String buildMessage() {
        return "Logging is already configured and please note that Logging service " +
                "can be configured only ONCE per application!";
    }
}
