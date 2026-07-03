/*
 * package com.happytohelp.homeloan.controller.ui;
 * 
 * import com.happytohelp.homeloan.model.LoanDocument; import
 * com.happytohelp.homeloan.repo.EmiScheduleRepository; import
 * com.happytohelp.homeloan.service.PdfLetterService; import
 * org.springframework.core.io.FileSystemResource; import
 * org.springframework.http.HttpHeaders; import
 * org.springframework.http.MediaType; import
 * org.springframework.http.ResponseEntity; import java.io.File; import
 * java.nio.file.Files;
 * 
 * import com.happytohelp.homeloan.dto.LoanApplicationCreateRequest; import
 * com.happytohelp.homeloan.dto.PayEmiRequest; import
 * com.happytohelp.homeloan.model.DocumentType; import
 * com.happytohelp.homeloan.model.LoanApplication; import
 * com.happytohelp.homeloan.model.User; import
 * com.happytohelp.homeloan.repo.LoanDocumentRepository; import
 * com.happytohelp.homeloan.service.*; import jakarta.servlet.http.HttpSession;
 * import jakarta.validation.Valid; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.validation.BindingResult; import
 * org.springframework.web.bind.annotation.*; import
 * org.springframework.web.multipart.MultipartFile; import
 * org.springframework.web.servlet.mvc.support.RedirectAttributes;
 * 
 * @Controller
 * 
 * @RequestMapping("/ui/customer") public class CustomerUiController {
 * 
 * 
 * 
 * private final AuthService authService; private final LoanService loanService;
 * private final LoanDocumentRepository docRepo; private final DocumentService
 * documentService; private final EmiService emiService; private final
 * LedgerService ledgerService; private final PaymentService paymentService;
 * 
 * 
 * 
 * // LoanDocumentRepository docRepo
 * 
 * public CustomerUiController(AuthService authService, LoanService loanService,
 * LoanDocumentRepository docRepo, DocumentService documentService, EmiService
 * emiService, LedgerService ledgerService, PaymentService paymentService) {
 * this.authService = authService; this.loanService = loanService; this.docRepo
 * = docRepo; this.documentService = documentService; this.emiService =
 * emiService; this.ledgerService = ledgerService; this.paymentService =
 * paymentService; }
 * 
 * @GetMapping("/dashboard") public String dashboard(HttpSession session, Model
 * model) { User me = authService.requireLogin(session);
 * model.addAttribute("apps", loanService.myApps(me.getId())); return
 * "ui/customer/dashboard"; }
 * 
 * @GetMapping("/applications/new") public String newApplication(Model model) {
 * model.addAttribute("req", new LoanApplicationCreateRequest()); return
 * "ui/customer/app-form"; }
 * 
 * @PostMapping("/applications") public String
 * create(@Valid @ModelAttribute("req") LoanApplicationCreateRequest req,
 * BindingResult br, HttpSession session, RedirectAttributes ra) { if
 * (br.hasErrors()) return "ui/customer/app-form";
 * 
 * User me = authService.requireLogin(session); LoanApplication app =
 * loanService.create(me, req); ra.addFlashAttribute("msg",
 * "Application created: #" + app.getId()); return
 * "redirect:/ui/customer/applications/" + app.getId(); }
 * 
 * @GetMapping("/applications/{id}") public String appDetail(@PathVariable Long
 * id, HttpSession session, Model model) { User me =
 * authService.requireLogin(session); LoanApplication app =
 * loanService.myAppOrThrow(id, me.getId());
 * 
 * model.addAttribute("app", app); model.addAttribute("documents",
 * docRepo.findByLoanApplicationIdOrderByIdDesc(app.getId()));
 * model.addAttribute("docTypes", DocumentType.values());
 * 
 * return "ui/customer/app-detail"; }
 * 
 * @PostMapping("/applications/{id}/submit") public String submit(@PathVariable
 * Long id, HttpSession session, RedirectAttributes ra) { User me =
 * authService.requireLogin(session); LoanApplication app =
 * loanService.myAppOrThrow(id, me.getId()); loanService.submit(app);
 * 
 * ra.addFlashAttribute("msg", "Application submitted"); return
 * "redirect:/ui/customer/applications/" + id; }
 * 
 * @PostMapping("/applications/{id}/documents") public String
 * upload(@PathVariable Long id,
 * 
 * @RequestParam DocumentType type,
 * 
 * @RequestParam("file") MultipartFile file, HttpSession session,
 * RedirectAttributes ra) { User me = authService.requireLogin(session);
 * LoanApplication app = loanService.myAppOrThrow(id, me.getId());
 * 
 * documentService.upload(app, type, file); ra.addFlashAttribute("msg",
 * "Document uploaded"); return "redirect:/ui/customer/applications/" + id; }
 * 
 * @GetMapping("/applications/{id}/emis") public String emis(@PathVariable Long
 * id, HttpSession session, Model model) { User me =
 * authService.requireLogin(session); LoanApplication app =
 * loanService.myAppOrThrow(id, me.getId());
 * 
 * model.addAttribute("app", app); model.addAttribute("emis",
 * emiService.list(app.getId())); model.addAttribute("payReq", new
 * PayEmiRequest());
 * 
 * return "ui/customer/emis"; }
 * 
 * @PostMapping("/applications/{id}/emis/{emiId}/pay") public String
 * payEmi(@PathVariable Long id,
 * 
 * @PathVariable Long emiId,
 * 
 * @Valid @ModelAttribute("payReq") PayEmiRequest payReq, BindingResult br,
 * HttpSession session, RedirectAttributes ra, Model model) { User me =
 * authService.requireLogin(session); LoanApplication app =
 * loanService.myAppOrThrow(id, me.getId());
 * 
 * if (br.hasErrors()) { model.addAttribute("app", app);
 * model.addAttribute("emis", emiService.list(app.getId())); return
 * "ui/customer/emis"; }
 * 
 * paymentService.payEmi(app, emiId, payReq); ra.addFlashAttribute("msg",
 * "EMI paid successfully"); return "redirect:/ui/customer/applications/" + id +
 * "/emis"; }
 * 
 * @GetMapping("/applications/{id}/ledger") public String ledger(@PathVariable
 * Long id, HttpSession session, Model model) { User me =
 * authService.requireLogin(session); LoanApplication app =
 * loanService.myAppOrThrow(id, me.getId());
 * 
 * model.addAttribute("app", app); model.addAttribute("entries",
 * ledgerService.getLedger(app.getId())); model.addAttribute("balance",
 * ledgerService.latestOutstanding(app.getId()));
 * 
 * return "ui/customer/ledger"; } }
 */

