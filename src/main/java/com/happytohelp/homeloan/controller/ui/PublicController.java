/*
 * package com.happytohelp.homeloan.controller.ui;
 * 
 * 
 * 
 * import org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.*;
 * 
 * import java.math.BigDecimal; import java.math.RoundingMode;
 * 
 * @Controller
 * 
 * @RequestMapping("/ui/public") public class PublicController {
 * 
 * @GetMapping("/about") public String about() { return "ui/public/about"; }
 * 
 * @GetMapping("/contact") public String contact() { return "ui/public/contact";
 * }
 * 
 * @PostMapping("/contact") public String contactSubmit(@RequestParam String
 * name,
 * 
 * @RequestParam String email,
 * 
 * @RequestParam String message, Model model) { // For now just show success
 * message (you can email this to admin later) model.addAttribute("msg",
 * "Thanks " + name + ". We received your message."); return
 * "ui/public/contact"; }
 * 
 * // EMI Calculator
 * 
 * @GetMapping("/emi-calculator") public String emiCalculator() { return
 * "ui/public/emi-calculator"; }
 * 
 * @PostMapping("/emi-calculator") public String
 * emiCalculatorResult(@RequestParam BigDecimal principal,
 * 
 * @RequestParam BigDecimal annualRate,
 * 
 * @RequestParam Integer tenureMonths, Model model) {
 * 
 * // r = annualRate / 12 / 100 BigDecimal r =
 * annualRate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP)
 * .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);
 * 
 * // EMI = P*r*(1+r)^n / ((1+r)^n - 1) BigDecimal onePlusR =
 * BigDecimal.ONE.add(r); BigDecimal pow = onePlusR.pow(tenureMonths);
 * 
 * BigDecimal emi = principal.multiply(r).multiply(pow)
 * .divide(pow.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
 * 
 * BigDecimal totalPayable =
 * emi.multiply(BigDecimal.valueOf(tenureMonths)).setScale(2,
 * RoundingMode.HALF_UP); BigDecimal totalInterest =
 * totalPayable.subtract(principal).setScale(2, RoundingMode.HALF_UP);
 * 
 * model.addAttribute("principal", principal); model.addAttribute("annualRate",
 * annualRate); model.addAttribute("tenureMonths", tenureMonths);
 * model.addAttribute("emi", emi); model.addAttribute("totalPayable",
 * totalPayable); model.addAttribute("totalInterest", totalInterest);
 * 
 * return "ui/public/emi-calculator"; }
 * 
 * // CIBIL Check (demo)
 * 
 * @GetMapping("/cibil-check") public String cibil() { return
 * "ui/public/cibil-check"; }
 * 
 * @PostMapping("/cibil-check") public String cibilResult(@RequestParam String
 * fullName,
 * 
 * @RequestParam String pan, Model model) {
 * 
 * // Demo score: deterministic based on input (so same user gets same score)
 * int hash = Math.abs((fullName + pan).toUpperCase().hashCode()); int score =
 * 650 + (hash % 201); // 650 to 850
 * 
 * model.addAttribute("fullName", fullName); model.addAttribute("pan", pan);
 * model.addAttribute("score", score);
 * 
 * return "ui/public/cibil-check"; } }
 */

