package fluent.logging.fileLogger;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.FileHandler;

public final class LogFileConfigurationFactory implements
        LogFileDestination,
        FileCreationOptions,
        TargetFileOptions,
        LogFileExtension,
        RollOverFileName,
        LogFileSizeLimit {

    private final String APPLICATION_ROOT = ".";
    private final String USERS_HOME_DIRECTORY = "%h";
    private final String TEMP_DIRECTORY = "%t";
    private final String ROTATING_LOG_INDEX = "%g";
    private int minAllowedSingleFileSizeInKiloBytes = 10;
    private int minAllowedFilesToRotate = 2;
    private String logFileDestination = APPLICATION_ROOT;
    private String logFileName = "logs";
    private int rollingLogFilesCount = 1;
    private int fileSizeInKilobytes = 10;
    private boolean append = false;

    @Override
    public FileCreationOptions inUserHomeDirectory() {
        logFileDestination = USERS_HOME_DIRECTORY;
        return this;
    }

    @Override
    public FileCreationOptions inTempDirectory() {
        logFileDestination = TEMP_DIRECTORY;
        return this;
    }

    @Override
    public FileCreationOptions inApplicationRootDirectory() {
        logFileDestination = APPLICATION_ROOT;
        return this;
    }

    @Override
    public TargetFileOptions appendToExisting() {
        append = true;
        return this;
    }

    @Override
    public TargetFileOptions create() {
        append = false;
        return this;
    }

    @Override
    public LogFileExtension file(String logFileName) {
        Objects.requireNonNull(logFileName);
        this.logFileName = logFileName;
        return this;
    }

    @Override
    public RollOverFileName files(int numberOfFilesToUse) {
        validateFilesCountToRotate(numberOfFilesToUse);
        rollingLogFilesCount = numberOfFilesToUse;
        return this;
    }

    @Override
    public LogFileSizeLimit usingName(String logFileName) {
        Objects.requireNonNull(logFileName);
        this.logFileName = logFileName;
        return this;
    }

    @Override
    public LogFileExtension rollOverWhenFileSizeReachesKb(int fileSizeInKilobytes) {
        validateMinFileSizeInKB(fileSizeInKilobytes);
        this.fileSizeInKilobytes = fileSizeInKilobytes;
        return this;
    }

    @Override
    public FileHandler withExtension(String extension) {
        Objects.requireNonNull(extension);
        try {
            if (rollLogsToMultipleFiles()) {
                String filePattern = buildRollingFilesPattern(extension);
                return new FileHandler(filePattern,
                        fileSizeInKilobytes,
                        rollingLogFilesCount,
                        append);
            }

            String filePattern = buildSingleFilePattern(extension);

            return new FileHandler(filePattern, append);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private boolean rollLogsToMultipleFiles() {
        return rollingLogFilesCount > 1;
    }

    private String buildRollingFilesPattern(String extension) {
        return String.format("%s/%s_%s.%s",
                logFileDestination,
                logFileName,
                ROTATING_LOG_INDEX,
                extension);
    }

    private String buildSingleFilePattern(String extension) {
        return String.format("%s/%s.%s",
                logFileDestination,
                logFileName,
                extension);
    }

    private void validateMinFileSizeInKB(int singleFileSizeInBytes) {
        if (singleFileSizeInBytes < minAllowedSingleFileSizeInKiloBytes) {
            throw new IllegalArgumentException(
                    String.format("'%d' bytes is not a valid log file size! " +
                                    "Minimum allowed file size is '%d' KB.",
                            singleFileSizeInBytes, minAllowedSingleFileSizeInKiloBytes));
        }
    }

    private void validateFilesCountToRotate(int filesCountToRotate) {
        if (filesCountToRotate < minAllowedFilesToRotate) {
            throw new IllegalArgumentException(
                    String.format("Can't rotate '%d' log usingName, minimum required usingName for rotate are '%d'!",
                            filesCountToRotate,
                            minAllowedFilesToRotate));
        }
    }

}
