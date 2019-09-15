package com.example.easywin.model;

public class HistoryRecord {
    private String partyName;
    private String amountOfMoney;
    private String percentOfPayment;

    public HistoryRecord(String name, String amount, String percent) {
        partyName = name;
        amountOfMoney = amount;
        percentOfPayment = percent;
    }

        public String getPercentOfPayment() {
            return percentOfPayment;
        }


    public String getAmountOfMoney() {
        return amountOfMoney;
    }


    public String getPartyName() {
        return partyName;
    }
}
