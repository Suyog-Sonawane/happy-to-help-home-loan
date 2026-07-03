package com.happytohelp.homeloan.service;



import com.happytohelp.homeloan.model.LedgerEntry;
import com.happytohelp.homeloan.model.LedgerEntryType;
import com.happytohelp.homeloan.model.LoanApplication;
import com.happytohelp.homeloan.repo.LedgerEntryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class LedgerService {

    private final LedgerEntryRepository ledgerRepo;

    public LedgerService(LedgerEntryRepository ledgerRepo) {
        this.ledgerRepo = ledgerRepo;
    }

    public BigDecimal latestOutstanding(Long appId) {
        return ledgerRepo.findTopByLoanApplicationIdOrderByIdDesc(appId)
                .map(LedgerEntry::getPrincipalOutstanding)
                .orElse(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
    }

    public LedgerEntry addDisbursement(LoanApplication app, BigDecimal amount) {
        BigDecimal newOutstanding = latestOutstanding(app.getId())
                .add(amount).setScale(2, RoundingMode.HALF_UP);

        LedgerEntry e = new LedgerEntry();
        e.setLoanApplication(app);
        e.setType(LedgerEntryType.DISBURSEMENT);
        e.setDescription("Loan disbursed");
        e.setPrincipalDebit(amount.setScale(2, RoundingMode.HALF_UP));
        e.setPrincipalCredit(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        e.setInterestCredit(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        e.setPrincipalOutstanding(newOutstanding);
        return ledgerRepo.save(e);
    }

    public LedgerEntry addEmiPayment(LoanApplication app, int emiNo,
                                     BigDecimal principalPaid, BigDecimal interestPaid) {
        BigDecimal prev = latestOutstanding(app.getId());
        BigDecimal newOutstanding = prev.subtract(principalPaid)
                .max(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);

        LedgerEntry e = new LedgerEntry();
        e.setLoanApplication(app);
        e.setType(LedgerEntryType.EMI_PAYMENT);
        e.setDescription("EMI payment - Installment #" + emiNo);
        e.setPrincipalDebit(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        e.setPrincipalCredit(principalPaid.setScale(2, RoundingMode.HALF_UP));
        e.setInterestCredit(interestPaid.setScale(2, RoundingMode.HALF_UP));
        e.setPrincipalOutstanding(newOutstanding);
        return ledgerRepo.save(e);
    }

    public List<LedgerEntry> getLedger(Long appId) {
        return ledgerRepo.findByLoanApplicationIdOrderByIdAsc(appId);
    }
}