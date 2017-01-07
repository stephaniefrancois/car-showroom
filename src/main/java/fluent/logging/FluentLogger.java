package fluent.logging;

import java.util.Objects;
import java.util.function.Function;
import java.util.logging.Logger;

public final class FluentLogger {
    private static String DEFAULT_LOGGER_NAME = "DefaultLogger";
    private static ChildLoggerFactory loggerFactory = null;
    private static ChildLoggerFactory defaultLoggerFactory = null;

    public synchronized static void configure(Function<NameRootLogger,
            ChildLoggerFactory> configurationFactory) {

        if (loggerFactory == null) {
            loggerFactory = configurationFactory.apply(new RootLoggerFactory());
        }
    }

    public synchronized static <TCaller> Logger configureAndGetLogger(Function<NameRootLogger,
            ChildLoggerFactory> configurationFactory, Class<TCaller> callerClass) {
        configure(configurationFactory);
        return getLogger(callerClass);
    }

    public synchronized static <TCaller> Logger getLogger(Class<TCaller> callerClass) {
        Objects.requireNonNull(callerClass);
        if (loggerFactory == null) {
            if (defaultLoggerFactory == null) {
                defaultLoggerFactory = configureDefaultLoggerFactory();
            }
            return defaultLoggerFactory.getLogger(callerClass);
        }

        return loggerFactory.getLogger(callerClass);
    }

    private static ChildLoggerFactory configureDefaultLoggerFactory() {
        NameRootLogger configurator = new RootLoggerFactory();
        return configurator.withLoggerName(DEFAULT_LOGGER_NAME)
                .logToConsole()
                .usingSimpleFormatter()
                .andCaptureAllLogs()
                .build();
    }
}
