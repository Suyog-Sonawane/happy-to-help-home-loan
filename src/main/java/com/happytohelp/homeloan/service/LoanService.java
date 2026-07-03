package com.happytohelp.homeloan.service;


import com.happytohelp.homeloan.dto.LoanApplicationCreateRequest;
import com.happytohelp.homeloan.exception.BadRequestException;
import com.happytohelp.homeloan.exception.NotFoundException;
import com.happytohelp.homeloan.model.ApplicationStatus;
import com.happytohelp.homeloan.model.LoanApplication;
import com.happytohelp.homeloan.model.User;
import com.happytohelp.homeloan.repo.LoanApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoanService {

    private final LoanApplicationRepository loanRepo;

    public LoanService(LoanApplicationRepository loanRepo) {
        this.loanRepo = loanRepo;
    }

    public LoanApplication create(User user, LoanApplicationCreateRequest req) {
        LoanApplication app = new LoanApplication();
        app.setUser(user);
        app.setRequestedAmount(req.getRequestedAmount());
        app.setTenureMonths(req.getTenureMonths());
        app.setPropertyValue(req.getPropertyValue());
        app.setPropertyAddress(req.getPropertyAddress());
        app.setMonthlyIncome(req.getMonthlyIncome());
        app.setExistingEmi(req.getExistingEmi());
        return loanRepo.save(app);
    }

    public List<LoanApplication> myApps(Long userId) {
        return loanRepo.findByUserIdOrderByIdDesc(userId);
    }

    public LoanApplication myAppOrThrow(Long appId, Long userId) {
        return loanRepo.findByIdAndUserId(appId, userId)
                .orElseThrow(() -> new NotFoundException("Application not found"));
    }

    @Transactional
    public LoanApplication submit(LoanApplication app) {
        if (app.getStatus() != ApplicationStatus.DRAFT) {
            throw new BadRequestException("Only DRAFT application can be submitted");
        }

        // Rule 1: LTV <= 80%
        BigDecimal ltvMax = app.getPropertyValue().multiply(BigDecimal.valueOf(0.80));
        if (app.getRequestedAmount().compareTo(ltvMax) > 0) {
            throw new BadRequestException("Requested amount exceeds 80% of property value");
        }

        // Rule 2: income check (simple)
        if (app.getExistingEmi().compareTo(app.getMonthlyIncome()) > 0) {
            throw new BadRequestException("Existing EMI cannot exceed monthly income");
        }

        app.setStatus(ApplicationStatus.SUBMITTED);
        app.setUpdatedAt(LocalDateTime.now());
        return loanRepo.save(app);
    }
}