/*
 * package com.happytohelp.homeloan.controller.ui;
 * 
 * import com.happytohelp.homeloan.dto.LoanApplicationCreateRequest; import
 * com.happytohelp.homeloan.dto.PayEmiRequest; import
 * com.happytohelp.homeloan.model.*; import
 * com.happytohelp.homeloan.repo.EmiScheduleRepository; import
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
 * org.springframework.web.multipart.MultipartFile; import
 * org.springframework.web.servlet.mvc.support.RedirectAttributes;
 * 
 * import java.io.File; import java.nio.file.Files;
 * 
 * @Controller
 * 
 * @RequestMapping("/ui/customer") public class CustomerUiController {
 * 
 * private final AuthService authService; private final LoanService loanService;
 * private final LoanDocumentRepository docRepo; private final DocumentService
 * documentService; private final EmiService emiService; private final
 * EmiScheduleRepository emiRepo; private final LedgerService ledgerService;
 * private final PaymentService paymentService; private final PdfLetterService
 * pdfLetterService;
 * 
 * public CustomerUiController(AuthService authService, LoanService loanService,
 * LoanDocumentRepository docRepo, DocumentService documentService, EmiService
 * emiService, EmiScheduleRepository emiRepo, LedgerService ledgerService,
 * PaymentService paymentService, PdfLetterService pdfLetterService) {
 * this.authService = authService; this.loanService = loanService; this.docRepo
 * = docRepo; this.documentService = documentService; this.emiService =
 * emiService; this.emiRepo = emiRepo; this.ledgerService = ledgerService;
 * this.paymentService = paymentService; this.pdfLetterService =
 * pdfLetterService; }
 * 
 * @GetMapping("/dashboard") public String dashboard(HttpSession session, Model
 * model) { User me = authService.requireLogin(session);
 * model.addAttribute("apps", loanService.myApps(me.getId())); return
 * "ui/customer/dashboard"; }
 * 
 * @GetMapping("/applications/new") public String newApplication(Model model) {
 * model.addAttribute("req", new LoanApplicationCreateRequest()); return
 * "ui/customer/app-form"; }
 * 
 * @PostMapping("/applications") public String
 * create(@Valid @ModelAttribute("req") LoanApplicationCreateRequest req,
 * BindingResult br, HttpSession session, RedirectAttributes ra) { if
 * (br.hasErrors()) return "ui/customer/app-form";
 * 
 * User me = authService.requireLogin(session); LoanApplication app =
 * loanService.create(me, req); ra.addFlashAttribute("msg",
 * "Application created: #" + app.getId()); return
 * "redirect:/ui/customer/applications/" + app.getId(); }
 * 
 * @GetMapping("/applications/{id}") public String appDetail(@PathVariable Long
 * id, HttpSession session, Model model) { User me =
 * authService.requireLogin(session); LoanApplication app =
 * loanService.myAppOrThrow(id, me.getId());
 * 
 * model.addAttribute("app", app); model.addAttribute("documents",
 * docRepo.findByLoanApplicationIdOrderByIdDesc(app.getId()));
 * model.addAttribute("docTypes", DocumentType.values());
 * 
 * return "ui/customer/app-detail"; }
 * 
 * @PostMapping("/applications/{id}/submit") public String submit(@PathVariable
 * Long id, HttpSession session, RedirectAttributes ra) { User me =
 * authService.requireLogin(session); LoanApplication app =
 * loanService.myAppOrThrow(id, me.getId()); loanService.submit(app);
 * 
 * ra.addFlashAttribute("msg", "Application submitted"); return
 * "redirect:/ui/customer/applications/" + id; }
 * 
 * @PostMapping("/applications/{id}/documents") public String
 * upload(@PathVariable Long id,
 * 
 * @RequestParam DocumentType type,
 * 
 * @RequestParam("file") MultipartFile file, HttpSession session,
 * RedirectAttributes ra) { User me = authService.requireLogin(session);
 * LoanApplication app = loanService.myAppOrThrow(id, me.getId());
 * 
 * documentService.upload(app, type, file); ra.addFlashAttribute("msg",
 * "Document uploaded"); return "redirect:/ui/customer/applications/" + id; }
 * 
 * // NEW: Download uploaded document
 * 
 * @GetMapping("/applications/{id}/documents/{docId}/download") public
 * ResponseEntity<FileSystemResource> downloadDoc(@PathVariable Long id,
 * 
 * @PathVariable Long docId, HttpSession session) throws Exception { User me =
 * authService.requireLogin(session); LoanApplication app =
 * loanService.myAppOrThrow(id, me.getId());
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
 * @GetMapping("/applications/{id}/emis") public String emis(@PathVariable Long
 * id, HttpSession session, Model model) { User me =
 * authService.requireLogin(session); LoanApplication app =
 * loanService.myAppOrThrow(id, me.getId());
 * 
 * model.addAttribute("app", app); model.addAttribute("emis",
 * emiService.list(app.getId())); model.addAttribute("payReq", new
 * PayEmiRequest());
 * 
 * return "ui/customer/emis"; }
 * 
 * // UPDATED: after pay redirect to receipt page
 * 
 * @PostMapping("/applications/{id}/emis/{emiId}/pay") public String
 * payEmi(@PathVariable Long id,
 * 
 * @PathVariable Long emiId,
 * 
 * @Valid @ModelAttribute("payReq") PayEmiRequest payReq, BindingResult br,
 * HttpSession session, RedirectAttributes ra, Model model) { User me =
 * authService.requireLogin(session); LoanApplication app =
 * loanService.myAppOrThrow(id, me.getId());
 * 
 * if (br.hasErrors()) { model.addAttribute("app", app);
 * model.addAttribute("emis", emiService.list(app.getId())); return
 * "ui/customer/emis"; }
 * 
 * EmiSchedule saved = paymentService.payEmi(app, emiId, payReq);
 * ra.addFlashAttribute("msg", "EMI paid successfully"); return
 * "redirect:/ui/customer/applications/" + id + "/emis/" + saved.getId() +
 * "/receipt"; }
 * 
 * @GetMapping("/applications/{id}/emis/{emiId}/receipt") public String
 * receipt(@PathVariable Long id, @PathVariable Long emiId, HttpSession session,
 * Model model) { User me = authService.requireLogin(session); LoanApplication
 * app = loanService.myAppOrThrow(id, me.getId());
 * 
 * EmiSchedule emi = emiRepo.findByIdAndLoanApplicationId(emiId, app.getId())
 * .orElseThrow(() -> new RuntimeException("EMI not found"));
 * 
 * if (emi.getPayment() == null) throw new
 * RuntimeException("Receipt not available (EMI not paid)");
 * 
 * model.addAttribute("app", app); model.addAttribute("emi", emi); return
 * "ui/customer/receipt"; }
 * 
 * @GetMapping("/applications/{id}/emis/{emiId}/receipt.pdf") public
 * ResponseEntity<byte[]> receiptPdf(@PathVariable Long id, @PathVariable Long
 * emiId, HttpSession session) { User me = authService.requireLogin(session);
 * LoanApplication app = loanService.myAppOrThrow(id, me.getId());
 * 
 * EmiSchedule emi = emiRepo.findByIdAndLoanApplicationId(emiId, app.getId())
 * .orElseThrow(() -> new RuntimeException("EMI not found")); if
 * (emi.getPayment() == null) throw new
 * RuntimeException("Receipt not available (EMI not paid)");
 * 
 * byte[] pdf = pdfLetterService.buildReceiptPdf(app, me, emi);
 * 
 * return ResponseEntity.ok() .contentType(MediaType.APPLICATION_PDF)
 * .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=Receipt_App_" +
 * app.getId() + "_EMI_" + emi.getInstallmentNo() + ".pdf") .body(pdf); }
 * 
 * @GetMapping("/applications/{id}/ledger") public String ledger(@PathVariable
 * Long id, HttpSession session, Model model) { User me =
 * authService.requireLogin(session); LoanApplication app =
 * loanService.myAppOrThrow(id, me.getId());
 * 
 * model.addAttribute("app", app); model.addAttribute("entries",
 * ledgerService.getLedger(app.getId())); model.addAttribute("balance",
 * ledgerService.latestOutstanding(app.getId())); return "ui/customer/ledger"; }
 * 
 * // NEW: Sanction letter download from portal
 * 
 * @GetMapping("/applications/{id}/sanction-letter.pdf") public
 * ResponseEntity<byte[]> sanctionPdf(@PathVariable Long id, HttpSession
 * session) { User me = authService.requireLogin(session); LoanApplication app =
 * loanService.myAppOrThrow(id, me.getId());
 * 
 * if (!(app.getStatus() == ApplicationStatus.SANCTIONED || app.getStatus() ==
 * ApplicationStatus.DISBURSED)) { throw new
 * RuntimeException("Sanction letter available only after SANCTIONED"); }
 * 
 * byte[] pdf = pdfLetterService.buildSanctionLetter(app, me); return
 * ResponseEntity.ok() .contentType(MediaType.APPLICATION_PDF)
 * .header(HttpHeaders.CONTENT_DISPOSITION,
 * "inline; filename=SanctionLetter_App_" + app.getId() + ".pdf") .body(pdf); }
 * 
 * // NEW: Disbursement letter download from portal
 * 
 * @GetMapping("/applications/{id}/disbursement-letter.pdf") public
 * ResponseEntity<byte[]> disbursementPdf(@PathVariable Long id, HttpSession
 * session) { User me = authService.requireLogin(session); LoanApplication app =
 * loanService.myAppOrThrow(id, me.getId());
 * 
 * if (app.getStatus() != ApplicationStatus.DISBURSED) { throw new
 * RuntimeException("Disbursement letter available only after DISBURSED"); }
 * 
 * byte[] pdf = pdfLetterService.buildDisbursementLetter(app, me); return
 * ResponseEntity.ok() .contentType(MediaType.APPLICATION_PDF)
 * .header(HttpHeaders.CONTENT_DISPOSITION,
 * "inline; filename=DisbursementLetter_App_" + app.getId() + ".pdf")
 * .body(pdf); } }
 * 
 * 
 * 
 * 
 *//*
	 * package com.happytohelp.homeloan.controller.ui;
	 * 
	 * import com.happytohelp.homeloan.dto.LoanApplicationCreateRequest; import
	 * com.happytohelp.homeloan.dto.PayEmiRequest; import
	 * com.happytohelp.homeloan.model.*; import
	 * com.happytohelp.homeloan.repo.EmiScheduleRepository; import
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
	 * org.springframework.web.multipart.MultipartFile; import
	 * org.springframework.web.servlet.mvc.support.RedirectAttributes;
	 * 
	 * import java.io.File; import java.nio.file.Files; import
	 * com.happytohelp.homeloan.repo.LoanApplicationRepository; import
	 * com.happytohelp.homeloan.repo.EmiScheduleRepository; import
	 * com.happytohelp.homeloan.service.DashboardService; import
	 * org.springframework.data.domain.PageRequest;
	 * 
	 * import java.util.List;
	 * 
	 * @Controller
	 * 
	 * @RequestMapping("/ui/customer") public class CustomerUiController {
	 * 
	 * LoanApplicationRepository loanRepo; DashboardService dashboardService;
	 * private final AuthService authService; private final LoanService loanService;
	 * private final LoanDocumentRepository docRepo; private final DocumentService
	 * documentService; private final EmiService emiService; private final
	 * EmiScheduleRepository emiRepo; private final LedgerService ledgerService;
	 * private final PaymentService paymentService; private final PdfLetterService
	 * pdfLetterService;
	 * 
	 * public CustomerUiController(AuthService authService, LoanService loanService,
	 * LoanDocumentRepository docRepo, DocumentService documentService, EmiService
	 * emiService, EmiScheduleRepository emiRepo, LedgerService ledgerService,
	 * PaymentService paymentService, PdfLetterService pdfLetterService) {
	 * this.authService = authService; this.loanService = loanService; this.docRepo
	 * = docRepo; this.documentService = documentService; this.emiService =
	 * emiService; this.emiRepo = emiRepo; this.ledgerService = ledgerService;
	 * this.paymentService = paymentService; this.pdfLetterService =
	 * pdfLetterService; }
	 * 
	 * 
	 * @GetMapping("/dashboard") public String dashboard(HttpSession session, Model
	 * model) { User me = authService.requireLogin(session);
	 * model.addAttribute("apps", loanService.myApps(me.getId())); return
	 * "ui/customer/dashboard"; }
	 * 
	 * 
	 * 
	 * 
	 * @GetMapping("/dashboard") public String dashboard(HttpSession session, Model
	 * model) { User me = authService.requireLogin(session);
	 * 
	 * var apps = loanService.myApps(me.getId()); model.addAttribute("apps", apps);
	 * 
	 * long totalApps = loanRepo.countByUserId(me.getId()); long disbursed =
	 * loanRepo.countByUserIdAndStatus(me.getId(), ApplicationStatus.DISBURSED);
	 * long sanctioned = loanRepo.countByUserIdAndStatus(me.getId(),
	 * ApplicationStatus.SANCTIONED); long inProgress =
	 * loanRepo.countByUserIdAndStatus(me.getId(), ApplicationStatus.SUBMITTED) +
	 * loanRepo.countByUserIdAndStatus(me.getId(), ApplicationStatus.UNDER_REVIEW);
	 * 
	 * var nextDueList = emiRepo.findNextDueEmiForUser(me.getId(),
	 * PageRequest.of(0,1)); EmiSchedule nextDue = nextDueList.isEmpty() ? null :
	 * nextDueList.get(0);
	 * 
	 * model.addAttribute("kpiTotalApps", totalApps);
	 * model.addAttribute("kpiInProgress", inProgress);
	 * model.addAttribute("kpiSanctioned", sanctioned);
	 * model.addAttribute("kpiDisbursed", disbursed);
	 * 
	 * model.addAttribute("kpiOutstanding",
	 * dashboardService.totalOutstandingForUser(me.getId()));
	 * model.addAttribute("nextDue", nextDue);
	 * 
	 * // Chart (your own apps by status) long draft =
	 * loanRepo.countByUserIdAndStatus(me.getId(), ApplicationStatus.DRAFT); long
	 * submitted = loanRepo.countByUserIdAndStatus(me.getId(),
	 * ApplicationStatus.SUBMITTED); long review =
	 * loanRepo.countByUserIdAndStatus(me.getId(), ApplicationStatus.UNDER_REVIEW);
	 * long rej = loanRepo.countByUserIdAndStatus(me.getId(),
	 * ApplicationStatus.REJECTED); long sanc =
	 * loanRepo.countByUserIdAndStatus(me.getId(), ApplicationStatus.SANCTIONED);
	 * long disb = loanRepo.countByUserIdAndStatus(me.getId(),
	 * ApplicationStatus.DISBURSED);
	 * 
	 * model.addAttribute("chartLabels",
	 * List.of("DRAFT","SUBMITTED","UNDER_REVIEW","SANCTIONED","DISBURSED",
	 * "REJECTED")); model.addAttribute("chartCounts", List.of(draft, submitted,
	 * review, sanc, disb, rej));
	 * 
	 * return "ui/customer/dashboard"; }
	 * 
	 * @GetMapping("/applications/new") public String newApplication(Model model) {
	 * model.addAttribute("req", new LoanApplicationCreateRequest()); return
	 * "ui/customer/app-form"; }
	 * 
	 * @PostMapping("/applications") public String
	 * create(@Valid @ModelAttribute("req") LoanApplicationCreateRequest req,
	 * BindingResult br, HttpSession session, RedirectAttributes ra) { if
	 * (br.hasErrors()) return "ui/customer/app-form";
	 * 
	 * User me = authService.requireLogin(session); LoanApplication app =
	 * loanService.create(me, req); ra.addFlashAttribute("msg",
	 * "Application created: #" + app.getId()); return
	 * "redirect:/ui/customer/applications/" + app.getId(); }
	 * 
	 * @GetMapping("/applications/{id}") public String appDetail(@PathVariable Long
	 * id, HttpSession session, Model model) { User me =
	 * authService.requireLogin(session); LoanApplication app =
	 * loanService.myAppOrThrow(id, me.getId());
	 * 
	 * model.addAttribute("app", app); model.addAttribute("documents",
	 * docRepo.findByLoanApplicationIdOrderByIdDesc(app.getId()));
	 * model.addAttribute("docTypes", DocumentType.values()); return
	 * "ui/customer/app-detail"; }
	 * 
	 * @PostMapping("/applications/{id}/submit") public String submit(@PathVariable
	 * Long id, HttpSession session, RedirectAttributes ra) { User me =
	 * authService.requireLogin(session); LoanApplication app =
	 * loanService.myAppOrThrow(id, me.getId()); loanService.submit(app);
	 * 
	 * ra.addFlashAttribute("msg", "Application submitted"); return
	 * "redirect:/ui/customer/applications/" + id; }
	 * 
	 * @PostMapping("/applications/{id}/documents") public String
	 * upload(@PathVariable Long id,
	 * 
	 * @RequestParam DocumentType type,
	 * 
	 * @RequestParam("file") MultipartFile file, HttpSession session,
	 * RedirectAttributes ra) { User me = authService.requireLogin(session);
	 * LoanApplication app = loanService.myAppOrThrow(id, me.getId());
	 * 
	 * documentService.upload(app, type, file); ra.addFlashAttribute("msg",
	 * "Document uploaded"); return "redirect:/ui/customer/applications/" + id; }
	 * 
	 * // Download uploaded document (customer)
	 * 
	 * @GetMapping("/applications/{id}/documents/{docId}/download") public
	 * ResponseEntity<FileSystemResource> downloadDoc(@PathVariable Long id,
	 * 
	 * @PathVariable Long docId, HttpSession session) throws Exception { User me =
	 * authService.requireLogin(session); LoanApplication app =
	 * loanService.myAppOrThrow(id, me.getId());
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
	 * @GetMapping("/applications/{id}/emis") public String emis(@PathVariable Long
	 * id, HttpSession session, Model model) { User me =
	 * authService.requireLogin(session); LoanApplication app =
	 * loanService.myAppOrThrow(id, me.getId());
	 * 
	 * model.addAttribute("app", app); model.addAttribute("emis",
	 * emiService.list(app.getId())); model.addAttribute("payReq", new
	 * PayEmiRequest()); return "ui/customer/emis"; }
	 * 
	 * // Pay EMI -> redirect to receipt
	 * 
	 * @PostMapping("/applications/{id}/emis/{emiId}/pay") public String
	 * payEmi(@PathVariable Long id,
	 * 
	 * @PathVariable Long emiId,
	 * 
	 * @Valid @ModelAttribute("payReq") PayEmiRequest payReq, BindingResult br,
	 * HttpSession session, RedirectAttributes ra, Model model) { User me =
	 * authService.requireLogin(session); LoanApplication app =
	 * loanService.myAppOrThrow(id, me.getId());
	 * 
	 * if (br.hasErrors()) { model.addAttribute("app", app);
	 * model.addAttribute("emis", emiService.list(app.getId())); return
	 * "ui/customer/emis"; }
	 * 
	 * EmiSchedule saved = paymentService.payEmi(app, emiId, payReq);
	 * ra.addFlashAttribute("msg", "EMI paid successfully"); return
	 * "redirect:/ui/customer/applications/" + id + "/emis/" + saved.getId() +
	 * "/receipt"; }
	 * 
	 * @GetMapping("/applications/{id}/emis/{emiId}/receipt") public String
	 * receipt(@PathVariable Long id, @PathVariable Long emiId, HttpSession session,
	 * Model model) { User me = authService.requireLogin(session); LoanApplication
	 * app = loanService.myAppOrThrow(id, me.getId());
	 * 
	 * EmiSchedule emi = emiRepo.findByIdAndLoanApplicationId(emiId, app.getId())
	 * .orElseThrow(() -> new RuntimeException("EMI not found"));
	 * 
	 * if (emi.getPayment() == null) throw new
	 * RuntimeException("Receipt not available (EMI not paid)");
	 * 
	 * model.addAttribute("app", app); model.addAttribute("emi", emi); return
	 * "ui/customer/receipt"; }
	 * 
	 * @GetMapping("/applications/{id}/emis/{emiId}/receipt.pdf") public
	 * ResponseEntity<byte[]> receiptPdf(@PathVariable Long id, @PathVariable Long
	 * emiId, HttpSession session) { User me = authService.requireLogin(session);
	 * LoanApplication app = loanService.myAppOrThrow(id, me.getId());
	 * 
	 * EmiSchedule emi = emiRepo.findByIdAndLoanApplicationId(emiId, app.getId())
	 * .orElseThrow(() -> new RuntimeException("EMI not found")); if
	 * (emi.getPayment() == null) throw new
	 * RuntimeException("Receipt not available (EMI not paid)");
	 * 
	 * byte[] pdf = pdfLetterService.buildReceiptPdf(app, me, emi);
	 * 
	 * return ResponseEntity.ok() .contentType(MediaType.APPLICATION_PDF)
	 * .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=Receipt_App_" +
	 * app.getId() + "_EMI_" + emi.getInstallmentNo() + ".pdf") .body(pdf); }
	 * 
	 * @GetMapping("/applications/{id}/ledger") public String ledger(@PathVariable
	 * Long id, HttpSession session, Model model) { User me =
	 * authService.requireLogin(session); LoanApplication app =
	 * loanService.myAppOrThrow(id, me.getId());
	 * 
	 * model.addAttribute("app", app); model.addAttribute("entries",
	 * ledgerService.getLedger(app.getId())); model.addAttribute("balance",
	 * ledgerService.latestOutstanding(app.getId())); return "ui/customer/ledger"; }
	 * 
	 * // Sanction letter PDF (customer download)
	 * 
	 * @GetMapping("/applications/{id}/sanction-letter.pdf") public
	 * ResponseEntity<byte[]> sanctionPdf(@PathVariable Long id, HttpSession
	 * session) { User me = authService.requireLogin(session); LoanApplication app =
	 * loanService.myAppOrThrow(id, me.getId());
	 * 
	 * if (!(app.getStatus() == ApplicationStatus.SANCTIONED || app.getStatus() ==
	 * ApplicationStatus.DISBURSED)) { throw new
	 * RuntimeException("Sanction letter available only after SANCTIONED"); }
	 * 
	 * byte[] pdf = pdfLetterService.buildSanctionLetter(app, me); return
	 * ResponseEntity.ok() .contentType(MediaType.APPLICATION_PDF)
	 * .header(HttpHeaders.CONTENT_DISPOSITION,
	 * "inline; filename=SanctionLetter_App_" + app.getId() + ".pdf") .body(pdf); }
	 * 
	 * // Disbursement letter PDF (customer download)
	 * 
	 * @GetMapping("/applications/{id}/disbursement-letter.pdf") public
	 * ResponseEntity<byte[]> disbursementPdf(@PathVariable Long id, HttpSession
	 * session) { User me = authService.requireLogin(session); LoanApplication app =
	 * loanService.myAppOrThrow(id, me.getId());
	 * 
	 * if (app.getStatus() != ApplicationStatus.DISBURSED) { throw new
	 * RuntimeException("Disbursement letter available only after DISBURSED"); }
	 * 
	 * byte[] pdf = pdfLetterService.buildDisbursementLetter(app, me); return
	 * ResponseEntity.ok() .contentType(MediaType.APPLICATION_PDF)
	 * .header(HttpHeaders.CONTENT_DISPOSITION,
	 * "inline; filename=DisbursementLetter_App_" + app.getId() + ".pdf")
	 * .body(pdf); } }
	 */





