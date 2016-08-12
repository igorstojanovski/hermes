package org.programirame.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;
import java.util.Date;

//@Document(indexName = "hermes", type = "invoice", shards = 1, replicas = 0, refreshInterval = "-1")
public class Invoice {

//    @Id
    private String invoiceId;
    private Date invoiceDate;
    private Date invoiceDueDate;
    private BigDecimal invoiceAmount;
    private BigDecimal invoicePayedAmount;
    private String clientId;

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Date getInvoiceDueDate() {
        return invoiceDueDate;
    }

    public void setInvoiceDueDate(Date invoiceDueDate) {
        this.invoiceDueDate = invoiceDueDate;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public BigDecimal getInvoicePayedAmount() {
        return invoicePayedAmount;
    }

    public void setInvoicePayedAmount(BigDecimal invoicePayedAmount) {
        this.invoicePayedAmount = invoicePayedAmount;
    }

    public Invoice(String invoiceId, String clientId, Date invoiceDate, Date invoiceDueDate, BigDecimal invoiceAmount, BigDecimal invoicePayedAmount) {
        this.invoiceId = invoiceId;
        this.clientId = clientId;
        this.invoiceDate = invoiceDate;
        this.invoiceDueDate = invoiceDueDate;
        this.invoiceAmount = invoiceAmount;
        this.invoicePayedAmount = invoicePayedAmount;
    }

    public Invoice() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
