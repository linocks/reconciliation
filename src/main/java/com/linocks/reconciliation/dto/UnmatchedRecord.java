package com.linocks.reconciliation.dto;

public class UnmatchedRecord {
    private String date1;
    private String reference1;
    private String amount1;
    private String date2;
    private String reference2;
    private String amount2;

    public UnmatchedRecord(){}

    public UnmatchedRecord(String date1, String reference1, String amount1, String date2, String reference2, String amount2){
        this.date1 = date1;
        this.reference1 = reference1;
        this.amount1 = amount1;
        this.date2 = date2;
        this.reference2 = reference2;
        this.amount2 = amount2;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getReference1() {
        return reference1;
    }

    public void setReference1(String reference1) {
        this.reference1 = reference1;
    }

    public String getAmount1() {
        return amount1;
    }

    public void setAmount1(String amount1) {
        this.amount1 = amount1;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public String getReference2() {
        return reference2;
    }

    public void setReference2(String reference2) {
        this.reference2 = reference2;
    }

    public String getAmount2() {
        return amount2;
    }

    public void setAmount2(String amount2) {
        this.amount2 = amount2;
    }

    @Override
    public String toString() {
        return "UnmatchedRecord{" +
                "date1='" + date1 + '\'' +
                ", reference1='" + reference1 + '\'' +
                ", amount1='" + amount1 + '\'' +
                ", date2='" + date2 + '\'' +
                ", reference2='" + reference2 + '\'' +
                ", amount2='" + amount2 + '\'' +
                '}';
    }
}
