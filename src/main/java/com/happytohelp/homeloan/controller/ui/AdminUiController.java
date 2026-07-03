/*
 * package com.happytohelp.homeloan.controller.ui;
 * 
 * 
 * 
 * import com.happytohelp.homeloan.dto.DisburseRequest; import
 * com.happytohelp.homeloan.dto.SanctionRequest; import
 * com.happytohelp.homeloan.model.ApplicationStatus; import
 * com.happytohelp.homeloan.model.LoanApplication; import
 * com.happytohelp.homeloan.service.AdminLoanService; import
 * com.happytohelp.homeloan.service.AuthService; import
 * com.happytohelp.homeloan.service.EmiService; import
 * com.happytohelp.homeloan.service.LedgerService; import
 * jakarta.servlet.http.HttpSession; import jakarta.validation.Valid; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.validation.BindingResult; import
 * org.springframework.web.bind.annotation.*; import
 * org.springframework.web.servlet.mvc.support.RedirectAttributes;
 * 
 * @Controller
 * 
 * @RequestMapping("/ui/admin") public class AdminUiController {
 * 
 * private final AuthService authService; private final AdminLoanService
 * adminLoanService; private final EmiService emiService; private final
 * LedgerService ledgerService;
 * 
 * public AdminUiController(AuthService authService, AdminLoanService
 * adminLoanService, EmiService emiService, LedgerService ledgerService) {
 * this.authService = authService; this.adminLoanService = adminLoanService;
 * this.emiService = emiService; this.ledgerService = ledgerService; }
 * 
 * @GetMapping("/dashboard") public String dashboard(@RequestParam(required =
 * false) ApplicationStatus status, HttpSession session, Model model) {
 * authService.requireAdmin(session);
 * 
 * model.addAttribute("status", status); model.addAttribute("allStatuses",
 * ApplicationStatus.values()); model.addAttribute("apps",
 * adminLoanService.listByStatus(status));
 * 
 * return "ui/admin/dashboard"; }
 * 
 * @GetMapping("/applications/{id}") public String detail(@PathVariable Long id,
 * HttpSession session, Model model) { authService.requireAdmin(session);
 * 
 * LoanApplication app = adminLoanService.getOrThrow(id);
 * 
 * model.addAttribute("app", app); model.addAttribute("sanctionReq", new
 * SanctionRequest()); model.addAttribute("disburseReq", new DisburseRequest());
 * model.addAttribute("ledger", ledgerService.getLedger(app.getId()));
 * model.addAttribute("emis", emiService.list(app.getId()));
 * 
 * return "ui/admin/app-detail"; }
 * 
 * @PostMapping("/applications/{id}/under-review") public String
 * underReview(@PathVariable Long id, HttpSession session, RedirectAttributes
 * ra) { authService.requireAdmin(session); adminLoanService.underReview(id);
 * ra.addFlashAttribute("msg", "Moved to UNDER_REVIEW"); return
 * "redirect:/ui/admin/applications/" + id; }
 * 
 * @PostMapping("/applications/{id}/reject") public String reject(@PathVariable
 * Long id,
 * 
 * @RequestParam String reason, HttpSession session, RedirectAttributes ra) {
 * authService.requireAdmin(session); adminLoanService.reject(id, reason);
 * ra.addFlashAttribute("msg", "Rejected"); return
 * "redirect:/ui/admin/applications/" + id; }
 * 
 * @PostMapping("/applications/{id}/sanction") public String
 * sanction(@PathVariable Long id,
 * 
 * @Valid @ModelAttribute("sanctionReq") SanctionRequest req, BindingResult br,
 * HttpSession session, RedirectAttributes ra, Model model) {
 * authService.requireAdmin(session);
 * 
 * if (br.hasErrors()) { LoanApplication app = adminLoanService.getOrThrow(id);
 * model.addAttribute("app", app); model.addAttribute("disburseReq", new
 * DisburseRequest()); model.addAttribute("ledger",
 * ledgerService.getLedger(app.getId())); model.addAttribute("emis",
 * emiService.list(app.getId())); return "ui/admin/app-detail"; }
 * 
 * adminLoanService.sanction(id, req); ra.addFlashAttribute("msg",
 * "Sanctioned and email sent"); return "redirect:/ui/admin/applications/" + id;
 * }
 * 
 * @PostMapping("/applications/{id}/disburse") public String
 * disburse(@PathVariable Long id,
 * 
 * @Valid @ModelAttribute("disburseReq") DisburseRequest req, BindingResult br,
 * HttpSession session, RedirectAttributes ra, Model model) {
 * authService.requireAdmin(session);
 * 
 * if (br.hasErrors()) { LoanApplication app = adminLoanService.getOrThrow(id);
 * model.addAttribute("app", app); model.addAttribute("sanctionReq", new
 * SanctionRequest()); model.addAttribute("ledger",
 * ledgerService.getLedger(app.getId())); model.addAttribute("emis",
 * emiService.list(app.getId())); return "ui/admin/app-detail"; }
 * 
 * adminLoanService.disburse(id, req); ra.addFlashAttribute("msg",
 * "Disbursed, EMI generated, email sent"); return
 * "redirect:/ui/admin/applications/" + id; } }
 */

