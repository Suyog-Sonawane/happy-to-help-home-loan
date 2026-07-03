package com.happytohelp.homeloan.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "loan_applications")
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // EAGER to simplify Thymeleaf UI later
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status = ApplicationStatus.DRAFT;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt;

    // Customer inputs
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal requestedAmount;

    @Column(nullable = false)
    private Integer tenureMonths;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal propertyValue;

    @Column(nullable = false)
    private String propertyAddress;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal monthlyIncome;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal existingEmi;

    // Admin sanction
    @Column(precision = 19, scale = 2)
    private BigDecimal sanctionedAmount;

    @Column(precision = 5, scale = 2)
    private BigDecimal annualInterestRate;

    private LocalDate sanctionedDate;

    @Column(length = 1000)
    private String adminRemarks;

    // Disbursement
    @Column(precision = 19, scale = 2)
    private BigDecimal disbursedAmount;

    private LocalDate disbursedDate;

    // getters/setters
    public Long getId() { return id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

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

    public BigDecimal getSanctionedAmount() { return sanctionedAmount; }
    public void setSanctionedAmount(BigDecimal sanctionedAmount) { this.sanctionedAmount = sanctionedAmount; }

    public BigDecimal getAnnualInterestRate() { return annualInterestRate; }
    public void setAnnualInterestRate(BigDecimal annualInterestRate) { this.annualInterestRate = annualInterestRate; }

    public LocalDate getSanctionedDate() { return sanctionedDate; }
    public void setSanctionedDate(LocalDate sanctionedDate) { this.sanctionedDate = sanctionedDate; }

    public String getAdminRemarks() { return adminRemarks; }
    public void setAdminRemarks(String adminRemarks) { this.adminRemarks = adminRemarks; }

    public BigDecimal getDisbursedAmount() { return disbursedAmount; }
    public void setDisbursedAmount(BigDecimal disbursedAmount) { this.disbursedAmount = disbursedAmount; }

    public LocalDate getDisbursedDate() { return disbursedDate; }
    public void setDisbursedDate(LocalDate disbursedDate) { this.disbursedDate = disbursedDate; }
}