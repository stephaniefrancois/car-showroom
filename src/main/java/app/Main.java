package app;

import javax.swing.*;
import java.io.IOException;
import java.util.logging.*;

public class Main {

    private static Logger log = configureRootLogger();

    public static void main(String[] args) {
        log.info("Car-Showroom app starting ...");

        SwingUtilities.invokeLater(() -> {
            JFrame main = new AppFrame();
        });

        log.info("Car-showroom application has been started successfully.");
    }

    private static Logger configureRootLogger() {
        Handler handler = new ConsoleHandler();
        Level logLevel = Level.ALL;
        try {
            handler = new FileHandler("./app.log", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Formatter formatter = new SimpleFormatter();
        Logger logger = Logger.getLogger("root");
        handler.setFormatter(formatter);
        handler.setLevel(logLevel);
        logger.addHandler(handler);
        logger.setLevel(logLevel);

        return logger;
    }
}