/*
 * package com.happytohelp.homeloan.controller.ui;
 * 
 * import com.happytohelp.homeloan.dto.DisburseRequest; import
 * com.happytohelp.homeloan.dto.SanctionRequest; import
 * com.happytohelp.homeloan.model.*; import
 * com.happytohelp.homeloan.repo.LoanDocumentRepository; import
 * com.happytohelp.homeloan.service.AdminLoanService; import
 * com.happytohelp.homeloan.service.AuthService; import
 * com.happytohelp.homeloan.service.EmiService; import
 * com.happytohelp.homeloan.service.LedgerService; import
 * com.happytohelp.homeloan.service.PdfLetterService; import
 * jakarta.servlet.http.HttpSession; import jakarta.validation.Valid; import
 * org.springframework.core.io.FileSystemResource; import
 * org.springframework.http.HttpHeaders; import
 * org.springframework.http.MediaType; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.validation.BindingResult; import
 * org.springframework.web.bind.annotation.*; import
 * org.springframework.web.servlet.mvc.support.RedirectAttributes;
 * 
 * import java.io.File; import java.nio.file.Files;
 * 
 * @Controller
 * 
 * @RequestMapping("/ui/admin") public class AdminUiController {
 * 
 * private final AuthService authService; private final AdminLoanService
 * adminLoanService; private final EmiService emiService; private final
 * LedgerService ledgerService; private final LoanDocumentRepository docRepo;
 * private final PdfLetterService pdfLetterService;
 * 
 * public AdminUiController(AuthService authService, AdminLoanService
 * adminLoanService, EmiService emiService, LedgerService ledgerService,
 * LoanDocumentRepository docRepo, PdfLetterService pdfLetterService) {
 * this.authService = authService; this.adminLoanService = adminLoanService;
 * this.emiService = emiService; this.ledgerService = ledgerService;
 * this.docRepo = docRepo; this.pdfLetterService = pdfLetterService; }
 * 
 * @GetMapping("/dashboard") public String dashboard(@RequestParam(required =
 * false) ApplicationStatus status, HttpSession session, Model model) {
 * authService.requireAdmin(session);
 * 
 * model.addAttribute("status", status); model.addAttribute("allStatuses",
 * ApplicationStatus.values()); model.addAttribute("apps",
 * adminLoanService.listByStatus(status));
 * 
 * return "ui/admin/dashboard"; }
 * 
 * @GetMapping("/applications/{id}") public String detail(@PathVariable Long id,
 * HttpSession session, Model model) { authService.requireAdmin(session);
 * 
 * LoanApplication app = adminLoanService.getOrThrow(id);
 * 
 * model.addAttribute("app", app); model.addAttribute("sanctionReq", new
 * SanctionRequest()); model.addAttribute("disburseReq", new DisburseRequest());
 * model.addAttribute("ledger", ledgerService.getLedger(app.getId()));
 * model.addAttribute("emis", emiService.list(app.getId()));
 * model.addAttribute("documents",
 * docRepo.findByLoanApplicationIdOrderByIdDesc(app.getId()));
 * 
 * return "ui/admin/app-detail"; }
 * 
 * @PostMapping("/applications/{id}/under-review") public String
 * underReview(@PathVariable Long id, HttpSession session, RedirectAttributes
 * ra) { authService.requireAdmin(session); adminLoanService.underReview(id);
 * ra.addFlashAttribute("msg", "Moved to UNDER_REVIEW"); return
 * "redirect:/ui/admin/applications/" + id; }
 * 
 * @PostMapping("/applications/{id}/reject") public String reject(@PathVariable
 * Long id,
 * 
 * @RequestParam String reason, HttpSession session, RedirectAttributes ra) {
 * authService.requireAdmin(session); adminLoanService.reject(id, reason);
 * ra.addFlashAttribute("msg", "Rejected"); return
 * "redirect:/ui/admin/applications/" + id; }
 * 
 * @PostMapping("/applications/{id}/sanction") public String
 * sanction(@PathVariable Long id,
 * 
 * @Valid @ModelAttribute("sanctionReq") SanctionRequest req, BindingResult br,
 * HttpSession session, RedirectAttributes ra, Model model) {
 * authService.requireAdmin(session);
 * 
 * if (br.hasErrors()) { LoanApplication app = adminLoanService.getOrThrow(id);
 * model.addAttribute("app", app); model.addAttribute("disburseReq", new
 * DisburseRequest()); model.addAttribute("ledger",
 * ledgerService.getLedger(app.getId())); model.addAttribute("emis",
 * emiService.list(app.getId())); model.addAttribute("documents",
 * docRepo.findByLoanApplicationIdOrderByIdDesc(app.getId())); return
 * "ui/admin/app-detail"; }
 * 
 * adminLoanService.sanction(id, req); ra.addFlashAttribute("msg",
 * "Sanctioned and email sent"); return "redirect:/ui/admin/applications/" + id;
 * }
 * 
 * @PostMapping("/applications/{id}/disburse") public String
 * disburse(@PathVariable Long id,
 * 
 * @Valid @ModelAttribute("disburseReq") DisburseRequest req, BindingResult br,
 * HttpSession session, RedirectAttributes ra, Model model) {
 * authService.requireAdmin(session);
 * 
 * if (br.hasErrors()) { LoanApplication app = adminLoanService.getOrThrow(id);
 * model.addAttribute("app", app); model.addAttribute("sanctionReq", new
 * SanctionRequest()); model.addAttribute("ledger",
 * ledgerService.getLedger(app.getId())); model.addAttribute("emis",
 * emiService.list(app.getId())); model.addAttribute("documents",
 * docRepo.findByLoanApplicationIdOrderByIdDesc(app.getId())); return
 * "ui/admin/app-detail"; }
 * 
 * adminLoanService.disburse(id, req); ra.addFlashAttribute("msg",
 * "Disbursed, EMI generated, email sent"); return
 * "redirect:/ui/admin/applications/" + id; }
 * 
 * // ADMIN: download any uploaded document
 * 
 * @GetMapping("/applications/{id}/documents/{docId}/download") public
 * ResponseEntity<FileSystemResource> downloadDoc(@PathVariable Long id,
 * 
 * @PathVariable Long docId, HttpSession session) throws Exception {
 * authService.requireAdmin(session); LoanApplication app =
 * adminLoanService.getOrThrow(id);
 * 
 * LoanDocument doc = docRepo.findByIdAndLoanApplicationId(docId, app.getId())
 * .orElseThrow(() -> new RuntimeException("Document not found"));
 * 
 * File f = new File(doc.getStoredPath()); if (!f.exists()) throw new
 * RuntimeException("File missing on server");
 * 
 * String contentType = Files.probeContentType(f.toPath()); if (contentType ==
 * null) contentType = "application/octet-stream";
 * 
 * return ResponseEntity.ok()
 * .contentType(MediaType.parseMediaType(contentType))
 * .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
 * doc.getOriginalFileName() + "\"") .body(new FileSystemResource(f)); }
 * 
 * // ADMIN: sanction pdf download
 * 
 * @GetMapping("/applications/{id}/sanction-letter.pdf") public
 * ResponseEntity<byte[]> sanctionPdf(@PathVariable Long id, HttpSession
 * session) { authService.requireAdmin(session); LoanApplication app =
 * adminLoanService.getOrThrow(id);
 * 
 * if (!(app.getStatus() == ApplicationStatus.SANCTIONED || app.getStatus() ==
 * ApplicationStatus.DISBURSED)) { throw new
 * RuntimeException("Sanction letter available only after SANCTIONED"); }
 * 
 * User customer = app.getUser(); byte[] pdf =
 * pdfLetterService.buildSanctionLetter(app, customer);
 * 
 * return ResponseEntity.ok() .contentType(MediaType.APPLICATION_PDF)
 * .header(HttpHeaders.CONTENT_DISPOSITION,
 * "inline; filename=SanctionLetter_App_" + app.getId() + ".pdf") .body(pdf); }
 * 
 * // ADMIN: disbursement pdf download
 * 
 * @GetMapping("/applications/{id}/disbursement-letter.pdf") public
 * ResponseEntity<byte[]> disbursementPdf(@PathVariable Long id, HttpSession
 * session) { authService.requireAdmin(session); LoanApplication app =
 * adminLoanService.getOrThrow(id);
 * 
 * if (app.getStatus() != ApplicationStatus.DISBURSED) { throw new
 * RuntimeException("Disbursement letter available only after DISBURSED"); }
 * 
 * User customer = app.getUser(); byte[] pdf =
 * pdfLetterService.buildDisbursementLetter(app, customer);
 * 
 * return ResponseEntity.ok() .contentType(MediaType.APPLICATION_PDF)
 * .header(HttpHeaders.CONTENT_DISPOSITION,
 * "inline; filename=DisbursementLetter_App_" + app.getId() + ".pdf")
 * .body(pdf); } }
 */

