package fluent.logging.fileLogger;

public interface LogFileSizeLimit {
    LogFileExtension rollOverWhenFileSizeReachesKb(int fileSizeInKilobytes);
}
