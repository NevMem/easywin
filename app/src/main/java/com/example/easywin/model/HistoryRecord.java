package com.example.easywin.model;

public class HistoryRecord {
    public String partyName;
    public String amountOfMoney;
    public String allPeople;
    public String currentPeople;

    public HistoryRecord(String name, String amount, String current, String all     ) {
        partyName = name;
        amountOfMoney = amount;
        allPeople = all;
        currentPeople = current;
    }
}
