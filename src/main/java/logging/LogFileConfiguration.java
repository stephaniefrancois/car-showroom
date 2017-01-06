package logging;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.FileHandler;
import java.util.logging.Handler;

public final class LogFileConfiguration {
    private final String pattern;
    private int minAllowedSingleFileSizeInBytes = 1024;
    private int minAllowedFilesToRotate = 2;
    private int singleFileSizeInBytes = 0;
    private int filesCountToRotate = 0;

    public LogFileConfiguration(String pattern) {
        Objects.requireNonNull(pattern);
        this.pattern = pattern;
    }

    public LogFileConfiguration(String pattern,
                                int singleFileSizeInBytes,
                                int filesCountToRotate) {
        Objects.requireNonNull(pattern);
        validateMinFileSizeInBytes(singleFileSizeInBytes);
        validateFilesCountToRotate(filesCountToRotate);

        this.pattern = pattern;
        this.singleFileSizeInBytes = singleFileSizeInBytes;
        this.filesCountToRotate = filesCountToRotate;
    }

    private void validateMinFileSizeInBytes(int singleFileSizeInBytes) {
        if (singleFileSizeInBytes < minAllowedSingleFileSizeInBytes) {
            throw new IllegalArgumentException(
                    String.format("'%d' bytes is not a valid log file size! " +
                                    "Minimum allowed file size is '%d' bytes.",
                            singleFileSizeInBytes, minAllowedSingleFileSizeInBytes));
        }
    }

    private void validateFilesCountToRotate(int filesCountToRotate) {
        if (filesCountToRotate < minAllowedFilesToRotate) {
            throw new IllegalArgumentException(
                    String.format("Can't rotate '%d' log files, minimum required files for rotate are '%d'!",
                            filesCountToRotate,
                            minAllowedFilesToRotate));
        }
    }

    public final Handler buildHandler() throws IOException {
        if (singleFileSizeInBytes > 0 && filesCountToRotate > 0) {
            return new FileHandler(pattern, singleFileSizeInBytes, filesCountToRotate);
        }
        return new FileHandler(pattern);
    }
}
