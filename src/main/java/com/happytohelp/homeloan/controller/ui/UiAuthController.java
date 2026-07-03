package com.happytohelp.homeloan.controller.ui;



import com.happytohelp.homeloan.dto.LoginRequest;
import com.happytohelp.homeloan.dto.RegisterRequest;
import com.happytohelp.homeloan.model.Role;
import com.happytohelp.homeloan.model.User;
import com.happytohelp.homeloan.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ui")
public class UiAuthController {

    private final AuthService authService;

    public UiAuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginPage(Model model, @ModelAttribute("msg") String msg) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "ui/login";
    }

    @PostMapping("/login")
    public String doLogin(@Valid @ModelAttribute LoginRequest loginRequest,
                          BindingResult br,
                          HttpSession session,
                          RedirectAttributes ra) {
        if (br.hasErrors()) return "ui/login";

        User u = authService.login(loginRequest, session);
        ra.addFlashAttribute("msg", "Login successful");

        return u.getRole() == Role.ADMIN
                ? "redirect:/ui/admin/dashboard"
                : "redirect:/ui/customer/dashboard";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerRequest", new RegisterRequest());
        return "ui/register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid @ModelAttribute RegisterRequest registerRequest,
                             BindingResult br,
                             RedirectAttributes ra) {
        if (br.hasErrors()) return "ui/register";

        authService.registerCustomer(registerRequest);
        ra.addFlashAttribute("msg", "Registration successful. Please login.");
        return "redirect:/ui/login";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes ra) {
        authService.logout(session);
        ra.addFlashAttribute("msg", "Logged out");
        return "redirect:/ui/login";
    }
}