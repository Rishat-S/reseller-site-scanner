package ru.rishat.login;

import org.openqa.selenium.WebDriver;
import ru.rishat.scanner.PositionScanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static ru.rishat.constants.Constants.AUTH_CSV;
import static ru.rishat.constants.Constants.XPATH_BUTTON_OK;

public class LogIn {
    private static final Logger logger = Logger.getLogger(LogIn.class.getName());
    private static final PositionScanner positionScanner = new PositionScanner();

    public static void logIn(WebDriver driver) {
        try {
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(AUTH_CSV));
            List<String> collect = bufferedReader.lines().collect(Collectors.toList());
            String[] authData = collect.get(0).split(",");
            positionScanner.findElementByTagName(driver, "button").click();
            positionScanner.findElementByName(driver, "fr.email").click();
            positionScanner.findElementByName(driver, "fr.email").clear();
            positionScanner.findElementByName(driver, "fr.email").sendKeys(authData[0]);
            positionScanner.findElementByName(driver, "fr.password").click();
            positionScanner.findElementByName(driver, "fr.password").clear();
            positionScanner.findElementByName(driver, "fr.password").sendKeys(authData[1]);
            positionScanner.findElementByXpath(driver, XPATH_BUTTON_OK).click();
            logger.log(Level.INFO, "Authorization data is filled");
        } catch (IOException e) {
            logger.log(Level.INFO, "Can't read auth file");
            throw new RuntimeException(e);
        }
    }

}