package com.happytohelp.homeloan.controller.ui;

import com.happytohelp.homeloan.dto.LoanApplicationCreateRequest;
import com.happytohelp.homeloan.dto.PayEmiRequest;
import com.happytohelp.homeloan.model.*;
import com.happytohelp.homeloan.repo.EmiScheduleRepository;
import com.happytohelp.homeloan.repo.LoanApplicationRepository;
import com.happytohelp.homeloan.repo.LoanDocumentRepository;
import com.happytohelp.homeloan.service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Controller
@RequestMapping("/ui/customer")
public class CustomerUiController {

    private final AuthService authService;
    private final LoanService loanService;
    private final LoanDocumentRepository docRepo;
    private final DocumentService documentService;

    private final EmiService emiService;
    private final EmiScheduleRepository emiRepo;

    private final LedgerService ledgerService;
    private final PaymentService paymentService;
    private final PdfLetterService pdfLetterService;

    // NEW for dashboard KPIs/Chart
    private final LoanApplicationRepository loanRepo;
    private final DashboardService dashboardService;

    public CustomerUiController(AuthService authService,
                                LoanService loanService,
                                LoanDocumentRepository docRepo,
                                DocumentService documentService,
                                EmiService emiService,
                                EmiScheduleRepository emiRepo,
                                LedgerService ledgerService,
                                PaymentService paymentService,
                                PdfLetterService pdfLetterService,
                                LoanApplicationRepository loanRepo,
                                DashboardService dashboardService) {
        this.authService = authService;
        this.loanService = loanService;
        this.docRepo = docRepo;
        this.documentService = documentService;
        this.emiService = emiService;
        this.emiRepo = emiRepo;
        this.ledgerService = ledgerService;
        this.paymentService = paymentService;
        this.pdfLetterService = pdfLetterService;
        this.loanRepo = loanRepo;
        this.dashboardService = dashboardService;
    }

