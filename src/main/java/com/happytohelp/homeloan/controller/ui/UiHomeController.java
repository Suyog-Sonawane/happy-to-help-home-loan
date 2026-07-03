/*
 * package com.happytohelp.homeloan.controller.ui;
 * 
 * 
 * import com.happytohelp.homeloan.model.Role; import
 * com.happytohelp.homeloan.model.User; import
 * com.happytohelp.homeloan.service.AuthService; import
 * jakarta.servlet.http.HttpSession; import
 * org.springframework.stereotype.Controller; import
 * org.springframework.web.bind.annotation.GetMapping;
 * 
 * @Controller public class UiHomeController {
 * 
 * private final AuthService authService;
 * 
 * public UiHomeController(AuthService authService) { this.authService =
 * authService; }
 * 
 * @GetMapping("/") public String home(HttpSession session) { try { User me =
 * authService.requireLogin(session); return me.getRole() == Role.ADMIN ?
 * "redirect:/ui/admin/dashboard" : "redirect:/ui/customer/dashboard"; } catch
 * (Exception e) { return "redirect:/ui/login"; } }
 * 
 * @GetMapping("/ui/403") public String forbidden() { return "ui/403"; } }
 */


package com.happytohelp.homeloan.controller.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UiHomeController {

    @GetMapping("/")
    public String home() {
        // public home page
        return "ui/public/home";
    }

    @GetMapping("/ui/403")
    public String forbidden() {
        return "ui/403";
    }
}