package com.happytohelp.homeloan.service;



import com.happytohelp.homeloan.dto.PayEmiRequest;
import com.happytohelp.homeloan.exception.BadRequestException;
import com.happytohelp.homeloan.exception.NotFoundException;
import com.happytohelp.homeloan.model.*;
import com.happytohelp.homeloan.repo.EmiScheduleRepository;
import com.happytohelp.homeloan.repo.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class PaymentService {

    private final EmiScheduleRepository emiRepo;
    private final PaymentRepository paymentRepo;
    private final LedgerService ledgerService;

    public PaymentService(EmiScheduleRepository emiRepo,
                          PaymentRepository paymentRepo,
                          LedgerService ledgerService) {
        this.emiRepo = emiRepo;
        this.paymentRepo = paymentRepo;
        this.ledgerService = ledgerService;
    }

    @Transactional
    public EmiSchedule payEmi(LoanApplication app, Long emiId, PayEmiRequest req) {
        EmiSchedule emi = emiRepo.findByIdAndLoanApplicationId(emiId, app.getId())
                .orElseThrow(() -> new NotFoundException("EMI not found"));

        if (app.getStatus() != ApplicationStatus.DISBURSED) {
            throw new BadRequestException("EMI can be paid only after DISBURSED");
        }
        if (emi.getStatus() == EmiStatus.PAID) {
            throw new BadRequestException("EMI already paid");
        }
        if (req.getAmount() == null || req.getAmount().compareTo(emi.getEmiAmount()) != 0) {
            throw new BadRequestException("Amount must be exactly EMI amount: " + emi.getEmiAmount());
        }

        Payment p = new Payment();
        p.setLoanApplication(app);
        p.setAmount(req.getAmount());
        p.setMethod(req.getMethod());
        p.setTxnRef(req.getTxnRef());
        p = paymentRepo.save(p);

        emi.setPayment(p);
        emi.setStatus(EmiStatus.PAID);
        emi.setPaidDate(LocalDate.now());
        EmiSchedule saved = emiRepo.save(emi);

        ledgerService.addEmiPayment(app, emi.getInstallmentNo(),
                emi.getPrincipalComponent(), emi.getInterestComponent());

        return saved;
    }
}