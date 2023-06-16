package ru.rishat.service;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.rishat.entity.Position;
import ru.rishat.repository.PositionRepository;
import ru.rishat.repository.PositionRepositoryImp;
import ru.rishat.scanner.PositionScanner;

import java.util.Arrays;
import java.util.List;
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
                positionScanner.scrollDownToElementByXpath(driver, XPATH_BOTTOM_OF_THE_PAGE);
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
                    System.out.println("First exit " + numberOfCurrentElement + " - " + currentFrameElementsCount);
                    return;
                }

                WebElement titleOfCurrentPosition = positionScanner.findElementByXpath(
                        driver,
                        XPATH_FRAME_ + numberOfCurrentElement + XPATH_TITLE
                );
                positionScanner.scrollDownToElementByWebElement(driver, titleOfCurrentPosition);

                synchronized (driver) {
                    driver.wait(2000);
                }

                int to = numberOfCurrentElement + 4;

                try {
                    for (; numberOfCurrentElement < to; numberOfCurrentElement++) {
                        if (numberOfCurrentElement > currentFrameElementsCount) {
                            System.out.println("Second exit " + numberOfCurrentElement + " - " + currentFrameElementsCount);
                            return;
                        }
                        String xpathImage = XPATH_FRAME_ + numberOfCurrentElement + XPATH_IMAGE;
                        String xpathTitle = XPATH_FRAME_ + numberOfCurrentElement + XPATH_TITLE;
                        String xpathReseller = XPATH_FRAME_ + numberOfCurrentElement + XPATH_SELLER;
                        String xpathComment = XPATH_FRAME_ + numberOfCurrentElement + XPATH_COMMENT;
                        String xpathSum = XPATH_FRAME_ + numberOfCurrentElement + XPATH_SUM;
                        String xpathPointOfSale = XPATH_FRAME_ + numberOfCurrentElement + XPATH_LINE_OF_SELLER;

                        boolean isSpecial = false;
                        String specialCalculation = "";
                        int percent = DEFAULT_PERCENT;
                        boolean isBVPointer = false;

                        checkItIfTheFrameHasLoaded(driver, numberOfCurrentElement);

                        final String[] titleOfFrame = positionScanner.findElementByXpath(driver, xpathTitle)
                                .getText().split(DELIMITER_FOR_TITLE);
                        logger.info(Arrays.toString(titleOfFrame));
                        int positionID = Integer.parseInt(titleOfFrame[1]);
                        long resellerID = Long.parseLong(titleOfFrame[0]
                                .split(DELIMITER_FOR_RESELLER_ID)[1]);

                        String resellerName = positionScanner.findElementByXpath(driver, xpathReseller)
                                .getText();
                        String[] productPurchasePriseData = positionScanner.findElementByXpath(driver, xpathSum)
                                .getText().split(DELIMITER_FOR_PRISE);
                        int productPurchasePrise = (int) Double.parseDouble(productPurchasePriseData[0]);
                        int specialProductPurchasePrise = productPurchasePrise;
                        String[] splitAmountData = productPurchasePriseData[1].split(" ")[2].split(DELIMITER_FOR_AMOUNT);
                        int productAmount = Integer.parseInt(splitAmountData[0]);
                        String pointOfSale = positionScanner.findElementByXpath(driver, xpathPointOfSale)
                                .getText();
                        final String photoName = getPathOfImage(driver, xpathImage);
                        String sizeOfProduct = "";
                        final String comment = positionScanner.findElementByXpath(driver, xpathComment).getText();
                        String[] listOfElementsInTheFrame;
                        if (resellersMap.containsKey(resellerID)) {
                            listOfElementsInTheFrame = comment.split("\n");
                        } else {
                            listOfElementsInTheFrame = new String[]{comment};
                        }
                        String lastElementOfTheFrame = listOfElementsInTheFrame[listOfElementsInTheFrame.length - 1];
                        if (lastElementOfTheFrame.matches(REGEX_FOR_DEFINING_CALC_METHOD)
                                || lastElementOfTheFrame.matches(REGEX_FOR_DEFINING_CALC_METHOD_WITH_DOT)
                                || lastElementOfTheFrame.matches(REGEX_FOR_DEFINING_CALC_METHOD_WITH_COMMA)) {
                            isSpecial = true;
                            specialCalculation = lastElementOfTheFrame;
                            System.out.println(specialCalculation);
                            String[] specials = specialCalculation.split(DELIMITER_FOR_SPECIAL_CALCULATION);
                            System.out.println("Specials is" + Arrays.toString(specials));
                            specialProductPurchasePrise = (int) (Double.parseDouble(specials[0].replace(",", ".")) * 10);
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
                            if (resellerID == 2) {
                                position.setResellerName(resellersMap.get(resellerID));
                            } else {
                                position.setResellerName(resellerName);
                            }
                            String[] elementsData;
                            if (resellersMap.containsKey(resellerID)) {
                                elementsData = elementOfFrame.split(",");
                            } else {
                                elementsData = new String[]{elementOfFrame};
                            }
                            if (!elementsData[0].isEmpty() && elementsData.length != 1) {
                                sizeOfProduct = elementsData[0];
                            }
                            if (elementsData.length == 1) {
                                position.setBuyersName(elementOfFrame);
                            } else if (elementsData.length == 2) {
                                position.setBuyersName(elementsData[1].trim());
                            } else {
                                position.setBuyersName(elementsData[2].trim());
                                try {
                                    productAmount = Integer.parseInt(elementsData[1].trim());
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                                if (elementsData.length > 3) {
                                    try {
                                        String resellerIDStringFromElementsData = elementsData[3].trim();
                                        Long newResellerIDFromElementsData = Long.parseLong(resellerIDStringFromElementsData);
                                        String resellerNameByID = resellersMap.get(newResellerIDFromElementsData);
                                        System.out.println("Change! Reseller's ID is - " + newResellerIDFromElementsData);
                                        position.setResellerID(newResellerIDFromElementsData);
                                        System.out.println("Reseller's Name is - " + resellerNameByID);
                                        position.setResellerName(resellerNameByID);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            }

                            position.setProductAmount(productAmount);
                            if (position.isBV()) {
                                position.setProductSize(sizeOfProduct + " " + BV_POINTER);
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
                }
            }
        }

    }

    @Override
    public String getPathOfImage(WebDriver driver, String xpathImage) {
        PositionScanner.waitToVisibilityOfElementLocated(driver, xpathImage, 30);
        String[] styles;
        do {
            WebElement image = positionScanner.findElementByXpath(driver, xpathImage);
            styles = image.getAttribute("style").split("\"");
        } while (styles.length != 3);


        return styles[1];
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
