package ru.rishat.entity;

import static ru.rishat.constants.Constants.DEFAULT_PERCENT;

public class Position implements Comparable<Position> {
    private long purchaseID;
    private long positionID;
    private long resellerID;
    private String resellerName;
    private String buyersName = "";
    private String productSize;
    private int productAmount;
    private int productPurchasePrice;
    private int SpecialProductPurchasePrice;
    private String photoURL;
    private int percent = DEFAULT_PERCENT;
    private int percentOfIntermediatePrice = DEFAULT_PERCENT;
    private String pointOfSale;
    private boolean isSpecialTypeOfCalculation = false;
    private boolean isBV = false;

    public int getSpecialProductPurchasePrice() {
        return SpecialProductPurchasePrice;
    }

    public void setSpecialProductPurchasePrice(int specialProductPurchasePrice) {
        SpecialProductPurchasePrice = specialProductPurchasePrice;
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
            result = this.getSpecialProductPurchasePrice();
        } else {
            double percentAmount = this.getPercentOfIntermediatePrice() * this.getProductPurchasePrice() / 100.;
            result = (long) (Math.ceil((this.getProductPurchasePrice() + percentAmount) / 10.) * 10);
        }
        return result;
    }

    public long getPrice() {
        long result;
        if (isSpecialTypeOfCalculation) {
            double percentAmount = this.getPercent() * this.getSpecialProductPurchasePrice() / 100.;
            result = (long) (Math.ceil((this.getSpecialProductPurchasePrice() + percentAmount) / 10.) * 10);
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

    public int getProductPurchasePrice() {
        return productPurchasePrice;
    }

    public void setProductPurchasePrice(int productPurchasePrice) {
        this.productPurchasePrice = productPurchasePrice;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
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
        return this.productAmount * this.getProductPurchasePrice();
    }

    @Override
    public int compareTo(Position position) {
        int compare;
        try {
            compare = this.getBuyersName().compareToIgnoreCase(position.getBuyersName());
        } catch (Exception e) {
            System.out.println(position.toString());
            System.out.println(this);
            throw new RuntimeException(e);
        }
        return compare;
    }

    @Override
    public String toString() {
        return "Position{" +
                "purchaseID=" + purchaseID +
                ", positionID=" + positionID +
                ", resellerID=" + resellerID +
                ", resellerName='" + resellerName + '\'' +
                ", buyersName='" + buyersName + '\'' +
                ", productSize='" + productSize + '\'' +
                ", productAmount=" + productAmount +
                ", productPurchasePrice=" + productPurchasePrice +
                ", SpecialProductPurchasePrice=" + SpecialProductPurchasePrice +
                ", photoURL='" + photoURL + '\'' +
                ", percent=" + percent +
                ", percentOfIntermediatePrice=" + percentOfIntermediatePrice +
                ", pointOfSale='" + pointOfSale + '\'' +
                ", isSpecialTypeOfCalculation=" + isSpecialTypeOfCalculation +
                ", isBV=" + isBV +
                ", specialGoal='" + specialGoal + '\'' +
                '}';
    }
}
