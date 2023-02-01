package ru.rishat.service;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.rishat.entity.Position;
import ru.rishat.repository.PositionRepository;
import ru.rishat.repository.PositionRepositoryImp;
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

public class PositionServiceImp implements PositionService {
    private final Logger logger = Logger.getLogger(PositionServiceImp.class.getName());
    private final PositionScanner positionScanner = new PositionScanner();
    private final PositionRepository positionRepository = new PositionRepositoryImp();

    @Override
    public void scanAllPositions(WebDriver driver) throws InterruptedException {
        int numberOfCurrentElement = 1;
        int currentFrameElementsCount = 0;
        int allFrameElementCounts;

        while (true) {
            if (numberOfCurrentElement == 1) {
                positionScanner.scrollDownToDeltaY(driver, 500);
            } else {
                positionScanner.scrollDownToElementByXpath(driver, BOTTOM_TO_PAGE);
            }
            synchronized (driver) {
                driver.wait(5000);
            }

            final List<WebElement> frameElements = positionScanner.findAllElementsByXpath(driver, XPATH_FRAME);
            allFrameElementCounts = frameElements.size();
            if (allFrameElementCounts == currentFrameElementsCount) {
                return;
            }
            currentFrameElementsCount = frameElements.size();
            for (int i = 0; i < 5; i++) {
                if (numberOfCurrentElement > currentFrameElementsCount) {
                    System.out.println("Exit 1 " + numberOfCurrentElement + " - " + currentFrameElementsCount);
                    return;
                }

                WebElement elementByXpath = positionScanner.findElementByXpath(
                        driver,
                        XPATH_FRAME_ + numberOfCurrentElement + XPATH_TITLE
                );
                positionScanner.scrollDownToElementByWebElement(driver, elementByXpath);

                synchronized (driver) {
                    driver.wait(2000);
                }

                int to = numberOfCurrentElement + 4;

                try {
                    for (; numberOfCurrentElement < to; numberOfCurrentElement++) {
                        if (numberOfCurrentElement > currentFrameElementsCount) {
                            System.out.println("Exit 2 " + numberOfCurrentElement + " - " + currentFrameElementsCount);
                            return;
                        }
                        logger.log(Level.INFO, ">>> Number of current element - " + numberOfCurrentElement);
                        final String xpathImage = XPATH_FRAME_ + numberOfCurrentElement + XPATH_IMAGE;
                        final String xpathTitle = XPATH_FRAME_ + numberOfCurrentElement + XPATH_TITLE;
                        final String xpathReseller = XPATH_FRAME_ + numberOfCurrentElement + XPATH_SELLER;
                        final String xpathComment = XPATH_FRAME_ + numberOfCurrentElement + XPATH_COMMENT;
                        final String xpathSum = XPATH_FRAME_ + numberOfCurrentElement + XPATH_SUM;
                        final String xpathPointOfSale = XPATH_FRAME_ + numberOfCurrentElement + XPATH_LINE_OF_SELLER;

                        boolean isSpecial = false;
                        String specialCalculation = "";
                        int percent = DEFAULT_PERCENT;
                        boolean isBVPointer = false;

                        checkItIfTheFrameHasLoaded(driver, numberOfCurrentElement);

                        final String[] titleOfFrame = positionScanner.findElementByXpath(driver, xpathTitle)
                                .getText().split(DELIMITER_FOR_TITLE);
                        logger.log(Level.INFO, "-->>> Title is <<<-- " + Arrays.toString(titleOfFrame));
                        final int positionID = Integer.parseInt(titleOfFrame[1]);
                        long resellerID = Long.parseLong(titleOfFrame[0]
                                .split(DELIMITER_FOR_RESELLER_ID)[1]);

//                        TODO: configure resellers selection
                        switch ((int) resellerID) {
//                         case 20:
//                            case 1:
                            case 10: {
                                System.out.println(resellerID);
                                break;
                            }
                            default: {
                                System.out.println("!!!---The reseller doesn't match. Skipped.");
                                continue;
                            }
                        }

                        final String resellerName = positionScanner.findElementByXpath(driver, xpathReseller)
                                .getText();
                        final String[] productPurchasePriseData = positionScanner.findElementByXpath(driver, xpathSum)
                                .getText().split(DELIMITER_FOR_PRISE);
                        int productPurchasePrise = (int) Double.parseDouble(productPurchasePriseData[0]);
                        int specialProductPurchasePrise = productPurchasePrise;
                        final String[] splitAmountData = productPurchasePriseData[1].split(" ")[2].split(DELIMITER_FOR_AMOUNT);
                        int productAmount = Integer.parseInt(splitAmountData[0]);
                        final String pointOfSale = positionScanner.findElementByXpath(driver, xpathPointOfSale)
                                .getText();
                        final String photoName = saveImageToFile(driver, xpathImage, String.valueOf(titleOfFrame[1]));
                        String sizeOfProduct = "";
                        final String comment = positionScanner.findElementByXpath(driver, xpathComment).getText();
                        String[] listOfElementsInTheFrame = comment.split("\n");
                        String lastElementOfTheFrame = listOfElementsInTheFrame[listOfElementsInTheFrame.length - 1];
                        if (lastElementOfTheFrame.matches(REGEX_FOR_DEFINING_CALC_METHOD)
                                || lastElementOfTheFrame.matches(REGEX_FOR_DEFINING_CALC_METHOD_WITH_DOT)
                                || lastElementOfTheFrame.matches(REGEX_FOR_DEFINING_CALC_METHOD_STARTING_WITH_ASTERISK)
                                || lastElementOfTheFrame.matches(REGEX_FOR_DEFINING_CALC_METHOD_STARTING_WITH_ASTERISK_WITH_DOT)) {
                            isSpecial = true;
                            specialCalculation = lastElementOfTheFrame;
                            System.out.println(specialCalculation);
                            String[] specials;
                            String[] split = specialCalculation.split(SEPARATOR_FOR_SPECIAL_CALCULATION);
                            if (split.length > 1) {
                                specials = split[1].split(DELIMITER_FOR_SPECIAL_CALCULATION);
                            } else {
                                specials = specialCalculation.split(DELIMITER_FOR_SPECIAL_CALCULATION);
                            }
                            System.out.println("Specials is" + Arrays.toString(specials));
                            specialProductPurchasePrise = (int) (Double.parseDouble(specials[0]) * 10);
                            percent = Integer.parseInt(specials[1]);
                            listOfElementsInTheFrame[listOfElementsInTheFrame.length - 1] = "";
                            if (listOfElementsInTheFrame[listOfElementsInTheFrame.length - 2].contains(BV_POINTER)) {
                                isBVPointer = true;
                            }
                        }

                        if (listOfElementsInTheFrame[listOfElementsInTheFrame.length - 1].contains(BV_POINTER)) {
                            isBVPointer = true;
                        }

                        for (String elementOfFrame : listOfElementsInTheFrame) {
                            if (elementOfFrame.equals(BV_POINTER)) {
                                System.out.println("Skipped б/в line");
                                continue;
                            }
                            if (elementOfFrame.isEmpty()) {
                                System.out.println("Skipped empty line");
                                continue;
                            }

                            System.out.println(elementOfFrame);
                            Position position = new Position();
                            position.setBV(isBVPointer);
                            position.setSpecialTypeOfCalculation(isSpecial);
                            position.setSpecialGoal(specialCalculation);
                            position.setPositionID(positionID);
                            position.setResellerID(resellerID);
                            position.setResellerName(resellerName);
                            final String[] elementsData = elementOfFrame.split(",");
                            if (!elementsData[0].isEmpty() && elementsData.length != 1) {
                                sizeOfProduct = elementsData[0];
                            }
                            if (elementsData.length == 1) {
                                position.setBuyersName(elementOfFrame);
                            } else if (elementsData.length == 2) {
                                logger.log(Level.INFO, "Buyer's name is " + elementsData[1].trim());
                                position.setBuyersName(elementsData[1].trim());
                            } else if (elementsData.length == 3) {
                                logger.log(Level.INFO, "Buyer's name is " + elementsData[2].trim());
                                position.setBuyersName(elementsData[2].trim());
                                try {
                                    productAmount = Integer.parseInt(elementsData[1].trim());
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }

                            logger.log(Level.INFO, "amount is " + productAmount);
                            position.setProductAmount(productAmount);
                            logger.log(Level.INFO, "size is " + sizeOfProduct);
                            if (position.isBV()) {
                                position.setProductSize(sizeOfProduct + " б/в");
                            } else {
                                position.setProductSize(sizeOfProduct);
                            }
                            position.setPercent(percent);
                            position.setProductPurchasePrice(productPurchasePrise);
                            position.setSpecialProductPurchasePrice(specialProductPurchasePrise);
                            position.setPointOfSale(pointOfSale);
                            position.setPhotoURL(photoName);
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

    @Override
    public String saveImageToFile(WebDriver driver, String xpathImage, String photoName) {
        PositionScanner.waitToVisibilityOfElementLocated(driver, xpathImage, 30);
        final String pathname = PATH_IMAGES_PHOTO_OF_PURCHASE;
        String[] styles;
        do {
            WebElement image = positionScanner.findElementByXpath(driver, xpathImage);
            styles = image.getAttribute("style").split("\"");
            logger.log(Level.INFO, "Style attribute length is " + styles.length);
            System.out.println(Arrays.toString(styles));
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

    private void checkItIfTheFrameHasLoaded(WebDriver driver, int numberOfCurrentElement) throws InterruptedException {
        String loadedOrNot = positionScanner.findElementByXpath(driver, XPATH_FRAME_ + numberOfCurrentElement + "]").getText();
        while (loadedOrNot.equals("Загрузка...")) {
            positionScanner.scrollDownToDeltaY(driver, 100);
            synchronized (driver) {
                driver.wait(2000);
            }
            loadedOrNot = positionScanner.findElementByXpath(driver, XPATH_FRAME_ + numberOfCurrentElement + "]").getText();
            synchronized (driver) {
                driver.wait(2000);
            }
        }
    }

    @Override
    public void saveAllPositionsToFile() {
        positionRepository.saveAllPositionsToFile();
    }
}
