package ru.rishat.login;

import org.openqa.selenium.WebDriver;
import ru.rishat.entity.User;
import ru.rishat.scanner.PositionScanner;

import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.rishat.constants.Constants.XPATH_BUTTON_OK;

public class LogIn {
    private static final Logger logger = Logger.getLogger(LogIn.class.getName());
    private static final PositionScanner positionScanner = new PositionScanner();

    public static void logIn(WebDriver driver, User user) {
        positionScanner.findElementByTagName(driver, "button").click();
        positionScanner.findElementByName(driver, "fr.email").click();
        positionScanner.findElementByName(driver, "fr.email").clear();
        positionScanner.findElementByName(driver, "fr.email").sendKeys(user.getName());
        positionScanner.findElementByName(driver, "fr.password").click();
        positionScanner.findElementByName(driver, "fr.password").clear();
        positionScanner.findElementByName(driver, "fr.password").sendKeys(user.getPassword());
        positionScanner.findElementByXpath(driver, XPATH_BUTTON_OK).click();
        logger.log(Level.INFO, "Authorization data is filled");
    }

}