/*
 * package com.happytohelp.homeloan.controller.ui;
 * 
 * import com.happytohelp.homeloan.repo.EmiScheduleRepository; import
 * com.happytohelp.homeloan.repo.LoanApplicationRepository; import
 * com.happytohelp.homeloan.service.DashboardService;
 * 
 * import java.math.BigDecimal; import java.util.List; import
 * com.happytohelp.homeloan.dto.DisburseRequest; import
 * com.happytohelp.homeloan.dto.SanctionRequest; import
 * com.happytohelp.homeloan.model.*; import
 * com.happytohelp.homeloan.repo.LoanDocumentRepository; import
 * com.happytohelp.homeloan.service.*; import jakarta.servlet.http.HttpSession;
 * import jakarta.validation.Valid; import
 * org.springframework.core.io.FileSystemResource; import
 * org.springframework.http.HttpHeaders; import
 * org.springframework.http.MediaType; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.validation.BindingResult; import
 * org.springframework.web.bind.annotation.*; import
 * org.springframework.web.servlet.mvc.support.RedirectAttributes;
 * 
 * import java.io.File; import java.nio.file.Files;
 * 
 * @Controller
 * 
 * @RequestMapping("/ui/admin") public class AdminUiController {
 * 
 * private final LoanApplicationRepository loanRepo; private final
 * EmiScheduleRepository emiRepo; private final DashboardService
 * dashboardService;
 * 
 * private final AuthService authService; private final AdminLoanService
 * adminLoanService; private final EmiService emiService; private final
 * LedgerService ledgerService; private final LoanDocumentRepository docRepo;
 * private final PdfLetterService pdfLetterService;
 * 
 * public AdminUiController(AuthService authService, AdminLoanService
 * adminLoanService, EmiService emiService, LedgerService ledgerService,
 * LoanDocumentRepository docRepo, PdfLetterService pdfLetterService) {
 * this.authService = authService; this.adminLoanService = adminLoanService;
 * this.emiService = emiService; this.ledgerService = ledgerService;
 * this.docRepo = docRepo; this.pdfLetterService = pdfLetterService; }
 * 
 * 
 * @GetMapping("/dashboard") public String dashboard(@RequestParam(required =
 * false) ApplicationStatus status, HttpSession session, Model model) {
 * authService.requireAdmin(session);
 * 
 * model.addAttribute("status", status); model.addAttribute("allStatuses",
 * ApplicationStatus.values()); model.addAttribute("apps",
 * adminLoanService.listByStatus(status)); return "ui/admin/dashboard"; }
 * 
 * 
 * 
 * @GetMapping("/dashboard") public String dashboard(@RequestParam(required =
 * false) ApplicationStatus status, HttpSession session, Model model) {
 * authService.requireAdmin(session);
 * 
 * model.addAttribute("status", status); model.addAttribute("allStatuses",
 * ApplicationStatus.values()); model.addAttribute("apps",
 * adminLoanService.listByStatus(status));
 * 
 * // KPIs long draft = loanRepo.countByStatus(ApplicationStatus.DRAFT); long
 * submitted = loanRepo.countByStatus(ApplicationStatus.SUBMITTED); long review
 * = loanRepo.countByStatus(ApplicationStatus.UNDER_REVIEW); long sanctioned =
 * loanRepo.countByStatus(ApplicationStatus.SANCTIONED); long disbursed =
 * loanRepo.countByStatus(ApplicationStatus.DISBURSED); long rejected =
 * loanRepo.countByStatus(ApplicationStatus.REJECTED);
 * 
 * BigDecimal totalDisbursed = loanRepo.sumTotalDisbursed(); BigDecimal
 * totalOutstanding = dashboardService.totalOutstandingAllDisbursed(); long
 * dueEmis = emiRepo.countDueEmisForDisbursedLoans();
 * 
 * model.addAttribute("kpiDraft", draft); model.addAttribute("kpiSubmitted",
 * submitted); model.addAttribute("kpiReview", review);
 * model.addAttribute("kpiSanctioned", sanctioned);
 * model.addAttribute("kpiDisbursed", disbursed);
 * model.addAttribute("kpiRejected", rejected);
 * 
 * model.addAttribute("kpiTotalDisbursed", totalDisbursed);
 * model.addAttribute("kpiTotalOutstanding", totalOutstanding);
 * model.addAttribute("kpiDueEmis", dueEmis);
 * 
 * // Chart data model.addAttribute("chartLabels",
 * List.of("DRAFT","SUBMITTED","UNDER_REVIEW","SANCTIONED","DISBURSED",
 * "REJECTED")); model.addAttribute("chartCounts", List.of(draft, submitted,
 * review, sanctioned, disbursed, rejected));
 * 
 * return "ui/admin/dashboard"; }
 * 
 * 
 * 
 * @GetMapping("/applications/{id}") public String detail(@PathVariable Long id,
 * HttpSession session, Model model) { authService.requireAdmin(session);
 * 
 * LoanApplication app = adminLoanService.getOrThrow(id);
 * 
 * model.addAttribute("app", app); model.addAttribute("sanctionReq", new
 * SanctionRequest()); model.addAttribute("disburseReq", new DisburseRequest());
 * model.addAttribute("ledger", ledgerService.getLedger(app.getId()));
 * model.addAttribute("emis", emiService.list(app.getId()));
 * model.addAttribute("documents",
 * docRepo.findByLoanApplicationIdOrderByIdDesc(app.getId())); return
 * "ui/admin/app-detail"; }
 * 
 * @PostMapping("/applications/{id}/under-review") public String
 * underReview(@PathVariable Long id, HttpSession session, RedirectAttributes
 * ra) { authService.requireAdmin(session); adminLoanService.underReview(id);
 * ra.addFlashAttribute("msg", "Moved to UNDER_REVIEW"); return
 * "redirect:/ui/admin/applications/" + id; }
 * 
 * @PostMapping("/applications/{id}/reject") public String reject(@PathVariable
 * Long id,
 * 
 * @RequestParam String reason, HttpSession session, RedirectAttributes ra) {
 * authService.requireAdmin(session); adminLoanService.reject(id, reason);
 * ra.addFlashAttribute("msg", "Rejected"); return
 * "redirect:/ui/admin/applications/" + id; }
 * 
 * @PostMapping("/applications/{id}/sanction") public String
 * sanction(@PathVariable Long id,
 * 
 * @Valid @ModelAttribute("sanctionReq") SanctionRequest req, BindingResult br,
 * HttpSession session, RedirectAttributes ra, Model model) {
 * authService.requireAdmin(session);
 * 
 * if (br.hasErrors()) { LoanApplication app = adminLoanService.getOrThrow(id);
 * model.addAttribute("app", app); model.addAttribute("disburseReq", new
 * DisburseRequest()); model.addAttribute("ledger",
 * ledgerService.getLedger(app.getId())); model.addAttribute("emis",
 * emiService.list(app.getId())); model.addAttribute("documents",
 * docRepo.findByLoanApplicationIdOrderByIdDesc(app.getId())); return
 * "ui/admin/app-detail"; }
 * 
 * adminLoanService.sanction(id, req); ra.addFlashAttribute("msg",
 * "Sanctioned and email sent"); return "redirect:/ui/admin/applications/" + id;
 * }
 * 
 * @PostMapping("/applications/{id}/disburse") public String
 * disburse(@PathVariable Long id,
 * 
 * @Valid @ModelAttribute("disburseReq") DisburseRequest req, BindingResult br,
 * HttpSession session, RedirectAttributes ra, Model model) {
 * authService.requireAdmin(session);
 * 
 * if (br.hasErrors()) { LoanApplication app = adminLoanService.getOrThrow(id);
 * model.addAttribute("app", app); model.addAttribute("sanctionReq", new
 * SanctionRequest()); model.addAttribute("ledger",
 * ledgerService.getLedger(app.getId())); model.addAttribute("emis",
 * emiService.list(app.getId())); model.addAttribute("documents",
 * docRepo.findByLoanApplicationIdOrderByIdDesc(app.getId())); return
 * "ui/admin/app-detail"; }
 * 
 * adminLoanService.disburse(id, req); ra.addFlashAttribute("msg",
 * "Disbursed, EMI generated, email sent"); return
 * "redirect:/ui/admin/applications/" + id; }
 * 
 * // ADMIN: download uploaded document
 * 
 * @GetMapping("/applications/{id}/documents/{docId}/download") public
 * ResponseEntity<FileSystemResource> downloadDoc(@PathVariable Long id,
 * 
 * @PathVariable Long docId, HttpSession session) throws Exception {
 * authService.requireAdmin(session); LoanApplication app =
 * adminLoanService.getOrThrow(id);
 * 
 * LoanDocument doc = docRepo.findByIdAndLoanApplicationId(docId, app.getId())
 * .orElseThrow(() -> new RuntimeException("Document not found"));
 * 
 * File f = new File(doc.getStoredPath()); if (!f.exists()) throw new
 * RuntimeException("File missing on server");
 * 
 * String contentType = Files.probeContentType(f.toPath()); if (contentType ==
 * null) contentType = "application/octet-stream";
 * 
 * return ResponseEntity.ok()
 * .contentType(MediaType.parseMediaType(contentType))
 * .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
 * doc.getOriginalFileName() + "\"") .body(new FileSystemResource(f)); }
 * 
 * // ADMIN: sanction letter download
 * 
 * @GetMapping("/applications/{id}/sanction-letter.pdf") public
 * ResponseEntity<byte[]> sanctionPdf(@PathVariable Long id, HttpSession
 * session) { authService.requireAdmin(session); LoanApplication app =
 * adminLoanService.getOrThrow(id);
 * 
 * if (!(app.getStatus() == ApplicationStatus.SANCTIONED || app.getStatus() ==
 * ApplicationStatus.DISBURSED)) { throw new
 * RuntimeException("Sanction letter available only after SANCTIONED"); }
 * 
 * byte[] pdf = pdfLetterService.buildSanctionLetter(app, app.getUser()); return
 * ResponseEntity.ok() .contentType(MediaType.APPLICATION_PDF)
 * .header(HttpHeaders.CONTENT_DISPOSITION,
 * "inline; filename=SanctionLetter_App_" + app.getId() + ".pdf") .body(pdf); }
 * 
 * // ADMIN: disbursement letter download
 * 
 * @GetMapping("/applications/{id}/disbursement-letter.pdf") public
 * ResponseEntity<byte[]> disbursementPdf(@PathVariable Long id, HttpSession
 * session) { authService.requireAdmin(session); LoanApplication app =
 * adminLoanService.getOrThrow(id);
 * 
 * if (app.getStatus() != ApplicationStatus.DISBURSED) { throw new
 * RuntimeException("Disbursement letter available only after DISBURSED"); }
 * 
 * byte[] pdf = pdfLetterService.buildDisbursementLetter(app, app.getUser());
 * return ResponseEntity.ok() .contentType(MediaType.APPLICATION_PDF)
 * .header(HttpHeaders.CONTENT_DISPOSITION,
 * "inline; filename=DisbursementLetter_App_" + app.getId() + ".pdf")
 * .body(pdf); } }
 */



