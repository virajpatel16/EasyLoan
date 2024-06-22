package com.example.easyloan.model;

public class Bank {
    private final int id;
    private final String name;
    private final int logo;
    private final String balanceNumber;
    private final String statementNumber;
    private final String careNumber;

    public Bank(int id, String name, int logo, String balanceNumber, String statementNumber, String careNumber) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.balanceNumber = balanceNumber;
        this.statementNumber = statementNumber;
        this.careNumber = careNumber;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLogo() {
        return logo;
    }

    public String getBalanceNumber() {
        return balanceNumber;
    }

    public String getStatementNumber() {
        return statementNumber;
    }

    public String getCareNumber() {
        return careNumber;
    }
}
