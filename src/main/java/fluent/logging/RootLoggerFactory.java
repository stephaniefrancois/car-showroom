package fluent.logging;

import fluent.logging.fileLogger.LogFileConfigurationFactory;
import fluent.logging.fileLogger.LogFileDestination;

import java.util.Objects;
import java.util.function.Function;
import java.util.logging.*;

public final class RootLoggerFactory
        implements NameRootLogger,
        LogDestination,
        UseFormatter,
        LogLevelSetter,
        LoggerBuilder,
        ChildLoggerFactory {

    private Handler handler;
    private Formatter formatter;
    private Logger rootLogger;
    private String rootLoggerName;
    private Level logLevel;

    @Override
    public LogDestination withLoggerName(String rootLoggerName) {
        Objects.requireNonNull(rootLoggerName);
        this.rootLoggerName = rootLoggerName;
        return this;
    }

    @Override
    public UseFormatter logToFile(
            Function<LogFileDestination,
                    FileHandler> fileHandlerFactory) {
        LogFileConfigurationFactory factory = new LogFileConfigurationFactory();
        FileHandler handler = fileHandlerFactory.apply(factory);
        Objects.requireNonNull(handler);
        this.handler = handler;
        return this;
    }

    @Override
    public UseFormatter logToConsole() {
        handler = new ConsoleHandler();
        return this;
    }

    @Override
    public LogLevelSetter usingSimpleFormatter() {
        formatter = new SimpleFormatter();
        return this;
    }

    @Override
    public ChildLoggerFactory build() {
        handler.setFormatter(formatter);
        handler.setLevel(logLevel);
        Logger logger = Logger.getLogger(rootLoggerName);
        logger.addHandler(handler);
        logger.setLevel(logLevel);
        rootLogger = logger;
        return this;
    }

    @Override
    public <TCaller> Logger getLogger(Class<TCaller> callerClass) {
        return Logger.getLogger(String.format("%s.%s",
                rootLoggerName,
                callerClass.getTypeName()));
    }

    @Override
    public LoggerBuilder andCaptureAllLogs() {
        logLevel = Level.ALL;
        return this;
    }

    @Override
    public LoggerBuilder andCaptureInfoLogs() {
        logLevel = Level.INFO;
        return this;
    }

    @Override
    public LoggerBuilder andCaptureWarningsLogs() {
        logLevel = Level.WARNING;
        return this;
    }

    @Override
    public LoggerBuilder andCaptureErrorsLogs() {
        logLevel = Level.SEVERE;
        return this;
    }
}
