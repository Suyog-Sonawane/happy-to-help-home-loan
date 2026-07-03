package com.happytohelp.homeloan.dto;

/*
 * import jakarta.validation.constraints.DecimalMin; import
 * jakarta.validation.constraints.NotNull;
 * 
 * import java.math.BigDecimal; import java.time.LocalDate;
 * 
 * public class DisburseRequest {
 * 
 * @NotNull @DecimalMin("1") private BigDecimal disbursedAmount;
 * 
 * // optional; if null we will use today private LocalDate disbursedDate;
 * 
 * public BigDecimal getDisbursedAmount() { return disbursedAmount; } public
 * void setDisbursedAmount(BigDecimal disbursedAmount) { this.disbursedAmount =
 * disbursedAmount; }
 * 
 * public LocalDate getDisbursedDate() { return disbursedDate; } public void
 * setDisbursedDate(LocalDate disbursedDate) { this.disbursedDate =
 * disbursedDate; } }
 */   


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DisburseRequest {

    @NotNull @DecimalMin("1")
    private BigDecimal disbursedAmount;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate disbursedDate;

    public BigDecimal getDisbursedAmount() { return disbursedAmount; }
    public void setDisbursedAmount(BigDecimal disbursedAmount) { this.disbursedAmount = disbursedAmount; }

    public LocalDate getDisbursedDate() { return disbursedDate; }
    public void setDisbursedDate(LocalDate disbursedDate) { this.disbursedDate = disbursedDate; }
}