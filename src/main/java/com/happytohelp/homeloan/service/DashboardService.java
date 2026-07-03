package com.happytohelp.homeloan.service;



import com.happytohelp.homeloan.model.ApplicationStatus;
import com.happytohelp.homeloan.model.LoanApplication;
import com.happytohelp.homeloan.repo.LoanApplicationRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DashboardService {

    private final LoanApplicationRepository loanRepo;
    private final LedgerService ledgerService;

    public DashboardService(LoanApplicationRepository loanRepo, LedgerService ledgerService) {
        this.loanRepo = loanRepo;
        this.ledgerService = ledgerService;
    }

    /** Total outstanding across ALL disbursed loans (admin KPI) */
    public BigDecimal totalOutstandingAllDisbursed() {
        List<LoanApplication> disbursed = loanRepo.findByStatusOrderByIdDesc(ApplicationStatus.DISBURSED);
        BigDecimal sum = BigDecimal.ZERO;
        for (LoanApplication a : disbursed) {
            sum = sum.add(ledgerService.latestOutstanding(a.getId()));
        }
        return sum;
    }

    /** Total outstanding for a CUSTOMER across their disbursed loans */
    public BigDecimal totalOutstandingForUser(Long userId) {
        List<LoanApplication> apps = loanRepo.findByUserIdOrderByIdDesc(userId);
        BigDecimal sum = BigDecimal.ZERO;
        for (LoanApplication a : apps) {
            if (a.getStatus() == ApplicationStatus.DISBURSED) {
                sum = sum.add(ledgerService.latestOutstanding(a.getId()));
            }
        }
        return sum;
    }
}