package fluent.logging.fileLogger;

public interface TargetFileOptions {
    LogFileExtension file(String logFileName);

    RollOverFileName files(int numberOfFilesToUse);
}
