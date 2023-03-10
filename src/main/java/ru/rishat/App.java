package ru.rishat;

import org.openqa.selenium.WebDriver;
import ru.rishat.config.GlobalVarConfig;
import ru.rishat.config.WebDriverConfig;
import ru.rishat.controller.PositionController;
import ru.rishat.controller.UserController;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.rishat.constants.Constants.*;

/**
 *
 */
public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());
    static PositionController positionController = new PositionController();
    static UserController userController = new UserController();

    public static void main(String[] args) throws InterruptedException, FileNotFoundException {

        Instant start = Instant.now();
        final WebDriver driver = WebDriverConfig.getWebDriver();
        logger.log(Level.INFO, "Start program, try scan purchase - " + PURCHASE_ID);
        try {
            for (int i = 0; i < STATUS.length; i++) {
                driver.get(MARKET_STATE_PLACE + STATUS[i] + LINE_OF_SELLER);
                if (i == 0) {
                    GlobalVarConfig.initializingVariables(new File(AUTH_CSV));
                    userController.logInUser(driver, userController.getUser());
                }

                synchronized (driver) {
                    driver.wait(3000);
                }

                positionController.scanAllPositions(driver);

            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

        try {
            positionController.saveAllPositionsToFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Instant end = Instant.now();
        logger.info("End program");
        long totalTime = Duration.between(start, end).toMinutes();
        logger.log(Level.INFO, "Total time: " + totalTime + " min");
    }
}
