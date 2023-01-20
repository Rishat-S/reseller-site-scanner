package ru.rishat.controller;

import org.openqa.selenium.WebDriver;
import ru.rishat.service.PositionServiceImp;

public class PositionController {
    private final PositionServiceImp positionServiceImp = new PositionServiceImp();

    public void scanAllPositions(WebDriver driver) throws InterruptedException {
        positionServiceImp.scanAllPositions(driver);
    }

    public void saveAllPositionsToFile() {
        positionServiceImp.saveAllPositionsToFile();
    }
}
