/*
 * package com.happytohelp.homeloan.config;
 * 
 * 
 * 
 * import com.happytohelp.homeloan.model.Role; import
 * com.happytohelp.homeloan.model.User; import
 * com.happytohelp.homeloan.service.AuthService; import
 * jakarta.servlet.http.HttpServletRequest; import
 * jakarta.servlet.http.HttpServletResponse; import
 * jakarta.servlet.http.HttpSession; import
 * org.springframework.stereotype.Component; import
 * org.springframework.web.servlet.HandlerInterceptor;
 * 
 * @Component public class AuthInterceptor implements HandlerInterceptor {
 * 
 * private final AuthService authService;
 * 
 * public AuthInterceptor(AuthService authService) { this.authService =
 * authService; }
 * 
 * @Override public boolean preHandle(HttpServletRequest request,
 * HttpServletResponse response, Object handler) throws Exception {
 * 
 * String path = request.getRequestURI();
 * 
 * // Allow public pages and resources if (path.equals("/") ||
 * path.startsWith("/ui/login") || path.startsWith("/ui/register") ||
 * path.startsWith("/ui/403") || path.startsWith("/error") ||
 * path.startsWith("/css") || path.startsWith("/js") ||
 * path.startsWith("/images") || path.startsWith("/webjars")) { return true; }
 * 
 * // Only protect /ui/** if (!path.startsWith("/ui/")) return true;
 * 
 * HttpSession session = request.getSession(false); if (session == null) {
 * response.sendRedirect("/ui/login"); return false; }
 * 
 * User me; try { me = authService.requireLogin(session); } catch (Exception ex)
 * { response.sendRedirect("/ui/login"); return false; }
 * 
 * // Role checks if (path.startsWith("/ui/admin") && me.getRole() !=
 * Role.ADMIN) { response.sendRedirect("/ui/403"); return false; }
 * 
 * if (path.startsWith("/ui/customer") && me.getRole() != Role.CUSTOMER) {
 * response.sendRedirect("/ui/403"); return false; }
 * 
 * return true; } }
 */



package com.happytohelp.homeloan.config;

import com.happytohelp.homeloan.model.Role;
import com.happytohelp.homeloan.model.User;
import com.happytohelp.homeloan.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    public AuthInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String path = request.getRequestURI();

        // PUBLIC pages + static resources
        if (path.equals("/") ||
                path.startsWith("/ui/login") ||
                path.startsWith("/ui/register") ||
                path.startsWith("/ui/public/") ||
                path.startsWith("/ui/403") ||
                path.startsWith("/error") ||
                path.startsWith("/css") || path.startsWith("/js") || path.startsWith("/images") ||
                path.startsWith("/webjars")) {
            return true;
        }

        // protect only /ui/**
        if (!path.startsWith("/ui/")) return true;

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("/ui/login");
            return false;
        }

        User me;
        try {
            me = authService.requireLogin(session);
        } catch (Exception ex) {
            response.sendRedirect("/ui/login");
            return false;
        }

        // role checks
        if (path.startsWith("/ui/admin") && me.getRole() != Role.ADMIN) {
            response.sendRedirect("/ui/403");
            return false;
        }
        if (path.startsWith("/ui/customer") && me.getRole() != Role.CUSTOMER) {
            response.sendRedirect("/ui/403");
            return false;
        }

        return true;
    }
}