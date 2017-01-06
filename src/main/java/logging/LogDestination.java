package logging;

import java.io.IOException;
import java.util.function.Supplier;

public interface LogDestination {
    UseFormatter logToFile(
            Supplier<LogFileConfiguration> configFactory) throws IOException;

    UseFormatter logToConsole();
}
