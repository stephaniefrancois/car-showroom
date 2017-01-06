package logging;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.logging.*;

public final class LoggerConfigurator
        implements NameRootLogger,
        LogDestination,
        UseFormatter,
        LoggerBuilder,
        ChildLoggerFactory {

    private Handler handler;
    private Formatter formatter;
    private Logger rootLogger;
    private String loggerName;

    @Override
    public LogDestination usingName(Supplier<String> loggerNameFactory) {
        loggerName = loggerNameFactory.get();
        Objects.requireNonNull(loggerName);
        return this;
    }

    @Override
    public UseFormatter logToFile(
            Supplier<LogFileConfiguration> configFactory) throws IOException {
        LogFileConfiguration config = configFactory.get();
        Objects.requireNonNull(config);

        handler = config.buildHandler();
        return this;
    }

    @Override
    public UseFormatter logToConsole() {
        handler = new ConsoleHandler();
        return this;
    }

    @Override
    public LoggerBuilder usingSimpleFormatter() {
        formatter = new SimpleFormatter();
        return this;
    }

    @Override
    public ChildLoggerFactory build() {
        handler.setFormatter(formatter);
        Logger logger = Logger.getLogger(loggerName);
        logger.addHandler(handler);
        rootLogger = logger;
        return this;
    }

    @Override
    public <TCaller> Logger getLogger(Class<TCaller> callerClass) {
        return Logger.getLogger(callerClass.getTypeName());
    }
}
