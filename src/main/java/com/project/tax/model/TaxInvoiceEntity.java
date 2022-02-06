package com.project.tax.model;

import com.opencsv.bean.CsvBindByPosition;

import java.sql.Timestamp;

public class TaxInvoiceEntity {

    @CsvBindByPosition(position = 0)
    private int customerId;

    @CsvBindByPosition(position = 1)
    private String invoiceNo;

    @CsvBindByPosition(position = 2)
    private String timestamp;

    @CsvBindByPosition(position = 3)
    private Double amount;

    @CsvBindByPosition(position = 4)
    private String taxType;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }
}
