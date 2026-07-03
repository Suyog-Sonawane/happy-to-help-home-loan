/*
 * package com.happytohelp.homeloan.service;
 * 
 * 
 * import com.happytohelp.homeloan.dto.DisburseRequest; import
 * com.happytohelp.homeloan.dto.SanctionRequest; import
 * com.happytohelp.homeloan.exception.BadRequestException; import
 * com.happytohelp.homeloan.exception.NotFoundException; import
 * com.happytohelp.homeloan.model.*; import
 * com.happytohelp.homeloan.repo.LoanApplicationRepository; import
 * com.happytohelp.homeloan.repo.UserRepository; import
 * org.springframework.stereotype.Service; import
 * org.springframework.transaction.annotation.Transactional;
 * 
 * import java.time.LocalDate; import java.time.LocalDateTime; import
 * java.util.List;
 * 
 * @Service public class AdminLoanService {
 * 
 * private final LoanApplicationRepository loanRepo; private final
 * UserRepository userRepo; private final PdfLetterService pdfLetterService;
 * private final EmailService emailService; private final EmiService emiService;
 * private final LedgerService ledgerService;
 * 
 * public AdminLoanService(LoanApplicationRepository loanRepo, UserRepository
 * userRepo, PdfLetterService pdfLetterService, EmailService emailService,
 * EmiService emiService, LedgerService ledgerService) { this.loanRepo =
 * loanRepo; this.userRepo = userRepo; this.pdfLetterService = pdfLetterService;
 * this.emailService = emailService; this.emiService = emiService;
 * this.ledgerService = ledgerService; }
 * 
 * public LoanApplication getOrThrow(Long appId) { return
 * loanRepo.findById(appId).orElseThrow(() -> new
 * NotFoundException("Application not found")); }
 * 
 * public List<LoanApplication> listByStatus(ApplicationStatus status) { if
 * (status == null) return loanRepo.findAll(); return
 * loanRepo.findByStatusOrderByIdDesc(status); }
 * 
 * @Transactional public LoanApplication underReview(Long appId) {
 * LoanApplication app = getOrThrow(appId); if (app.getStatus() !=
 * ApplicationStatus.SUBMITTED) { throw new
 * BadRequestException("Only SUBMITTED application can be moved to UNDER_REVIEW"
 * ); } app.setStatus(ApplicationStatus.UNDER_REVIEW);
 * app.setUpdatedAt(LocalDateTime.now()); return loanRepo.save(app); }
 * 
 * @Transactional public LoanApplication reject(Long appId, String reason) {
 * LoanApplication app = getOrThrow(appId); if (app.getStatus() ==
 * ApplicationStatus.DISBURSED) { throw new
 * BadRequestException("Cannot reject after disbursement"); }
 * app.setStatus(ApplicationStatus.REJECTED); app.setAdminRemarks(reason);
 * app.setUpdatedAt(LocalDateTime.now()); return loanRepo.save(app); }
 * 
 * @Transactional public LoanApplication sanction(Long appId, SanctionRequest
 * req) { LoanApplication app = getOrThrow(appId);
 * 
 * if (app.getStatus() != ApplicationStatus.SUBMITTED && app.getStatus() !=
 * ApplicationStatus.UNDER_REVIEW) { throw new
 * BadRequestException("Only SUBMITTED/UNDER_REVIEW can be sanctioned"); }
 * 
 * app.setSanctionedAmount(req.getSanctionedAmount());
 * app.setAnnualInterestRate(req.getAnnualInterestRate());
 * app.setTenureMonths(req.getTenureMonths());
 * app.setSanctionedDate(LocalDate.now());
 * app.setAdminRemarks(req.getRemarks());
 * app.setStatus(ApplicationStatus.SANCTIONED);
 * app.setUpdatedAt(LocalDateTime.now());
 * 
 * LoanApplication saved = loanRepo.save(app); User customer =
 * userRepo.findById(saved.getUser().getId()).orElseThrow();
 * 
 * byte[] pdf = pdfLetterService.buildSanctionLetter(saved, customer);
 * emailService.sendPdfAttachment( customer.getEmail(),
 * "Home Loan Sanction Letter - Application #" + saved.getId(), "Dear " +
 * customer.getName() +
 * ",\n\nPlease find attached your sanction letter.\n\nRegards,\nBank",
 * "SanctionLetter_App_" + saved.getId() + ".pdf", pdf );
 * 
 * return saved; }
 * 
 * @Transactional public LoanApplication disburse(Long appId, DisburseRequest
 * req) { LoanApplication app = getOrThrow(appId);
 * 
 * if (app.getStatus() != ApplicationStatus.SANCTIONED) { throw new
 * BadRequestException("Only SANCTIONED application can be disbursed"); } if
 * (app.getAnnualInterestRate() == null || app.getSanctionedAmount() == null) {
 * throw new BadRequestException("Sanction details missing"); }
 * 
 * app.setDisbursedAmount(req.getDisbursedAmount());
 * app.setDisbursedDate(req.getDisbursedDate() == null ? LocalDate.now() :
 * req.getDisbursedDate()); app.setStatus(ApplicationStatus.DISBURSED);
 * app.setUpdatedAt(LocalDateTime.now());
 * 
 * LoanApplication saved = loanRepo.save(app);
 * 
 * // Ledger opening entry ledgerService.addDisbursement(saved,
 * saved.getDisbursedAmount());
 * 
 * // Generate EMI schedule emiService.generateSchedule(saved);
 * 
 * // Email disbursement letter User customer =
 * userRepo.findById(saved.getUser().getId()).orElseThrow(); byte[] pdf =
 * pdfLetterService.buildDisbursementLetter(saved, customer);
 * 
 * emailService.sendPdfAttachment( customer.getEmail(),
 * "Home Loan Disbursement Letter - Application #" + saved.getId(), "Dear " +
 * customer.getName() +
 * ",\n\nPlease find attached your disbursement letter.\n\nRegards,\nBank",
 * "DisbursementLetter_App_" + saved.getId() + ".pdf", pdf );
 * 
 * return saved; } }
 */


