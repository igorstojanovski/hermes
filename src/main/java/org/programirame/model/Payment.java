package org.programirame.model;

import java.math.BigDecimal;
import java.util.Date;

public class Payment {

    private Date paymentDate;
    private String paymentInvoiceId;
    private BigDecimal paymentAmount;

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentInvoiceId() {
        return paymentInvoiceId;
    }

    public void setPaymentInvoiceId(String paymentInvoiceId) {
        this.paymentInvoiceId = paymentInvoiceId;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
