package ru.rishat.constants;

public class Constants {

    public static final long PURCHASE_ID = 35316;
    public static final String MARKET_STATE_PLACE = "https://dedyuhina.posred.pro/purchases/" + PURCHASE_ID + "/market?state=W&place";
    public static final String XPATH_BUTTON_OK = "//input[contains(@class,'button-pro')]";
    public static final String XPATH_FRAME = "//div[contains(@class,'pa-2')]";
    public static final String XPATH_FRAME_ = "//div[contains(@class,'pa-2')][";
    public static final String XPATH_IMAGE = "]//div[contains(@class,'v-image__')]";
    public static final String XPATH_TITLE = "]//div[contains(@class,'info-title darken')]";
    public static final String XPATH_SELLER = "]//div[contains(@class,'info-subtitle darken-light')]";
    public static final String XPATH_COMMENT = "]//div[contains(@class,'info-comment color-comment')]";
    public static final String XPATH_SUM = "]//div[@style='display: inline-block;']";
    public static final String XPATH_LINE = "]//div[contains(@class,'font-weight-bold')]";
    public static final String BOTTOM_TO_PAGE = "//div[@role='status']";
    public static final String PATH_IMAGES_PHOTO_OF_PURCHASE = "src/main/resources/images/photoOfPurchase/";
    public static final String RESOURCES_DATA_XLSX = "src/main/resources/data.xlsx";
    public static final String[] LIST_FOR_VALIDATION_DATA_CELL = {"Ожидает", "Куплен", "Собран", "Выдан", "Не найден"};


}
