package logging;

import java.util.function.Supplier;

public interface NameRootLogger {
    LogDestination usingName(Supplier<String> loggerNameFactory);
}
