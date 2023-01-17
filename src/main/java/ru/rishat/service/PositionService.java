package ru.rishat.service;

import org.openqa.selenium.WebDriver;

public interface PositionService {
    void scanAllPositions(WebDriver driver) throws InterruptedException;

    String saveImageToFile(WebDriver driver, String xpathImage, String photoName);

    void saveAllPositionsToFile();
}
