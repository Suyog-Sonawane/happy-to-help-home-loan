package com.happytohelp.homeloan.dto;


import com.happytohelp.homeloan.model.PaymentMethod;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class PayEmiRequest {

    @NotNull
    private BigDecimal amount;

    @NotNull
    private PaymentMethod method;

    private String txnRef;

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public PaymentMethod getMethod() { return method; }
    public void setMethod(PaymentMethod method) { this.method = method; }

    public String getTxnRef() { return txnRef; }
    public void setTxnRef(String txnRef) { this.txnRef = txnRef; }
}