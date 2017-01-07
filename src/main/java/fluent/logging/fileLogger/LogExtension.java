package fluent.logging.fileLogger;

import java.util.logging.FileHandler;

public interface LogExtension {
    FileHandler withExtension(String extension);
}
