package com.happytohelp.homeloan.controller.ui;

import com.happytohelp.homeloan.model.User;
import com.happytohelp.homeloan.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class UiAdvice {

    private final AuthService authService;

    public UiAdvice(AuthService authService) {
        this.authService = authService;
    }

    @ModelAttribute("me")
    public User me(HttpSession session) {
        try {
            return authService.requireLogin(session);
        } catch (Exception e) {
            return null;
        }
    }
}