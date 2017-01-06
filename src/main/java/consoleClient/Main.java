package consoleClient;

import logging.Log;
import logging.LoggerAlreadyConfiguredException;

import java.io.IOException;
import java.util.logging.Logger;

public class Main {
    // TODO: test logger with files
    // TODO: add different formatters ???
    private static Logger logger = Log.getLogger(Main.class);

    public static void main(String[] args) throws IOException, LoggerAlreadyConfiguredException {
        logger.warning("Something weird is going on with that magician!");
        System.out.println("Hello!");
        logger.info("Some great info1");
        logger.info("Some great info2");
        logger.info("Some great info3");
        logger.severe("Oh my! We have crashed!");
    }
}
