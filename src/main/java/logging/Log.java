package logging;

import java.util.Objects;
import java.util.function.Function;
import java.util.logging.Logger;

public final class Log {
    private static String DEFAULT_LOGGER_NAME = "DefaultLogger";
    private static ChildLoggerFactory loggerFactory = null;
    private static ChildLoggerFactory defaultLoggerFactory = null;

    public synchronized static void configure(Function<NameRootLogger,
            ChildLoggerFactory> configurationFactory) throws LoggerAlreadyConfiguredException {

        if (loggerFactory != null) {
            throw new LoggerAlreadyConfiguredException();
        }

        loggerFactory = configurationFactory.apply(new LoggerConfigurator());
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
        NameRootLogger configurator = new LoggerConfigurator();
        return configurator.usingName(() -> DEFAULT_LOGGER_NAME)
                .logToConsole()
                .usingSimpleFormatter()
                .build();
    }
}
