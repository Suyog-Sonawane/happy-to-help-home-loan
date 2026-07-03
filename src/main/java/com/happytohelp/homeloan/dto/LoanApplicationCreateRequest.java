package com.happytohelp.homeloan.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class LoanApplicationCreateRequest {

    @NotNull @DecimalMin("1")
    private BigDecimal requestedAmount;

    @NotNull @Min(6)
    private Integer tenureMonths;

    @NotNull @DecimalMin("1")
    private BigDecimal propertyValue;

    @NotBlank
    private String propertyAddress;

    @NotNull @DecimalMin("1")
    private BigDecimal monthlyIncome;

    @NotNull @DecimalMin("0")
    private BigDecimal existingEmi;

    public BigDecimal getRequestedAmount() { return requestedAmount; }
    public void setRequestedAmount(BigDecimal requestedAmount) { this.requestedAmount = requestedAmount; }

    public Integer getTenureMonths() { return tenureMonths; }
    public void setTenureMonths(Integer tenureMonths) { this.tenureMonths = tenureMonths; }

    public BigDecimal getPropertyValue() { return propertyValue; }
    public void setPropertyValue(BigDecimal propertyValue) { this.propertyValue = propertyValue; }

    public String getPropertyAddress() { return propertyAddress; }
    public void setPropertyAddress(String propertyAddress) { this.propertyAddress = propertyAddress; }

    public BigDecimal getMonthlyIncome() { return monthlyIncome; }
    public void setMonthlyIncome(BigDecimal monthlyIncome) { this.monthlyIncome = monthlyIncome; }

    public BigDecimal getExistingEmi() { return existingEmi; }
    public void setExistingEmi(BigDecimal existingEmi) { this.existingEmi = existingEmi; }
}