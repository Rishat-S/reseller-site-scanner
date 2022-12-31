package ru.rishat.scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;

import java.util.List;
import java.util.logging.Logger;

public class PositionScanner {
    private static final Logger logger = Logger.getLogger(PositionScanner.class.getName());
    public WebElement findElementByTagName(WebDriver driver, String tagName) {
        return driver.findElement(By.tagName(tagName));
    }

    public WebElement findElementByName(WebDriver driver, String name) {
        return driver.findElement(By.name(name));
    }

    public WebElement findElementByXpath(WebDriver driver, String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    public List<WebElement> findAllElementsByXpath(WebDriver driver, String xpath) {
        return driver.findElements(By.xpath(xpath));
    }

    public void scrollDownToDeltaY(WebDriver driver, int deltaY) {
        WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromViewport();
        new Actions(driver)
                .scrollFromOrigin(scrollOrigin, 0, deltaY)
                .perform();
    }

    public void scrollDownToElementByXpath(WebDriver driver, String xpath) {
        new Actions(driver)
                .scrollToElement(driver.findElement(By.xpath(xpath)))
                .perform();
    }

    // Handlers

}
