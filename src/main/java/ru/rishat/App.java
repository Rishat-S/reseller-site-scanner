package ru.rishat;

import org.openqa.selenium.WebDriver;
import ru.rishat.config.WebDriverConfig;
import ru.rishat.login.LogIn;

import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.rishat.constants.Constants.MARKET_STATE_PLACE;

/**
 *
 */
public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        Instant start = Instant.now();
        logger.log(Level.INFO, "Start");

        WebDriver driver = WebDriverConfig.getWebDriver();
        driver.get(MARKET_STATE_PLACE);
        LogIn.logIn(driver);

        //TODO:

        Instant end = Instant.now();
        logger.info("End");
        long totalTime = Duration.between(start, end).toSeconds();
        logger.log(Level.INFO, "Total time: " + totalTime + " s");


    }
}
