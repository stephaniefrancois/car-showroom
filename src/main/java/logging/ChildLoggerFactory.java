package logging;

import java.util.logging.Logger;

public interface ChildLoggerFactory {
    <TCaller> Logger getLogger(Class<TCaller> callerClass);
}
