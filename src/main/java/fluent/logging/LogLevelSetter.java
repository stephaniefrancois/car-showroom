package fluent.logging;

public interface LogLevelSetter {
    LoggerBuilder andCaptureAllLogs();

    LoggerBuilder andCaptureInfoLogs();

    LoggerBuilder andCaptureWarningsLogs();

    LoggerBuilder andCaptureErrorsLogs();

}
