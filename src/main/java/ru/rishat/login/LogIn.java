package ru.rishat.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.rishat.constants.Constants.XPATH_BUTTON_OK;

public class LogIn {
    private static final Logger logger = Logger.getLogger(LogIn.class.getName());

    public static void logIn(WebDriver driver) {
        try {
            final BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/auth.csv"));
            final String[] authData = bufferedReader.readLine().split(",");
            driver.findElement(By.tagName("button")).click();
            driver.findElement(By.name("fr.email")).click();
            driver.findElement(By.name("fr.email")).clear();
            driver.findElement(By.name("fr.email")).sendKeys(authData[0]);
            driver.findElement(By.name("fr.password")).click();
            driver.findElement(By.name("fr.password")).clear();
            driver.findElement(By.name("fr.password")).sendKeys(authData[1]);
            driver.findElement(By.xpath(XPATH_BUTTON_OK)).click();
        } catch (IOException e) {
            logger.log(Level.INFO, "Can't read auth file");
            throw new RuntimeException(e);
        }
    }

}
