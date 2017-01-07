package fluent.logging.fileLogger;

public interface LogFileSizeLimit {
    LogExtension rollOverWhenFileSizeReachesKb(int fileSizeInKilobytes);
}
