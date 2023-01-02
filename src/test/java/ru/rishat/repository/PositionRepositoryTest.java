package ru.rishat.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import ru.rishat.config.WebDriverConfig;
import ru.rishat.login.LogIn;
import ru.rishat.service.PositionService;

import static ru.rishat.constants.Constants.*;

class PositionRepositoryTest {
    static final WebDriver driver = WebDriverConfig.getWebDriver();
    static String xpath = XPATH_FRAME_
            .concat(String.valueOf(1))
            .concat(XPATH_IMAGE);

    @BeforeAll
    public static void getDriver() throws InterruptedException {
        driver.get(MARKET_STATE_PLACE);
        LogIn.logIn(driver);
        synchronized (driver) {
            driver.wait(3000);
        }
    }

    @Test
    public void saveImageToFile() throws InterruptedException {
        PositionService positionService = new PositionService();
        positionService.saveImageToFile(driver, xpath, "!test");
    }

}