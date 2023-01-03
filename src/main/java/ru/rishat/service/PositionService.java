package ru.rishat.service;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import ru.rishat.entity.Position;
import ru.rishat.repository.PositionRepository;
import ru.rishat.scanner.PositionScanner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.rishat.constants.Constants.*;

public class PositionService {
    private static final Logger logger = Logger.getLogger(PositionService.class.getName());
    private static final PositionScanner positionScanner = new PositionScanner();
    private static final PositionRepository positionRepository = new PositionRepository();

    public String saveImageToFile(WebDriver driver, String xpathImage, String photoName) {
        PositionScanner.waitToVisibilityOfElementLocated(driver, xpathImage, 10);
        final String pathname = PATH_IMAGES_PHOTO_OF_PURCHASE;
        String[] styles;
        do {
            WebElement image = positionScanner.findElementByXpath(driver,xpathImage);
            styles = image.getAttribute("style").split("\"");
            logger.log(Level.INFO, "Style attribute length is " + styles.length);
        } while (styles.length != 3);

        try (InputStream in = new URL(styles[1]).openStream()) {
            File pathToFolder = new File(pathname);
            if (!pathToFolder.exists()) {
                if (pathToFolder.mkdir()) {
                    logger.log(Level.INFO, "The folder " + pathname + " does not exist and was created");
                }
            }
            positionRepository.saveImageToFile(in, pathname + photoName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pathname + photoName;
    }

    public void scanAllPositions(WebDriver driver) throws InterruptedException {
        int numberOfCurrentElement = 1;
        int currentFrameElementsCount = 0;
        int allFrameElementCounts;

        while (true) {
            if (numberOfCurrentElement == 1) {
                WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromViewport();
                new Actions(driver)
                        .scrollFromOrigin(scrollOrigin, 0, 500)
                        .perform();
            } else {
                new Actions(driver)
                        .scrollToElement(positionScanner.findElementByXpath(driver, BOTTOM_TO_PAGE))
                        .perform();
            }
            synchronized (driver) {
                driver.wait(5000);
            }

            final List<WebElement> frameElements = driver.findElements(By.xpath(XPATH_FRAME));
            allFrameElementCounts = frameElements.size();
            if (allFrameElementCounts == currentFrameElementsCount) {
                return;
            }
            currentFrameElementsCount = frameElements.size();
            for (int i = 0; i < 5; i++) {
                new Actions(driver)
                        .scrollToElement(driver.findElement(By.xpath(XPATH_FRAME_ + numberOfCurrentElement + XPATH_TITLE)))
                        .perform();

                synchronized (driver) {
                    driver.wait(2000);
                }

                int to = numberOfCurrentElement + 4;

                try {
                    for (; numberOfCurrentElement < to; numberOfCurrentElement++) {
                        if (numberOfCurrentElement > currentFrameElementsCount) {
                            return;
                        }
                        logger.log(Level.INFO, "Number of current element -" + numberOfCurrentElement);
                        final String xpathImage = XPATH_FRAME_ + numberOfCurrentElement + XPATH_IMAGE;
                        final String xpathTitle = XPATH_FRAME_ + numberOfCurrentElement + XPATH_TITLE;
                        final String xpathReseller = XPATH_FRAME_ + numberOfCurrentElement + XPATH_SELLER;
                        final String xpathComment = XPATH_FRAME_ + numberOfCurrentElement + XPATH_COMMENT;
                        final String xpathSum = XPATH_FRAME_ + numberOfCurrentElement + XPATH_SUM;
                        final String xpathLine = XPATH_FRAME_ + numberOfCurrentElement + XPATH_LINE;

                        Position position = new Position();
                        position.setResellerID(driver, xpathTitle);
                        position.setResellerName(positionScanner.findElementByXpath(driver, xpathReseller).getText());
                        //TODO:
                        position.setBuyersName(positionScanner.findElementByXpath(driver, xpathComment).getText());
                        //TODO:
                        position.setPercent(10);

                        final String[] splitSum = positionScanner.findElementByXpath(driver, xpathSum).getText().split("₽");
                        logger.log(Level.INFO, "purchasePrice is " + splitSum[0]);
                        position.setProductPurchasePrise(Integer.parseInt(splitSum[0]));

                        final String[] splitInter = splitSum[1].split(" ");
                        final String[] splitAmount = splitInter[2].split("шт");
                        logger.log(Level.INFO, "amount is " + splitAmount[0]);
                        position.setProductAmount(Integer.parseInt(splitAmount[0]));
                        position.setPointOfSale(positionScanner.findElementByXpath(driver, xpathLine).getText());
                        position.setPhotoName(saveImageToFile(driver, xpathImage, String.valueOf(position.getPositionID())));
                        position.setPurchaseID(PURCHASE_ID);
                        positionRepository.savePosition(position);
                    }

                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveAllPositionsToFile() {
        positionRepository.saveAllPositionsToFile();
    }
}
