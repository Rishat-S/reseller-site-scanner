package ru.rishat.controller;

import org.openqa.selenium.WebDriver;
import ru.rishat.service.PositionServiceImp;

import java.util.logging.Logger;

public class PositionController {
    private static final Logger logger = Logger.getLogger(PositionController.class.getName());
    PositionServiceImp positionServiceImp = new PositionServiceImp();

    public void scanAllPositions(WebDriver driver) throws InterruptedException {
        positionServiceImp.scanAllPositions(driver);
    }

    public void saveAllPositionsToFile() {
        positionServiceImp.saveAllPositionsToFile();
    }
}
