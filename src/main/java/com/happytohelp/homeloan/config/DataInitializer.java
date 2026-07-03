package com.happytohelp.homeloan.config;


//import com.example.homeloan.model.Role;
//import com.example.homeloan.model.User;
//import com.example.homeloan.repo.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.happytohelp.homeloan.model.Role;
import com.happytohelp.homeloan.model.User;
import com.happytohelp.homeloan.repo.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Value("${app.admin.email}") private String adminEmail;
    @Value("${app.admin.password}") private String adminPassword;
    @Value("${app.admin.name}") private String adminName;

    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        userRepository.findByEmail(adminEmail).ifPresentOrElse(u -> {}, () -> {
            User admin = new User();
            admin.setName(adminName);
            admin.setEmail(adminEmail);
            admin.setMobile("9999999999");
            admin.setRole(Role.ADMIN);
            admin.setPasswordHash(encoder.encode(adminPassword));
            userRepository.save(admin);
        });
    }
}