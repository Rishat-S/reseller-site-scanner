package ru.rishat.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverConfig {
    public static WebDriver getWebDriver() {
        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.setBinary("C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\brave.exe");
        return new ChromeDriver(chromeOptions);
    }
}
