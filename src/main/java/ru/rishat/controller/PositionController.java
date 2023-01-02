package ru.rishat.controller;

import org.openqa.selenium.WebDriver;
import ru.rishat.service.PositionService;

import java.util.logging.Logger;

public class PositionController {
    private static final Logger logger = Logger.getLogger(PositionController.class.getName());
    PositionService positionService = new PositionService();

    public void scanAllPositions(WebDriver driver) throws InterruptedException {
        positionService.scanAllPositions(driver);
    }

    public void saveAllPositionsToFile() {
        positionService.saveAllPositionsToFile();
    }
}
