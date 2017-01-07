package fluent.logging.fileLogger;

import java.util.logging.FileHandler;

public interface LogFileExtension {
    FileHandler withExtension(String extension);
}