package com.happytohelp.homeloan.controller.ui;

import com.happytohelp.homeloan.dto.DisburseRequest;
import com.happytohelp.homeloan.dto.SanctionRequest;
import com.happytohelp.homeloan.model.*;
import com.happytohelp.homeloan.repo.EmiScheduleRepository;
import com.happytohelp.homeloan.repo.LoanApplicationRepository;
import com.happytohelp.homeloan.repo.LoanDocumentRepository;
import com.happytohelp.homeloan.service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.List;

@Controller
@RequestMapping("/ui/admin")
public class AdminUiController {

    private final AuthService authService;
    private final AdminLoanService adminLoanService;
    private final EmiService emiService;
    private final LedgerService ledgerService;
    private final LoanDocumentRepository docRepo;
    private final PdfLetterService pdfLetterService;

    // NEW for dashboard KPIs/Chart
    private final LoanApplicationRepository loanRepo;
    private final EmiScheduleRepository emiRepo;
    private final DashboardService dashboardService;

    public AdminUiController(AuthService authService,
                             AdminLoanService adminLoanService,
                             EmiService emiService,
                             LedgerService ledgerService,
                             LoanDocumentRepository docRepo,
                             PdfLetterService pdfLetterService,
                             LoanApplicationRepository loanRepo,
                             EmiScheduleRepository emiRepo,
                             DashboardService dashboardService) {

        this.authService = authService;
        this.adminLoanService = adminLoanService;
        this.emiService = emiService;
        this.ledgerService = ledgerService;
        this.docRepo = docRepo;
        this.pdfLetterService = pdfLetterService;

        this.loanRepo = loanRepo;
        this.emiRepo = emiRepo;
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(required = false) ApplicationStatus status,
                            HttpSession session, Model model) {
        authService.requireAdmin(session);

        model.addAttribute("status", status);
        model.addAttribute("allStatuses", ApplicationStatus.values());
        model.addAttribute("apps", adminLoanService.listByStatus(status));

        // KPIs
        long draft = loanRepo.countByStatus(ApplicationStatus.DRAFT);
        long submitted = loanRepo.countByStatus(ApplicationStatus.SUBMITTED);
        long review = loanRepo.countByStatus(ApplicationStatus.UNDER_REVIEW);
        long sanctioned = loanRepo.countByStatus(ApplicationStatus.SANCTIONED);
        long disbursed = loanRepo.countByStatus(ApplicationStatus.DISBURSED);
        long rejected = loanRepo.countByStatus(ApplicationStatus.REJECTED);

        BigDecimal totalDisbursed = loanRepo.sumTotalDisbursed();
        BigDecimal totalOutstanding = dashboardService.totalOutstandingAllDisbursed();
        long dueEmis = emiRepo.countDueEmisForDisbursedLoans();

        model.addAttribute("kpiDraft", draft);
        model.addAttribute("kpiSubmitted", submitted);
        model.addAttribute("kpiReview", review);
        model.addAttribute("kpiSanctioned", sanctioned);
        model.addAttribute("kpiDisbursed", disbursed);
        model.addAttribute("kpiRejected", rejected);

        model.addAttribute("kpiTotalDisbursed", totalDisbursed);
        model.addAttribute("kpiTotalOutstanding", totalOutstanding);
        model.addAttribute("kpiDueEmis", dueEmis);

        // Chart data
        model.addAttribute("chartLabels",
                List.of("DRAFT", "SUBMITTED", "UNDER_REVIEW", "SANCTIONED", "DISBURSED", "REJECTED"));
        model.addAttribute("chartCounts",
                List.of(draft, submitted, review, sanctioned, disbursed, rejected));

        return "ui/admin/dashboard";
    }

