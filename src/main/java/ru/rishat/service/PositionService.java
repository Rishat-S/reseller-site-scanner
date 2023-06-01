package ru.rishat.service;

import org.openqa.selenium.WebDriver;

public interface PositionService {
    void scanAllPositions(WebDriver driver) throws InterruptedException;

    String getPathOfImage(WebDriver driver, String xpathImage);

    void saveAllPositionsToFile();
}
