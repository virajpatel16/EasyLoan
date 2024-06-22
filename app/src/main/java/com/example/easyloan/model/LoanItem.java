package com.example.easyloan.model;

public class LoanItem {
    private final String principalAmount;
    private final String rateOfInterest;
    private final String tenureYears;
    private final String tenureMonths;

    public LoanItem(String principalAmount, String rateOfInterest, String tenureYears, String tenureMonths) {
        this.principalAmount = principalAmount;
        this.rateOfInterest = rateOfInterest;
        this.tenureYears = tenureYears;
        this.tenureMonths = tenureMonths;
    }

    public String getPrincipalAmount() {
        return principalAmount;
    }

    public String getRateOfInterest() {
        return rateOfInterest;
    }

    public String getTenureYears() {
        return tenureYears;
    }

    public String getTenureMonths() {
        return tenureMonths;
    }
}
