package ru.rishat.repository;

import org.apache.poi.ss.usermodel.*;
import ru.rishat.entity.Position;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PositionRepository {
    private static final Logger logger = Logger.getLogger(PositionRepository.class.getName());
    Queue<Position> positions = new LinkedList<>();

    public void saveImageToFile(InputStream in, String photoName) {
        try {
            Files.copy(in, Paths.get(photoName + ".png"), StandardCopyOption.REPLACE_EXISTING);
            logger.log(Level.INFO, "Photo " + photoName + ".png was saved");
        } catch (IOException e) {
            logger.log(Level.INFO, "Photo " + photoName + ".png don't saved");
            throw new RuntimeException(e);
        }
    }

    public void savePosition(Position position) {
        positions.add(position);
        logger.log(Level.INFO, "-->> Position " + position.getPositionID() + " was saved to queue <<--");
    }

    public void saveAllPositionsToFile() {
        File dataFile = new File("src/main/resources/data.xlsm");
//        provideNewDataFile(dataFile);
        for (Position position : positions) {
            savePositionToFile(dataFile, position);
        }
    }

    private static void provideNewDataFile(File dataFile) {
        //TODO:
        try {
            if (dataFile.exists() && dataFile.isFile()) {
                if (dataFile.delete()) {
                    logger.info("data.xlsx was deleted");
                }
            }
            if (dataFile.createNewFile()) {
                logger.info("data.xlsx was created");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void savePositionToFile(File dataFile, Position position) {
        try (FileInputStream inputStream = new FileInputStream(dataFile);
             Workbook workbook = WorkbookFactory.create(inputStream);
             FileOutputStream outputStream = new FileOutputStream(dataFile);
        ) {
            Sheet sheetAt = workbook.getSheetAt(0);
            int lastRowNum = sheetAt.getLastRowNum();
            Row row = sheetAt.createRow(++lastRowNum);
            int columnCount = 0;
            Cell cell = row.createCell(columnCount++);
            cell.setCellValue("Наличие");
            cell = row.createCell(columnCount++);
            cell.setCellValue(position.getPercent());
            cell = row.createCell(columnCount++);
            cell.setCellValue(position.getPhotoName());
            cell = row.createCell(columnCount++);
            cell.setCellValue(position.getBuyersName());
            cell = row.createCell(columnCount++);
            cell.setCellValue(position.getProductSize());
            cell = row.createCell(columnCount++);
            cell.setCellValue(position.getPositionID());
            cell = row.createCell(columnCount++);
            cell.setCellValue(position.getProductAmount());
            cell = row.createCell(columnCount++);
            cell.setCellValue(position.getProductPurchasePrise());
            cell = row.createCell(columnCount++);
            cell.setCellValue(position.getIntermediatePrice());
            cell = row.createCell(columnCount++);
            cell.setCellValue(position.getPrice());
            cell = row.createCell(columnCount++);
            cell.setCellValue(position.getSum());
            cell = row.createCell(columnCount++);
            cell.setCellValue(position.getPurchaseSum());
            cell = row.createCell(columnCount++);
            cell.setCellValue(position.getPointOfSale());
            cell = row.createCell(columnCount++);
            cell.setCellValue(position.getResellerID());
            cell = row.createCell(columnCount);
            cell.setCellValue(position.getResellerName());
            cell = row.createCell(columnCount);
            cell.setCellValue(position.getPurchaseID());

            workbook.write(outputStream);

            logger.log(Level.INFO, "position " + position.getPositionID() + " was written to file");
        } catch (IOException e) {
            logger.log(Level.INFO, "position " + position.getPositionID() + " wasn't written to file");
            throw new RuntimeException(e);
        }
    }
}
