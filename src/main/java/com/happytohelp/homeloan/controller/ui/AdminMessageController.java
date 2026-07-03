package com.happytohelp.homeloan.controller.ui;


import com.happytohelp.homeloan.service.AuthService;
import com.happytohelp.homeloan.service.ContactService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ui/admin/messages")
public class AdminMessageController {

    private final AuthService authService;
    private final ContactService contactService;

    public AdminMessageController(AuthService authService, ContactService contactService) {
        this.authService = authService;
        this.contactService = contactService;
    }

    @GetMapping
    public String list(HttpSession session, Model model) {
        authService.requireAdmin(session);
        model.addAttribute("messages", contactService.listAll());
        return "ui/admin/messages";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, HttpSession session, Model model) {
        authService.requireAdmin(session);
        model.addAttribute("m", contactService.getOrThrow(id));
        return "ui/admin/message-detail";
    }

    @PostMapping("/{id}/resolve")
    public String resolve(@PathVariable Long id, HttpSession session, RedirectAttributes ra) {
        authService.requireAdmin(session);
        contactService.markResolved(id);
        ra.addFlashAttribute("msg", "Marked as RESOLVED");
        return "redirect:/ui/admin/messages/" + id;
    }
}