package com.happytohelp.homeloan.service;

import com.happytohelp.homeloan.dto.DisburseRequest;
import com.happytohelp.homeloan.dto.SanctionRequest;
import com.happytohelp.homeloan.exception.BadRequestException;
import com.happytohelp.homeloan.exception.NotFoundException;
import com.happytohelp.homeloan.model.*;
import com.happytohelp.homeloan.repo.LoanApplicationRepository;
import com.happytohelp.homeloan.repo.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminLoanService {

    private final LoanApplicationRepository loanRepo;
    private final UserRepository userRepo;
    private final PdfLetterService pdfLetterService;
    private final EmailService emailService;
    private final EmiService emiService;
    private final LedgerService ledgerService;

    public AdminLoanService(LoanApplicationRepository loanRepo,
                            UserRepository userRepo,
                            PdfLetterService pdfLetterService,
                            EmailService emailService,
                            EmiService emiService,
                            LedgerService ledgerService) {
        this.loanRepo = loanRepo;
        this.userRepo = userRepo;
        this.pdfLetterService = pdfLetterService;
        this.emailService = emailService;
        this.emiService = emiService;
        this.ledgerService = ledgerService;
    }

    public LoanApplication getOrThrow(Long appId) {
        return loanRepo.findById(appId).orElseThrow(() -> new NotFoundException("Application not found"));
    }

    public List<LoanApplication> listByStatus(ApplicationStatus status) {
        if (status == null) return loanRepo.findAll();
        return loanRepo.findByStatusOrderByIdDesc(status);
    }

    @Transactional
    public LoanApplication underReview(Long appId) {
        LoanApplication app = getOrThrow(appId);
        if (app.getStatus() != ApplicationStatus.SUBMITTED) {
            throw new BadRequestException("Only SUBMITTED application can be moved to UNDER_REVIEW");
        }
        app.setStatus(ApplicationStatus.UNDER_REVIEW);
        app.setUpdatedAt(LocalDateTime.now());
        return loanRepo.save(app);
    }

    @Transactional
    public LoanApplication reject(Long appId, String reason) {
        LoanApplication app = getOrThrow(appId);
        if (app.getStatus() == ApplicationStatus.DISBURSED) {
            throw new BadRequestException("Cannot reject after disbursement");
        }
        app.setStatus(ApplicationStatus.REJECTED);
        app.setAdminRemarks(reason);
        app.setUpdatedAt(LocalDateTime.now());
        return loanRepo.save(app);
    }

    @Transactional
    public LoanApplication sanction(Long appId, SanctionRequest req) {
        LoanApplication app = getOrThrow(appId);

        if (app.getStatus() != ApplicationStatus.SUBMITTED &&
            app.getStatus() != ApplicationStatus.UNDER_REVIEW) {
            throw new BadRequestException("Only SUBMITTED/UNDER_REVIEW can be sanctioned");
        }

        app.setSanctionedAmount(req.getSanctionedAmount());
        app.setAnnualInterestRate(req.getAnnualInterestRate());
        app.setTenureMonths(req.getTenureMonths());
        app.setSanctionedDate(LocalDate.now());
        app.setAdminRemarks(req.getRemarks());
        app.setStatus(ApplicationStatus.SANCTIONED);
        app.setUpdatedAt(LocalDateTime.now());

        LoanApplication saved = loanRepo.save(app);
        User customer = userRepo.findById(saved.getUser().getId()).orElseThrow();

        // IMPORTANT: do not fail sanction if SMTP fails
        byte[] pdf = pdfLetterService.buildSanctionLetter(saved, customer);
        emailService.trySendPdfAttachment(
                customer.getEmail(),
                "Home Loan Sanction Letter - Application #" + saved.getId(),
                "Dear " + customer.getName() + ",\n\nPlease find attached your sanction letter.\n\nRegards,\nBank",
                "SanctionLetter_App_" + saved.getId() + ".pdf",
                pdf
        );

        return saved;
    }

    @Transactional
    public LoanApplication disburse(Long appId, DisburseRequest req) {
        LoanApplication app = getOrThrow(appId);

        if (app.getStatus() != ApplicationStatus.SANCTIONED) {
            throw new BadRequestException("Only SANCTIONED application can be disbursed");
        }
        if (app.getAnnualInterestRate() == null || app.getSanctionedAmount() == null) {
            throw new BadRequestException("Sanction details missing");
        }

        app.setDisbursedAmount(req.getDisbursedAmount());
        app.setDisbursedDate(req.getDisbursedDate() == null ? LocalDate.now() : req.getDisbursedDate());
        app.setStatus(ApplicationStatus.DISBURSED);
        app.setUpdatedAt(LocalDateTime.now());

        LoanApplication saved = loanRepo.save(app);

        // Ledger opening entry
        ledgerService.addDisbursement(saved, saved.getDisbursedAmount());

        // Generate EMI schedule
        emiService.generateSchedule(saved);

        // IMPORTANT: do not fail disburse if SMTP fails
        User customer = userRepo.findById(saved.getUser().getId()).orElseThrow();
        byte[] pdf = pdfLetterService.buildDisbursementLetter(saved, customer);

        emailService.trySendPdfAttachment(
                customer.getEmail(),
                "Home Loan Disbursement Letter - Application #" + saved.getId(),
                "Dear " + customer.getName() + ",\n\nPlease find attached your disbursement letter.\n\nRegards,\nBank",
                "DisbursementLetter_App_" + saved.getId() + ".pdf",
                pdf
        );

        return saved;
    }
}