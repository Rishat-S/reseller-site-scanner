package ru.rishat.entity;

public class Position {
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

    public void setResellerID(long resellerID) {
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
}
