package fluent.logging.fileLogger;

public interface FileCreationOptions {
    TargetFileOptions appendToExisting();

    TargetFileOptions create();
}
