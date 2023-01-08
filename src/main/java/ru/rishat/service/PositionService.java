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
import java.util.Arrays;
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
            WebElement image = positionScanner.findElementByXpath(driver, xpathImage);
            styles = image.getAttribute("style").split("\"");
            logger.log(Level.INFO, "Style attribute length is " + styles.length);
        } while (styles.length != 3);

        String uriOfPhoto = styles[1];
        try (InputStream in = new URL(uriOfPhoto).openStream()) {
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

        return uriOfPhoto;
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
                        .scrollToElement(positionScanner.findElementByXpath(driver, XPATH_FRAME_ + numberOfCurrentElement + XPATH_TITLE))
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
                        logger.log(Level.INFO, ">>> Number of current element - " + numberOfCurrentElement);
                        final String xpathImage = XPATH_FRAME_ + numberOfCurrentElement + XPATH_IMAGE;
                        final String xpathTitle = XPATH_FRAME_ + numberOfCurrentElement + XPATH_TITLE;
                        final String xpathReseller = XPATH_FRAME_ + numberOfCurrentElement + XPATH_SELLER;
                        final String xpathComment = XPATH_FRAME_ + numberOfCurrentElement + XPATH_COMMENT;
                        final String xpathSum = XPATH_FRAME_ + numberOfCurrentElement + XPATH_SUM;
                        final String xpathPointOfSale = XPATH_FRAME_ + numberOfCurrentElement + XPATH_LINE;

                        //TODO:
                        String loadedOrNot = positionScanner.findElementByXpath(driver, XPATH_FRAME_ + numberOfCurrentElement + "]").getText();
                        while (loadedOrNot.equals("Загрузка...")) {
                            WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromViewport();
                            new Actions(driver)
                                    .scrollFromOrigin(scrollOrigin, 0, 100)
                                    .perform();
                            synchronized (driver) {
                                driver.wait(2000);
                            }
                            loadedOrNot = positionScanner.findElementByXpath(driver, XPATH_FRAME_ + numberOfCurrentElement + "]").getText();
                            synchronized (driver) {
                                driver.wait(2000);
                            }
                        }

                        final String[] titleOfFrame = positionScanner.findElementByXpath(driver, xpathTitle)
                                .getText().split("/");
                        logger.log(Level.INFO, "-->>> Title is <<<-- " + Arrays.toString(titleOfFrame));
                        final int positionID = Integer.parseInt(titleOfFrame[1]);
                        final long resellerID = Long.parseLong(titleOfFrame[0].split("№")[1]);
                        final String resellerName = positionScanner.findElementByXpath(driver, xpathReseller)
                                .getText();
                        final String[] productPurchasePriseData = positionScanner.findElementByXpath(driver, xpathSum)
                                .getText().split("₽");
                        final int productPurchasePrise = Integer.parseInt(productPurchasePriseData[0]);
                        final String pointOfSale = positionScanner.findElementByXpath(driver, xpathPointOfSale)
                                .getText();
                        final String photoName = saveImageToFile(driver, xpathImage, String.valueOf(titleOfFrame[1]));
                        final String[] listOfElementsInTheFrame = positionScanner.findElementByXpath(driver, xpathComment)
                                .getText().split("\n");

                        for (String elementOfFrame : listOfElementsInTheFrame) {
                            if (elementOfFrame.equals("б/в")) {
                                continue;
                            }
                            Position position = new Position();
                            position.setPositionID(positionID);
                            position.setResellerID(resellerID);
                            position.setResellerName(resellerName);
                            //TODO:
                            final String[] elementsData = elementOfFrame.split("-");
                            if (elementsData.length < 2) {
                                logger.log(Level.INFO, "Does not match the template");
                                position.setBuyersName(elementOfFrame);
                            } else {
                                //TODO:
                                logger.log(Level.INFO, "Buyer's name is " + elementsData[1]);
                                position.setBuyersName(elementsData[1].trim());
                                logger.log(Level.INFO, "amount is " + elementsData[0]);
                                //TODO:
                            }
                            int productAmount = 0;
                            try {
                                productAmount = Integer.parseInt(elementsData[0]);
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                            position.setProductAmount(productAmount);

                            //TODO:
                            position.setPercent(10);

                            position.setProductPurchasePrise(productPurchasePrise);
                            position.setPointOfSale(pointOfSale);
                            //TODO:
                            position.setPhotoName(photoName);
                            position.setPurchaseID(PURCHASE_ID);

                            positionRepository.savePosition(position);
                        }
                    }

                } catch (NoSuchElementException e) {
                    e.printStackTrace();
                    logger.log(Level.INFO,
                            "<<<>>> Current element - " + numberOfCurrentElement + "\n <<<>>>"
                                    + positionScanner.findElementByXpath(driver,
                                    XPATH_FRAME_ + numberOfCurrentElement + "]").getText());
                }
            }
        }
    }

    public void saveAllPositionsToFile() {
        positionRepository.saveAllPositionsToFile();
    }
}