    @GetMapping("/applications/{id}")
    public String detail(@PathVariable Long id, HttpSession session, Model model) {
        authService.requireAdmin(session);

        LoanApplication app = adminLoanService.getOrThrow(id);

        model.addAttribute("app", app);
        model.addAttribute("sanctionReq", new SanctionRequest());
        model.addAttribute("disburseReq", new DisburseRequest());
        model.addAttribute("ledger", ledgerService.getLedger(app.getId()));
        model.addAttribute("emis", emiService.list(app.getId()));
        model.addAttribute("documents", docRepo.findByLoanApplicationIdOrderByIdDesc(app.getId()));
        return "ui/admin/app-detail";
    }

    @PostMapping("/applications/{id}/under-review")
    public String underReview(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
        authService.requireAdmin(session);
        adminLoanService.underReview(id);
        ra.addFlashAttribute("msg", "Moved to UNDER_REVIEW");
        return "redirect:/ui/admin/applications/" + id;
    }

    @PostMapping("/applications/{id}/reject")
    public String reject(@PathVariable Long id,
                         @RequestParam String reason,
                         HttpSession session,
                         RedirectAttributes ra) {
        authService.requireAdmin(session);
        adminLoanService.reject(id, reason);
        ra.addFlashAttribute("msg", "Rejected");
        return "redirect:/ui/admin/applications/" + id;
    }

