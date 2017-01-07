package fluent.logging;

import fluent.logging.fileLogger.LogFileDestination;

import java.util.function.Function;
import java.util.logging.FileHandler;

public interface LogDestination {
    UseFormatter logToFile(
            Function<LogFileDestination, FileHandler> configFactory);

    UseFormatter logToConsole();
}
