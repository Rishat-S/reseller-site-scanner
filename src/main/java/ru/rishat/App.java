package ru.rishat;

import java.time.Duration;
import java.time.Instant;
import java.util.logging.Logger;

/**
 *
 */
public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        Instant start = Instant.now();
        logger.info("Start");

        //TODO:

        Instant end = Instant.now();
        logger.info("End");
        long durationTime = Duration.between(start, end).toMillis();
        logger.info("Duration time: " + durationTime + "millis");


    }
}
