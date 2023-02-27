package ru.rishat.constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public final class Constants {
    public static final long PURCHASE_ID = 39040;
    public static final String[] STATUS = new String[]{"W","P","O"};
    public static final String LINE_OF_SELLER = "&place";
    public static final String MARKET_STATE_PLACE = "https://dedyuhina.posred.pro/purchases/"
            + PURCHASE_ID
            + "/market?state=";
    public static final String XPATH_BUTTON_OK = "//input[contains(@class,'button-pro')]";
    public static final String XPATH_FRAME = "//div[contains(@class,'pa-2')]";
    public static final String XPATH_FRAME_ = "//div[contains(@class,'pa-2')][";
    public static final String XPATH_IMAGE = "]//div[contains(@class,'v-image__')]";
    public static final String XPATH_TITLE = "]//div[contains(@class,'info-title darken')]";
    public static final String XPATH_SELLER = "]//div[contains(@class,'info-subtitle darken-light')]";
    public static final String XPATH_COMMENT = "]//div[contains(@class,'info-comment color-comment')]";
    public static final String XPATH_SUM = "]//div[@style='display: inline-block;']";
    public static final String XPATH_LINE_OF_SELLER = "]//div[contains(@class,'font-weight-bold')]";
    public static final String XPATH_BOTTOM_OF_THE_PAGE = "//div[@role='status']";
    public static final String PATH_IMAGES_PHOTO_OF_PURCHASE = "src/main/resources/images/" + PURCHASE_ID + "/";
    public static final String AUTH_CSV = "src/main/resources/auth.csv";
    public static final String RESOURCES_DATA_XLSX = "src/main/resources/" + getDateAndTime() + PURCHASE_ID + ".xlsx";
    public static final String[] LIST_FOR_VALIDATION_DATA_CELL = {
            "Куплен",
            "Не выкуплен",
            "Ожидает",
            "Собран",
            "Получен",
            "Не найден"
    };
    public static final Long[] RESELLERS_ID_LIST = {
            10L,
            20L,
            1L
    };
    public static final int DEFAULT_PERCENT = 10;
    public static final String BV_POINTER = "б/в";
    public static final String DELIMITER_FOR_SPECIAL_CALCULATION = "/";
    public static final String SEPARATOR_FOR_SPECIAL_CALCULATION = "[*]";
    public static final String DELIMITER_FOR_TITLE = "/";
    public static final String DELIMITER_FOR_RESELLER_ID = "№";
    public static final String DELIMITER_FOR_AMOUNT = "шт";
    public static final String DELIMITER_FOR_PRISE = "₽";
    public static final String REGEX_FOR_DEFINING_CALC_METHOD = "([0-9]*)" + DELIMITER_FOR_SPECIAL_CALCULATION + "([0-9]*)";
    public static final String REGEX_FOR_DEFINING_CALC_METHOD_WITH_DOT = "([0-9]*(\\.))([0-9]*)" + DELIMITER_FOR_SPECIAL_CALCULATION + "([0-9]*)";
    public static final String REGEX_FOR_DEFINING_CALC_METHOD_STARTING_WITH_ASTERISK = "\\*([0-9]*)" + DELIMITER_FOR_SPECIAL_CALCULATION + "([0-9]*)";
    public static final String REGEX_FOR_DEFINING_CALC_METHOD_WITH_COMMA = "([0-9]*(\\,))([0-9]*)" + DELIMITER_FOR_SPECIAL_CALCULATION + "([0-9]*)";
    public static List<String> DATA_FROM_FILE;

    private static String getDateAndTime() {
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("E-yyyy.MM.dd_hh-mm-ss_a_zzz_");
        return formatForDateNow.format(dateNow);
    }
}