    // UPDATED dashboard with KPIs + next EMI + chart data
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User me = authService.requireLogin(session);

        var apps = loanService.myApps(me.getId());
        model.addAttribute("apps", apps);

        long totalApps = loanRepo.countByUserId(me.getId());
        long disbursed = loanRepo.countByUserIdAndStatus(me.getId(), ApplicationStatus.DISBURSED);
        long sanctioned = loanRepo.countByUserIdAndStatus(me.getId(), ApplicationStatus.SANCTIONED);
        long inProgress = loanRepo.countByUserIdAndStatus(me.getId(), ApplicationStatus.SUBMITTED)
                + loanRepo.countByUserIdAndStatus(me.getId(), ApplicationStatus.UNDER_REVIEW);

        List<EmiSchedule> nextDueList = emiRepo.findNextDueEmiForUser(me.getId(), PageRequest.of(0, 1));
        EmiSchedule nextDue = nextDueList.isEmpty() ? null : nextDueList.get(0);

        model.addAttribute("kpiTotalApps", totalApps);
        model.addAttribute("kpiInProgress", inProgress);
        model.addAttribute("kpiSanctioned", sanctioned);
        model.addAttribute("kpiDisbursed", disbursed);
        model.addAttribute("kpiOutstanding", dashboardService.totalOutstandingForUser(me.getId()));
        model.addAttribute("nextDue", nextDue);

