package ru.rishat;

import org.openqa.selenium.WebDriver;
import ru.rishat.config.WebDriverConfig;
import ru.rishat.controller.PositionController;
import ru.rishat.login.LogIn;

import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.rishat.constants.Constants.MARKET_STATE_PLACE;
import static ru.rishat.constants.Constants.PURCHASE_ID;

/**
 *
 */
public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());
    private static final WebDriver driver = WebDriverConfig.getWebDriver();
    static PositionController positionController = new PositionController();
    public static void main(String[] args) throws InterruptedException {

        Instant start = Instant.now();
        logger.log(Level.INFO, "Start program, try scan purchase - " + PURCHASE_ID);

        driver.get(MARKET_STATE_PLACE);
        LogIn.logIn(driver);
        synchronized (driver) {
            driver.wait(3000);
        }

        try {
            positionController.scanAllPositions(driver);
            positionController.saveAllPositionsToFile();
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
//            driver.quit();
        }

        Instant end = Instant.now();
        logger.info("End program");
        long totalTime = Duration.between(start, end).toMinutes();
        logger.log(Level.INFO, "Total time: " + totalTime + " min");
    }
}
