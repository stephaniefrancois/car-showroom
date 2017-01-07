package fluent.logging.fileLogger;

public interface LogFileDestination {
    FileCreationOptions inUserHomeDirectory();

    FileCreationOptions inTempDirectory();

    FileCreationOptions inApplicationRootDirectory();
}