    @PostMapping("/applications/{id}/sanction")
    public String sanction(@PathVariable Long id,
                           @Valid @ModelAttribute("sanctionReq") SanctionRequest req,
                           BindingResult br,
                           HttpSession session,
                           RedirectAttributes ra,
                           Model model) {
        authService.requireAdmin(session);

        if (br.hasErrors()) {
            LoanApplication app = adminLoanService.getOrThrow(id);
            model.addAttribute("app", app);
            model.addAttribute("disburseReq", new DisburseRequest());
            model.addAttribute("ledger", ledgerService.getLedger(app.getId()));
            model.addAttribute("emis", emiService.list(app.getId()));
            model.addAttribute("documents", docRepo.findByLoanApplicationIdOrderByIdDesc(app.getId()));
            return "ui/admin/app-detail";
        }

        adminLoanService.sanction(id, req);
        ra.addFlashAttribute("msg", "Sanctioned and email sent");
        return "redirect:/ui/admin/applications/" + id;
    }

    @PostMapping("/applications/{id}/disburse")
    public String disburse(@PathVariable Long id,
                           @Valid @ModelAttribute("disburseReq") DisburseRequest req,
                           BindingResult br,
                           HttpSession session,
                           RedirectAttributes ra,
                           Model model) {
        authService.requireAdmin(session);

        if (br.hasErrors()) {
            LoanApplication app = adminLoanService.getOrThrow(id);
            model.addAttribute("app", app);
            model.addAttribute("sanctionReq", new SanctionRequest());
            model.addAttribute("ledger", ledgerService.getLedger(app.getId()));
            model.addAttribute("emis", emiService.list(app.getId()));
            model.addAttribute("documents", docRepo.findByLoanApplicationIdOrderByIdDesc(app.getId()));
            return "ui/admin/app-detail";
        }

        adminLoanService.disburse(id, req);
        ra.addFlashAttribute("msg", "Disbursed, EMI generated, email sent");
        return "redirect:/ui/admin/applications/" + id;
    }

