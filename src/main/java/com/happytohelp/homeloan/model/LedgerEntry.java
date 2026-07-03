package com.happytohelp.homeloan.model;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ledger_entries")
public class LedgerEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private LoanApplication loanApplication;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LedgerEntryType type;

    @Column(nullable = false)
    private LocalDateTime entryAt = LocalDateTime.now();

    private String description;

    @Column(precision = 19, scale = 2)
    private BigDecimal principalDebit;   // disbursement

    @Column(precision = 19, scale = 2)
    private BigDecimal principalCredit;  // principal repaid

    @Column(precision = 19, scale = 2)
    private BigDecimal interestCredit;   // interest paid

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal principalOutstanding;

    public Long getId() { return id; }

    public LoanApplication getLoanApplication() { return loanApplication; }
    public void setLoanApplication(LoanApplication loanApplication) { this.loanApplication = loanApplication; }

    public LedgerEntryType getType() { return type; }
    public void setType(LedgerEntryType type) { this.type = type; }

    public LocalDateTime getEntryAt() { return entryAt; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrincipalDebit() { return principalDebit; }
    public void setPrincipalDebit(BigDecimal principalDebit) { this.principalDebit = principalDebit; }

    public BigDecimal getPrincipalCredit() { return principalCredit; }
    public void setPrincipalCredit(BigDecimal principalCredit) { this.principalCredit = principalCredit; }

    public BigDecimal getInterestCredit() { return interestCredit; }
    public void setInterestCredit(BigDecimal interestCredit) { this.interestCredit = interestCredit; }

    public BigDecimal getPrincipalOutstanding() { return principalOutstanding; }
    public void setPrincipalOutstanding(BigDecimal principalOutstanding) { this.principalOutstanding = principalOutstanding; }
}