/*
 * 
 * package com.happytohelp.homeloan.controller.ui;
 * 
 * import com.happytohelp.homeloan.service.ContactService; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.ui.Model; import
 * org.springframework.web.bind.annotation.*;
 * 
 * import java.math.BigDecimal; import java.math.RoundingMode;
 * 
 * @Controller
 * 
 * @RequestMapping("/ui/public") public class PublicController {
 * 
 * private final ContactService contactService;
 * 
 * public PublicController(ContactService contactService) { this.contactService
 * = contactService; }
 * 
 * @GetMapping("/about") public String about() { return "ui/public/about"; }
 * 
 * @GetMapping("/contact") public String contact() { return "ui/public/contact";
 * }
 * 
 * @PostMapping("/contact") public String contactSubmit(@RequestParam String
 * name,
 * 
 * @RequestParam String email,
 * 
 * @RequestParam(defaultValue = "General Query") String subject,
 * 
 * @RequestParam String message, Model model) {
 * 
 * contactService.createAndNotify(name, email, subject, message);
 * model.addAttribute("msg", "Thanks " + name +
 * ". We received your message and sent a confirmation email."); return
 * "ui/public/contact"; }
 * 
 * // EMI Calculator
 * 
 * @GetMapping("/emi-calculator") public String emiCalculator() { return
 * "ui/public/emi-calculator"; }
 * 
 * @PostMapping("/emi-calculator") public String
 * emiCalculatorResult(@RequestParam BigDecimal principal,
 * 
 * @RequestParam BigDecimal annualRate,
 * 
 * @RequestParam Integer tenureMonths, Model model) {
 * 
 * BigDecimal r = annualRate.divide(BigDecimal.valueOf(12), 10,
 * RoundingMode.HALF_UP) .divide(BigDecimal.valueOf(100), 10,
 * RoundingMode.HALF_UP);
 * 
 * BigDecimal onePlusR = BigDecimal.ONE.add(r); BigDecimal pow =
 * onePlusR.pow(tenureMonths);
 * 
 * BigDecimal emi = principal.multiply(r).multiply(pow)
 * .divide(pow.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
 * 
 * BigDecimal totalPayable =
 * emi.multiply(BigDecimal.valueOf(tenureMonths)).setScale(2,
 * RoundingMode.HALF_UP); BigDecimal totalInterest =
 * totalPayable.subtract(principal).setScale(2, RoundingMode.HALF_UP);
 * 
 * model.addAttribute("principal", principal); model.addAttribute("annualRate",
 * annualRate); model.addAttribute("tenureMonths", tenureMonths);
 * model.addAttribute("emi", emi); model.addAttribute("totalPayable",
 * totalPayable); model.addAttribute("totalInterest", totalInterest);
 * 
 * return "ui/public/emi-calculator"; }
 * 
 * // CIBIL Check (demo)
 * 
 * @GetMapping("/cibil-check") public String cibil() { return
 * "ui/public/cibil-check"; }
 * 
 * @PostMapping("/cibil-check") public String cibilResult(@RequestParam String
 * fullName,
 * 
 * @RequestParam String pan, Model model) {
 * 
 * int hash = Math.abs((fullName + pan).toUpperCase().hashCode()); int score =
 * 650 + (hash % 201);
 * 
 * model.addAttribute("fullName", fullName); model.addAttribute("pan", pan);
 * model.addAttribute("score", score);
 * 
 * return "ui/public/cibil-check"; } }
 */



package com.happytohelp.homeloan.controller.ui;

import com.happytohelp.homeloan.service.ContactService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Controller
@RequestMapping("/ui/public")
public class PublicController {

    private final ContactService contactService;

    public PublicController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/about")
    public String about() {
        return "ui/public/about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "ui/public/contact";
    }

    @PostMapping("/contact")
    public String contactSubmit(@RequestParam String name,
                                @RequestParam String email,
                                @RequestParam(defaultValue = "General Query") String subject,
                                @RequestParam String message,
                                Model model) {

        contactService.createAndNotify(name, email, subject, message);
        model.addAttribute("msg", "Thanks " + name + ". We received your message and sent a confirmation email.");
        return "ui/public/contact";
    }

    @GetMapping("/emi-calculator")
    public String emiCalculator() {
        return "ui/public/emi-calculator";
    }

    @PostMapping("/emi-calculator")
    public String emiCalculatorResult(@RequestParam BigDecimal principal,
                                      @RequestParam BigDecimal annualRate,
                                      @RequestParam Integer tenureMonths,
                                      Model model) {

        BigDecimal r = annualRate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP);

        BigDecimal onePlusR = BigDecimal.ONE.add(r);
        BigDecimal pow = onePlusR.pow(tenureMonths);

        BigDecimal emi = principal.multiply(r).multiply(pow)
                .divide(pow.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);

        BigDecimal totalPayable = emi.multiply(BigDecimal.valueOf(tenureMonths)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalInterest = totalPayable.subtract(principal).setScale(2, RoundingMode.HALF_UP);

        model.addAttribute("principal", principal);
        model.addAttribute("annualRate", annualRate);
        model.addAttribute("tenureMonths", tenureMonths);
        model.addAttribute("emi", emi);
        model.addAttribute("totalPayable", totalPayable);
        model.addAttribute("totalInterest", totalInterest);

        return "ui/public/emi-calculator";
    }

    @GetMapping("/cibil-check")
    public String cibil() {
        return "ui/public/cibil-check";
    }

    @PostMapping("/cibil-check")
    public String cibilResult(@RequestParam String fullName,
                              @RequestParam String pan,
                              Model model) {

        int hash = Math.abs((fullName + pan).toUpperCase().hashCode());
        int score = 650 + (hash % 201);

        model.addAttribute("fullName", fullName);
        model.addAttribute("pan", pan);
        model.addAttribute("score", score);

        return "ui/public/cibil-check";
    }
}