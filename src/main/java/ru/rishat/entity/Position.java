package ru.rishat.entity;

import java.util.logging.Logger;

public class Position {
    private static final Logger logger = Logger.getLogger(Position.class.getName());
    private long purchaseID;
    private long positionID;
    private long resellerID;
    private String resellerName;
    private String buyersName;
    private String productSize;
    private int productAmount;
    private int productPurchasePrise;
    private int SpecialProductPurchasePrise;
    private String photoName;
    private int percent = 10;
    private int percentOfIntermediatePrice = 10;
    private String pointOfSale;
    private boolean isSpecialTypeOfCalculation = false;
    private boolean isBV = false;

    public int getSpecialProductPurchasePrise() {
        return SpecialProductPurchasePrise;
    }

    public void setSpecialProductPurchasePrise(int specialProductPurchasePrise) {
        SpecialProductPurchasePrise = specialProductPurchasePrise;
    }

    public boolean isBV() {
        return isBV;
    }

    public void setBV(boolean BV) {
        isBV = BV;
    }

    private String specialGoal;

    public String getSpecialGoal() {
        return specialGoal;
    }

    public void setSpecialGoal(String specialGoal) {
        this.specialGoal = specialGoal;
    }

    public boolean isSpecialTypeOfCalculation() {
        return isSpecialTypeOfCalculation;
    }

    public int getPercentOfIntermediatePrice() {
        return percentOfIntermediatePrice;
    }

    public void setPercentOfIntermediatePrice(int percentOfIntermediatePrice) {
        this.percentOfIntermediatePrice = percentOfIntermediatePrice;
    }

    public void setSpecialTypeOfCalculation(boolean specialTypeOfCalculation) {
        isSpecialTypeOfCalculation = specialTypeOfCalculation;
    }

    public void setResellerID(long resellerID) {
        this.resellerID = resellerID;
    }

    public String getPointOfSale() {
        return pointOfSale;
    }

    public void setPointOfSale(String pointOfSale) {
        this.pointOfSale = pointOfSale;
    }

    public long getIntermediatePrice() {
        long result;
        if (isSpecialTypeOfCalculation) {
            result = this.getSpecialProductPurchasePrise();
        } else {
            double percentAmount = this.getPercentOfIntermediatePrice() * this.getProductPurchasePrise() / 100.;
            result = (long) (Math.ceil((this.getProductPurchasePrise() + percentAmount) / 10.) * 10);
        }
        return result;
    }

    public long getPrice() {
        long result;
        if (isSpecialTypeOfCalculation) {
            double percentAmount = this.getPercent() * this.getSpecialProductPurchasePrise() / 100.;
            result = (long) (Math.ceil((this.getSpecialProductPurchasePrise() + percentAmount) / 10.) * 10);
        } else {
            double percentAmount = this.getPercent() * this.getIntermediatePrice() / 100.;
            result = (long) (Math.ceil(this.getIntermediatePrice() + percentAmount));
        }
        return result;
    }

    public long getPurchaseID() {
        return purchaseID;
    }

    public void setPurchaseID(long purchaseID) {
        this.purchaseID = purchaseID;
    }

    public long getPositionID() {
        return positionID;
    }

    public void setPositionID(long positionID) {
        this.positionID = positionID;
    }

    public long getResellerID() {
        return resellerID;
    }

    public void setResellerID(Long resellerID) {
        this.resellerID = resellerID;
    }

    public String getResellerName() {
        return resellerName;
    }

    public void setResellerName(String resellerName) {
        this.resellerName = resellerName;
    }

    public String getBuyersName() {
        return buyersName;
    }

    public void setBuyersName(String buyersName) {
        this.buyersName = buyersName;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    public int getProductPurchasePrise() {
        return productPurchasePrise;
    }

    public void setProductPurchasePrise(int productPurchasePrise) {
        this.productPurchasePrise = productPurchasePrise;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public double getSum() {
        return this.productAmount * this.getPrice();
    }

    public double getPurchaseSum() {
        return this.productAmount * this.getProductPurchasePrise();
    }
}
