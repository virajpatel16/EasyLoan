package com.example.easyloan.Roomdatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "emi")
public class User {

    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "principalamount")
    private  String principalAmount;

    public User( String principalAmount, String rateOfInterest, String tenureYears, String tenureMonths) {

        this.principalAmount = principalAmount;
        this.rateOfInterest = rateOfInterest;
        this.tenureYears = tenureYears;
        this.tenureMonths = tenureMonths;
    }

    @ColumnInfo(name = "rateOfInterest")
    private  String rateOfInterest;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(String principalAmount) {
        this.principalAmount = principalAmount;
    }

    public String getRateOfInterest() {
        return rateOfInterest;
    }

    public void setRateOfInterest(String rateOfInterest) {
        this.rateOfInterest = rateOfInterest;
    }

    public String getTenureYears() {
        return tenureYears;
    }

    public void setTenureYears(String tenureYears) {
        this.tenureYears = tenureYears;
    }

    public String getTenureMonths() {
        return tenureMonths;
    }

    public void setTenureMonths(String tenureMonths) {
        this.tenureMonths = tenureMonths;
    }

    @ColumnInfo(name = "tenureYears")
    private  String tenureYears;
    @ColumnInfo(name = "tenureMonths")
    private  String tenureMonths;
}
