package ru.rishat.repository;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import ru.rishat.config.WebDriverConfig;
import ru.rishat.entity.User;
import ru.rishat.login.LogIn;
import ru.rishat.service.PositionServiceImp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static ru.rishat.constants.Constants.*;

class PositionRepositoryImpTest {
    static final WebDriver driver = WebDriverConfig.getWebDriver();
    static String xpath = XPATH_FRAME_
            .concat(String.valueOf(1))
            .concat(XPATH_IMAGE);

    @BeforeAll
    public static void getDriver(User user) throws InterruptedException {
        driver.get(MARKET_STATE_PLACE);
        LogIn.logIn(driver, user);
        synchronized (driver) {
            driver.wait(3000);
        }
    }

    @Test
    public void saveImageToFile() {
        PositionServiceImp positionServiceImp = new PositionServiceImp();
        positionServiceImp.saveImageToFile(driver, xpath, "!test");
    }

    @Test
    void insertImageToCell() throws IOException {
        Sheet sheet;
        try (Workbook workbook = new XSSFWorkbook();) {
            sheet = workbook.createSheet("Robot");
            Row row1 = sheet.createRow(0);
            row1.createCell(0).setCellValue("Fist");
            InputStream inputStream = new URL("https://s3.posred.pro/media/v2/order_image/2022/12/23/1029479/order_jSP3P1f.jpeg").openStream();
            byte[] inputImageBytes = IOUtils.toByteArray(inputStream);
            int inputImagePicture = workbook.addPicture(inputImageBytes, Workbook.PICTURE_TYPE_JPEG);
            XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
            XSSFClientAnchor imageAnchor = new XSSFClientAnchor();
            imageAnchor.setCol1(1);
            imageAnchor.setCol2(2);
            imageAnchor.setRow1(0);
            imageAnchor.setRow2(1);
            drawing.createPicture(imageAnchor, inputImagePicture);

            try (FileOutputStream saveExcel = new FileOutputStream("src/main/resources/test.xlsx")) {
                workbook.write(saveExcel);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}