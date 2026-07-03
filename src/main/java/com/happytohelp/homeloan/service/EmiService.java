package com.happytohelp.homeloan.service;


import com.happytohelp.homeloan.model.EmiSchedule;
import com.happytohelp.homeloan.model.EmiStatus;
import com.happytohelp.homeloan.model.LoanApplication;
import com.happytohelp.homeloan.repo.EmiScheduleRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmiService {

    private final EmiScheduleRepository emiRepo;

    public EmiService(EmiScheduleRepository emiRepo) {
        this.emiRepo = emiRepo;
    }

    public List<EmiSchedule> list(Long appId) {
        return emiRepo.findByLoanApplicationIdOrderByInstallmentNoAsc(appId);
    }

    public List<EmiSchedule> generateSchedule(LoanApplication app) {
        // delete old schedule if exists
        List<EmiSchedule> old = emiRepo.findByLoanApplicationIdOrderByInstallmentNoAsc(app.getId());
        emiRepo.deleteAll(old);

        BigDecimal P = app.getDisbursedAmount(); // principal
        int n = app.getTenureMonths();
        BigDecimal annual = app.getAnnualInterestRate();

        // monthly rate r = annual/12/100
        BigDecimal r = annual.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

        // EMI formula
        BigDecimal onePlusR = BigDecimal.ONE.add(r);
        BigDecimal pow = onePlusR.pow(n);

        BigDecimal emi = P.multiply(r).multiply(pow)
                .divide(pow.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);

        BigDecimal balance = P.setScale(2, RoundingMode.HALF_UP);

        List<EmiSchedule> rows = new ArrayList<>();
        LocalDate start = app.getDisbursedDate();

        for (int i = 1; i <= n; i++) {
            BigDecimal interest = balance.multiply(r).setScale(2, RoundingMode.HALF_UP);
            BigDecimal principal = emi.subtract(interest).setScale(2, RoundingMode.HALF_UP);

            // last installment adjustment
            if (i == n) {
                principal = balance;
                emi = principal.add(interest).setScale(2, RoundingMode.HALF_UP);
            }

            balance = balance.subtract(principal).max(BigDecimal.ZERO).setScale(2, RoundingMode.HALF_UP);

            EmiSchedule e = new EmiSchedule();
            e.setLoanApplication(app);
            e.setInstallmentNo(i);
            e.setDueDate(start.plusMonths(i));
            e.setEmiAmount(emi);
            e.setPrincipalComponent(principal);
            e.setInterestComponent(interest);
            e.setStatus(EmiStatus.DUE);

            rows.add(e);
        }

        return emiRepo.saveAll(rows);
    }
}