        long draft = loanRepo.countByUserIdAndStatus(me.getId(), ApplicationStatus.DRAFT);
        long submitted = loanRepo.countByUserIdAndStatus(me.getId(), ApplicationStatus.SUBMITTED);
        long review = loanRepo.countByUserIdAndStatus(me.getId(), ApplicationStatus.UNDER_REVIEW);
        long rej = loanRepo.countByUserIdAndStatus(me.getId(), ApplicationStatus.REJECTED);
        long sanc = loanRepo.countByUserIdAndStatus(me.getId(), ApplicationStatus.SANCTIONED);
        long disb = loanRepo.countByUserIdAndStatus(me.getId(), ApplicationStatus.DISBURSED);

        model.addAttribute("chartLabels",
                List.of("DRAFT", "SUBMITTED", "UNDER_REVIEW", "SANCTIONED", "DISBURSED", "REJECTED"));
        model.addAttribute("chartCounts",
                List.of(draft, submitted, review, sanc, disb, rej));

        return "ui/customer/dashboard";
    }

    @GetMapping("/applications/new")
    public String newApplication(Model model) {
        model.addAttribute("req", new LoanApplicationCreateRequest());
        return "ui/customer/app-form";
    }

    @PostMapping("/applications")
    public String create(@Valid @ModelAttribute("req") LoanApplicationCreateRequest req,
                         BindingResult br,
                         HttpSession session,
                         RedirectAttributes ra) {
        if (br.hasErrors()) return "ui/customer/app-form";

        User me = authService.requireLogin(session);
        LoanApplication app = loanService.create(me, req);
        ra.addFlashAttribute("msg", "Application created: #" + app.getId());
        return "redirect:/ui/customer/applications/" + app.getId();
    }

    @GetMapping("/applications/{id}")
    public String appDetail(@PathVariable Long id, HttpSession session, Model model) {
        User me = authService.requireLogin(session);
        LoanApplication app = loanService.myAppOrThrow(id, me.getId());

        model.addAttribute("app", app);
        model.addAttribute("documents", docRepo.findByLoanApplicationIdOrderByIdDesc(app.getId()));
        model.addAttribute("docTypes", DocumentType.values());
        return "ui/customer/app-detail";
    }

    @PostMapping("/applications/{id}/submit")
    public String submit(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
        User me = authService.requireLogin(session);
        LoanApplication app = loanService.myAppOrThrow(id, me.getId());
        loanService.submit(app);

        ra.addFlashAttribute("msg", "Application submitted");
        return "redirect:/ui/customer/applications/" + id;
    }

    @PostMapping("/applications/{id}/documents")
    public String upload(@PathVariable Long id,
                         @RequestParam DocumentType type,
                         @RequestParam("file") MultipartFile file,
                         HttpSession session,
                         RedirectAttributes ra) {
        User me = authService.requireLogin(session);
        LoanApplication app = loanService.myAppOrThrow(id, me.getId());

        documentService.upload(app, type, file);
        ra.addFlashAttribute("msg", "Document uploaded");
        return "redirect:/ui/customer/applications/" + id;
    }

    @GetMapping("/applications/{id}/documents/{docId}/download")
    public ResponseEntity<FileSystemResource> downloadDoc(@PathVariable Long id,
                                                         @PathVariable Long docId,
                                                         HttpSession session) throws Exception {
        User me = authService.requireLogin(session);
        LoanApplication app = loanService.myAppOrThrow(id, me.getId());

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

    @GetMapping("/applications/{id}/emis")
    public String emis(@PathVariable Long id, HttpSession session, Model model) {
        User me = authService.requireLogin(session);
        LoanApplication app = loanService.myAppOrThrow(id, me.getId());

        model.addAttribute("app", app);
        model.addAttribute("emis", emiService.list(app.getId()));
        model.addAttribute("payReq", new PayEmiRequest());
        return "ui/customer/emis";
    }

    @PostMapping("/applications/{id}/emis/{emiId}/pay")
    public String payEmi(@PathVariable Long id,
                         @PathVariable Long emiId,
                         @Valid @ModelAttribute("payReq") PayEmiRequest payReq,
                         BindingResult br,
                         HttpSession session,
                         RedirectAttributes ra,
                         Model model) {
        User me = authService.requireLogin(session);
        LoanApplication app = loanService.myAppOrThrow(id, me.getId());

        if (br.hasErrors()) {
            model.addAttribute("app", app);
            model.addAttribute("emis", emiService.list(app.getId()));
            return "ui/customer/emis";
        }

        EmiSchedule saved = paymentService.payEmi(app, emiId, payReq);
        ra.addFlashAttribute("msg", "EMI paid successfully");
        return "redirect:/ui/customer/applications/" + id + "/emis/" + saved.getId() + "/receipt";
    }

    @GetMapping("/applications/{id}/emis/{emiId}/receipt")
    public String receipt(@PathVariable Long id, @PathVariable Long emiId,
                          HttpSession session, Model model) {
        User me = authService.requireLogin(session);
        LoanApplication app = loanService.myAppOrThrow(id, me.getId());

        EmiSchedule emi = emiRepo.findByIdAndLoanApplicationId(emiId, app.getId())
                .orElseThrow(() -> new RuntimeException("EMI not found"));

        if (emi.getPayment() == null) throw new RuntimeException("Receipt not available (EMI not paid)");

        model.addAttribute("app", app);
        model.addAttribute("emi", emi);
        return "ui/customer/receipt";
    }

    @GetMapping("/applications/{id}/emis/{emiId}/receipt.pdf")
    public ResponseEntity<byte[]> receiptPdf(@PathVariable Long id, @PathVariable Long emiId,
                                            HttpSession session) {
        User me = authService.requireLogin(session);
        LoanApplication app = loanService.myAppOrThrow(id, me.getId());

        EmiSchedule emi = emiRepo.findByIdAndLoanApplicationId(emiId, app.getId())
                .orElseThrow(() -> new RuntimeException("EMI not found"));
        if (emi.getPayment() == null) throw new RuntimeException("Receipt not available (EMI not paid)");

        byte[] pdf = pdfLetterService.buildReceiptPdf(app, me, emi);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=Receipt_App_" + app.getId() + "_EMI_" + emi.getInstallmentNo() + ".pdf")
                .body(pdf);
    }

    @GetMapping("/applications/{id}/ledger")
    public String ledger(@PathVariable Long id, HttpSession session, Model model) {
        User me = authService.requireLogin(session);
        LoanApplication app = loanService.myAppOrThrow(id, me.getId());

        model.addAttribute("app", app);
        model.addAttribute("entries", ledgerService.getLedger(app.getId()));
        model.addAttribute("balance", ledgerService.latestOutstanding(app.getId()));
        return "ui/customer/ledger";
    }

    @GetMapping("/applications/{id}/sanction-letter.pdf")
    public ResponseEntity<byte[]> sanctionPdf(@PathVariable Long id, HttpSession session) {
        User me = authService.requireLogin(session);
        LoanApplication app = loanService.myAppOrThrow(id, me.getId());

        if (!(app.getStatus() == ApplicationStatus.SANCTIONED || app.getStatus() == ApplicationStatus.DISBURSED)) {
            throw new RuntimeException("Sanction letter available only after SANCTIONED");
        }

        byte[] pdf = pdfLetterService.buildSanctionLetter(app, me);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=SanctionLetter_App_" + app.getId() + ".pdf")
                .body(pdf);
    }

    @GetMapping("/applications/{id}/disbursement-letter.pdf")
    public ResponseEntity<byte[]> disbursementPdf(@PathVariable Long id, HttpSession session) {
        User me = authService.requireLogin(session);
        LoanApplication app = loanService.myAppOrThrow(id, me.getId());

        if (app.getStatus() != ApplicationStatus.DISBURSED) {
            throw new RuntimeException("Disbursement letter available only after DISBURSED");
        }

        byte[] pdf = pdfLetterService.buildDisbursementLetter(app, me);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=DisbursementLetter_App_" + app.getId() + ".pdf")
                .body(pdf);
    }
}