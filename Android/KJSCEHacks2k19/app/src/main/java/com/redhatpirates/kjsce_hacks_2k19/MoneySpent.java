package com.redhatpirates.kjsce_hacks_2k19;

public class MoneySpent {
    String uid,amount,date,expenditure_type;

    public MoneySpent(String uid, String amount, String date, String expenditure_type) {
        this.uid = uid;
        this.amount = amount;
        this.date = date;
        this.expenditure_type = expenditure_type;
    }

    public MoneySpent(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getExpenditure_type() {
        return expenditure_type;
    }
}