    @GetMapping("/applications/{id}/documents/{docId}/download")
    public ResponseEntity<FileSystemResource> downloadDoc(@PathVariable Long id,
                                                         @PathVariable Long docId,
                                                         HttpSession session) throws Exception {
        authService.requireAdmin(session);
        LoanApplication app = adminLoanService.getOrThrow(id);

        LoanDocument doc = docRepo.findByIdAndLoanApplicationId(docId, app.getId())
                .orElseThrow(() -> new RuntimeException("Document not found"));

        File f = new File(doc.getStoredPath());
        if (!f.exists()) throw new RuntimeException("File missing on server");

        String contentType = Files.probeContentType(f.toPath());
        if (contentType == null) contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + doc.getOriginalFileName() + "\"")
                .body(new FileSystemResource(f));
    }

    @GetMapping("/applications/{id}/sanction-letter.pdf")
    public ResponseEntity<byte[]> sanctionPdf(@PathVariable Long id, HttpSession session) {
        authService.requireAdmin(session);
        LoanApplication app = adminLoanService.getOrThrow(id);

        if (!(app.getStatus() == ApplicationStatus.SANCTIONED || app.getStatus() == ApplicationStatus.DISBURSED)) {
            throw new RuntimeException("Sanction letter available only after SANCTIONED");
        }

        byte[] pdf = pdfLetterService.buildSanctionLetter(app, app.getUser());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=SanctionLetter_App_" + app.getId() + ".pdf")
                .body(pdf);
    }

    @GetMapping("/applications/{id}/disbursement-letter.pdf")
    public ResponseEntity<byte[]> disbursementPdf(@PathVariable Long id, HttpSession session) {
        authService.requireAdmin(session);
        LoanApplication app = adminLoanService.getOrThrow(id);

        if (app.getStatus() != ApplicationStatus.DISBURSED) {
            throw new RuntimeException("Disbursement letter available only after DISBURSED");
        }

        byte[] pdf = pdfLetterService.buildDisbursementLetter(app, app.getUser());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=DisbursementLetter_App_" + app.getId() + ".pdf")
                .body(pdf);
    }
}