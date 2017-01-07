package fluent.logging.fileLogger;

public interface TargetFileOptions {
    LogExtension file(String logFileName);

    RollOverFileName files(int numberOfFilesToUse);
}
