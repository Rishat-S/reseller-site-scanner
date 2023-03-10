package ru.rishat.repository;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.*;
import ru.rishat.entity.Position;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.rishat.constants.Constants.*;

public class PositionRepositoryImp implements PositionRepository {
    private static final Logger logger = Logger.getLogger(PositionRepositoryImp.class.getName());
    private final List<Position> positions = new ArrayList<>();

    @Override
    public void saveImageToFile(InputStream in, String photoName) {
        try {
            Files.copy(in, Paths.get(photoName + ".png"), StandardCopyOption.REPLACE_EXISTING);
            logger.log(Level.INFO, "Photo " + photoName + ".png was saved");
        } catch (IOException e) {
            logger.log(Level.INFO, "Photo " + photoName + ".png don't saved");
            e.printStackTrace();
        }
    }

    @Override
    public void savePosition(Position position) {
        positions.add(position);
        logger.log(Level.INFO, "-->> Position " + position.getPositionID() + " was saved to queue <<--");
    }

    @Override
    public void saveAllPositionsToFile() {
        File dataFile = new File(RESOURCES_DATA_XLSX);
        if (dataFile.exists() && dataFile.isFile()) {
            System.out.println("Data file exists");
        } else {
            newDataFileProvider(dataFile);
        }
        try (FileInputStream inputStream = new FileInputStream(dataFile);
             Workbook workbook = WorkbookFactory.create(inputStream);
             FileOutputStream outputStream = new FileOutputStream(dataFile);
        ) {
            Collections.sort(positions);
            Sheet sheetAt;
            for (Position position : positions) {
                if (Arrays.asList(RESELLERS_ID_LIST).contains(position.getResellerID())) {
                    if (position.getResellerID() == 10) {
                        sheetAt = workbook.getSheetAt(0);
                    } else if (position.getResellerID() == 1) {
                        sheetAt = workbook.getSheetAt(1);
                    } else if (position.getResellerID() == 2) {
                        sheetAt = workbook.getSheetAt(1);
                    } else {
                        sheetAt = workbook.getSheetAt(3);
                    }
                } else {
                    sheetAt = workbook.getSheetAt(4);
                }
                savePositionToFile(workbook, sheetAt, position);
            }

            workbook.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private CellStyle createCellStyleForHeader(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("Calibri");
        font.setBold(true);
        font.setUnderline(Font.U_SINGLE);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setRotation((short) 90);
        return style;
    }

    private void newDataFileProvider(File dataFile) {
        try (XSSFWorkbook xssfWorkbook = new XSSFWorkbook()) {
            for (int j = 0; j <= RESELLERS_ID_LIST.length; j++) {
                XSSFSheet xssfSheet = xssfWorkbook.createSheet("Robot" + (j + 1));
                xssfSheet.setDefaultRowHeightInPoints(100);
                XSSFRow row = xssfSheet.createRow(0);
                final CellStyle cellStyle = createCellStyleForHeader(xssfWorkbook);

                for (int i = 0; i < 18; i++) {
                    XSSFCell cell = row.createCell(i);
                    cell.setCellStyle(cellStyle);
                    switch (i) {
                        case 0: {
                            cell.setCellValue("Статус");
                            break;
                        }
                        case 1: {
                            cell.setCellValue("%");
                            break;
                        }
                        case 2: {
                            xssfSheet.setColumnWidth(i, 5000);
                            cell.setCellValue("Фото");
                            break;
                        }
                        case 3: {
                            cell.setCellValue("ФИО");
                            break;
                        }
                        case 4: {
                            cell.setCellValue("Размер");
                            break;
                        }
                        case 5: {
                            cell.setCellValue("арт.");
                            break;
                        }
                        case 6: {
                            cell.setCellValue("Кол-во");
                            break;
                        }
                        case 7: {
                            cell.setCellValue("Зак. цен");
                            break;
                        }
                        case 8: {
                            cell.setCellValue("Цена");
                            break;
                        }
                        case 9: {
                            cell.setCellValue("Цена с орг.");
                            break;
                        }
                        case 10: {
                            cell.setCellValue("Ц*К");
                            break;
                        }
                        case 11: {
                            cell.setCellValue("Зак. Ц*К");
                            break;
                        }
                        case 12: {
                            cell.setCellValue("Точка");
                            break;
                        }
                        case 13: {
                            cell.setCellValue("ID посредника");
                            break;
                        }
                        case 14: {
                            cell.setCellValue("ФИО посредника");
                            break;
                        }
                        case 15: {
                            cell.setCellValue("№ Закупки");
                            break;
                        }
                        case 16: {
                            cell.setCellValue("Офис");
                            break;
                        }
                        case 17: {
                            cell.setCellValue("ОВР");
                        }
                    }
                }
            }

            try (FileOutputStream fileOutputStream = new FileOutputStream(dataFile)) {
                xssfWorkbook.write(fileOutputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void savePositionToFile(Workbook workbook, Sheet sheetAt, Position position) {
        int lastRowNum = sheetAt.getLastRowNum();
        Row row = sheetAt.createRow(++lastRowNum);
        int columnCount = 0;
        Cell cell = row.createCell(columnCount++);
        setDataValidationToCell(sheetAt, cell, LIST_FOR_VALIDATION_DATA_CELL);
        cell = row.createCell(columnCount++);
        cell.setCellValue(position.getPercent());
        cell = row.createCell(columnCount++);
        cell.setCellValue(position.getPhotoURL());
        setLinkToCell(workbook, cell, position.getPhotoURL());
        insertImageToCellFromURL(workbook, sheetAt, cell, position.getPhotoURL());
        cell = row.createCell(columnCount++);
        cell.setCellValue(position.getBuyersName());
        cell = row.createCell(columnCount++);
        cell.setCellValue(position.getProductSize());
        cell = row.createCell(columnCount++);
        setLinkToCell(workbook, cell, position.getPhotoURL());
        cell.setCellValue(position.getPositionID());
        cell = row.createCell(columnCount++);
        cell.setCellValue(position.getProductAmount());
        cell = row.createCell(columnCount++);
        cell.setCellValue(position.getProductPurchasePrice());
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
        cell = row.createCell(columnCount++);
        cell.setCellValue(position.getResellerName());
        cell = row.createCell(columnCount++);
        cell.setCellValue(position.getPurchaseID());
        cell = row.createCell(++columnCount);
        cell.setCellValue(position.getSpecialGoal());

        logger.log(Level.INFO, "position " + position.getPositionID() + " was written to file");

    }

    private static void setDataValidationToCell(Sheet sheetAt, Cell cell, String[] list) {
        cell.setCellValue(list[0]);
        DataValidationHelper helper = sheetAt.getDataValidationHelper();
        XSSFDataValidationConstraint constraint = new XSSFDataValidationConstraint(list);
        CellRangeAddressList regions = new CellRangeAddressList(cell.getRowIndex(), cell.getRowIndex(), cell.getColumnIndex(), cell.getColumnIndex());
        DataValidation validation = helper.createValidation(constraint, regions);
        validation.setSuppressDropDownArrow(true);
        validation.setShowErrorBox(true);
        validation.setErrorStyle(0);
        sheetAt.addValidationData(validation);
    }

    private static void insertImageToCellFromURL(Workbook workbook, Sheet sheetAt, Cell cell, String url) {
        try (InputStream inputStream1 = new URL(url).openStream()) {
            byte[] inputImageBytes = IOUtils.toByteArray(inputStream1);
            int inputImagePicture = workbook.addPicture(inputImageBytes, Workbook.PICTURE_TYPE_JPEG);
            XSSFDrawing drawing = (XSSFDrawing) sheetAt.createDrawingPatriarch();
            XSSFClientAnchor imageAnchor = new XSSFClientAnchor();
            imageAnchor.setAnchorType(ClientAnchor.AnchorType.MOVE_DONT_RESIZE);
            imageAnchor.setCol1(cell.getColumnIndex());
            imageAnchor.setCol2(cell.getColumnIndex() + 1);
            imageAnchor.setRow1(cell.getRowIndex());
            imageAnchor.setRow2(cell.getRowIndex() + 1);
            drawing.createPicture(imageAnchor, inputImagePicture);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setLinkToCell(Workbook workbook, Cell cell, String photoName) {
        final CreationHelper helper = workbook.getCreationHelper();
        final CellStyle cellStyle = workbook.createCellStyle();
        final Font font = workbook.createFont();
        font.setFontName("Calibri");
        font.setColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
        font.setUnderline(Font.U_SINGLE);
        cellStyle.setFont(font);
        final XSSFHyperlink hyperlink = (XSSFHyperlink) helper.createHyperlink(HyperlinkType.URL);
        hyperlink.setAddress(photoName);
        cell.setHyperlink(hyperlink);
        cell.setCellStyle(cellStyle);
    }
}
