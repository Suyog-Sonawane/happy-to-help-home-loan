package com.happytohelp.homeloan.service;



import com.happytohelp.homeloan.dto.LoginRequest;
import com.happytohelp.homeloan.dto.RegisterRequest;
import com.happytohelp.homeloan.exception.BadRequestException;
import com.happytohelp.homeloan.exception.UnauthorizedException;
import com.happytohelp.homeloan.model.Role;
import com.happytohelp.homeloan.model.User;
import com.happytohelp.homeloan.repo.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public static final String SESSION_USER_ID = "USER_ID";

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerCustomer(RegisterRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new BadRequestException("Email already registered");
        }
        User u = new User();
        u.setName(req.getName());
        u.setEmail(req.getEmail());
        u.setMobile(req.getMobile());
        u.setRole(Role.CUSTOMER);
        u.setPasswordHash(encoder.encode(req.getPassword()));
        return userRepository.save(u);
    }

    public User login(LoginRequest req, HttpSession session) {
        User u = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid email/password"));

        if (!encoder.matches(req.getPassword(), u.getPasswordHash())) {
            throw new UnauthorizedException("Invalid email/password");
        }

        session.setAttribute(SESSION_USER_ID, u.getId());
        return u;
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

    public User requireLogin(HttpSession session) {
        Object id = session.getAttribute(SESSION_USER_ID);
        if (id == null) throw new UnauthorizedException("Please login");
        Long userId = (Long) id;
        return userRepository.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("Session user not found"));
    }

    public User requireAdmin(HttpSession session) {
        User u = requireLogin(session);
        if (u.getRole() != Role.ADMIN) throw new UnauthorizedException("Admin access required");
        return u;
    }
}