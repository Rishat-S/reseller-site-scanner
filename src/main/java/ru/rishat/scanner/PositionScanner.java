package ru.rishat.scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

public class PositionScanner {
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
    public void scrollDownToElementByWebElement(WebDriver driver, WebElement webElement) {
        new Actions(driver)
                .scrollToElement(webElement)
                .perform();
    }

    public static WebElement waitToVisibilityOfElementLocated(WebDriver driver, String xpath, long seconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

}
