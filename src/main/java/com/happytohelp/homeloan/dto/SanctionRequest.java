package com.happytohelp.homeloan.dto;


import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class SanctionRequest {

    @NotNull @DecimalMin("1")
    private BigDecimal sanctionedAmount;

    @NotNull @DecimalMin("0.01")
    private BigDecimal annualInterestRate; // e.g., 9.5

    @NotNull @Min(6)
    private Integer tenureMonths;

    private String remarks;

    public BigDecimal getSanctionedAmount() { return sanctionedAmount; }
    public void setSanctionedAmount(BigDecimal sanctionedAmount) { this.sanctionedAmount = sanctionedAmount; }

    public BigDecimal getAnnualInterestRate() { return annualInterestRate; }
    public void setAnnualInterestRate(BigDecimal annualInterestRate) { this.annualInterestRate = annualInterestRate; }

    public Integer getTenureMonths() { return tenureMonths; }
    public void setTenureMonths(Integer tenureMonths) { this.tenureMonths = tenureMonths; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
}