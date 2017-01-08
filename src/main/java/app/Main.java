package app;

import fluent.logging.ChildLoggerFactory;
import fluent.logging.FluentLogger;
import fluent.logging.NameRootLogger;

import javax.swing.*;
import java.util.function.Function;
import java.util.logging.Logger;

public class Main {

    private static Logger log = FluentLogger.configureAndGetLogger(getLoggerConfiguration(), Main.class);

    public static void main(String[] args) {
        log.info("Car-Showroom app starting ...");

        SwingUtilities.invokeLater(() -> {
            JFrame main = new AppFrame();
            main.setSize(600, 600);
            main.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            main.setVisible(true);
        });

        log.info("Car-showroom application has been started successfully.");
    }

    private static Function<NameRootLogger, ChildLoggerFactory> getLoggerConfiguration() {
        return s -> s.withLoggerName("root")
                .logToFile(f -> f.inApplicationRootDirectory()
                        .appendToExisting()
                        .file("app")
                        .withExtension("log")
                )
                .usingSimpleFormatter()
                .andCaptureAllLogs()
                .build();
    }
}
