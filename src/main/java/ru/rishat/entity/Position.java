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
    private String photoName;
    private int percent;
    private String pointOfSale;

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
        double percentAmount = this.getPercent() * this.getProductPurchasePrise() / 100.;
        result = (long) (Math.ceil((this.getProductPurchasePrise() + percentAmount) / 10.) * 10);
        return result;
    }

    public long getPrice() {
        long result;
        double percentAmount = 10 * this.getIntermediatePrice() / 100.;
        result = (long) (Math.ceil((this.getIntermediatePrice() + percentAmount) / 10.) * 10);
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
