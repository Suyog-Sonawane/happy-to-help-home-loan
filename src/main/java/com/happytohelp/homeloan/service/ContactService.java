/*
 * package com.happytohelp.homeloan.service;
 * 
 * import com.happytohelp.homeloan.model.ContactMessage; import
 * com.happytohelp.homeloan.model.ContactStatus; import
 * com.happytohelp.homeloan.repo.ContactMessageRepository; import
 * org.springframework.beans.factory.annotation.Value; import
 * org.springframework.stereotype.Service;
 * 
 * import java.time.LocalDateTime; import java.util.List;
 * 
 * @Service public class ContactService {
 * 
 * private final ContactMessageRepository repo; private final EmailService
 * emailService;
 * 
 * @Value("${app.support.toEmail}") private String supportToEmail;
 * 
 * public ContactService(ContactMessageRepository repo, EmailService
 * emailService) { this.repo = repo; this.emailService = emailService; }
 * 
 * public ContactMessage createAndNotify(String name, String email, String
 * subject, String message) { ContactMessage cm = new ContactMessage();
 * cm.setName(name); cm.setEmail(email); cm.setSubject(subject);
 * cm.setMessage(message);
 * 
 * cm = repo.save(cm);
 * 
 * String mailSubject = "New Contact Message (#" + cm.getId() + "): " + subject;
 * String body = """ New message received from Contact Us form.
 * 
 * Message ID: %d Name: %s Email: %s Subject: %s
 * 
 * Message: %s """.formatted(cm.getId(), name, email, subject, message);
 * 
 * // email to bank/support inbox emailService.sendTextEmail(supportToEmail,
 * mailSubject, body);
 * 
 * // optional acknowledgement to user (comment out if you don't want)
 * emailService.sendTextEmail( email, "We received your message (#" + cm.getId()
 * + ")", "Dear " + name +
 * ",\n\nThanks for contacting us. Our team will reach out shortly.\n\nReference: "
 * + cm.getId() );
 * 
 * return cm; }
 * 
 * public List<ContactMessage> listAll() { return repo.findAllByOrderByIdDesc();
 * }
 * 
 * public ContactMessage getOrThrow(Long id) { return
 * repo.findById(id).orElseThrow(() -> new
 * RuntimeException("Message not found")); }
 * 
 * public ContactMessage markResolved(Long id) { ContactMessage cm =
 * getOrThrow(id); cm.setStatus(ContactStatus.RESOLVED);
 * cm.setResolvedAt(LocalDateTime.now()); return repo.save(cm); } }
 */



package com.happytohelp.homeloan.service;

import com.happytohelp.homeloan.model.ContactMessage;
import com.happytohelp.homeloan.model.ContactStatus;
import com.happytohelp.homeloan.repo.ContactMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContactService {

    private static final Logger log = LoggerFactory.getLogger(ContactService.class);

    private final ContactMessageRepository repo;
    private final EmailService emailService;

    @Value("${app.support.toEmail}")
    private String supportToEmail;

    public ContactService(ContactMessageRepository repo, EmailService emailService) {
        this.repo = repo;
        this.emailService = emailService;
    }

    public ContactMessage createAndNotify(String name, String email, String subject, String message) {
        ContactMessage cm = new ContactMessage();
        cm.setName(name);
        cm.setEmail(email);
        cm.setSubject(subject);
        cm.setMessage(message);

        // Save FIRST (so admin can see it even if email fails)
        cm = repo.saveAndFlush(cm);

        try {
            String mailSubject = "New Contact Message (#" + cm.getId() + "): " + subject;
            String body = """
                    New message received from Contact Us form.

                    Message ID: %d
                    Name: %s
                    Email: %s
                    Subject: %s

                    Message:
                    %s
                    """.formatted(cm.getId(), name, email, subject, message);

            emailService.sendTextEmail(supportToEmail, mailSubject, body);
        } catch (Exception ex) {
            log.error("Support email failed for contact id={}", cm.getId(), ex);
        }

        try {
            emailService.sendTextEmail(
                    email,
                    "We received your message (#" + cm.getId() + ")",
                    "Dear " + name + ",\n\nThanks for contacting us. Our team will reach out shortly.\n\nReference: " + cm.getId()
            );
        } catch (Exception ex) {
            log.error("Customer acknowledgement email failed for contact id={}", cm.getId(), ex);
        }

        return cm;
    }

    public List<ContactMessage> listAll() {
        return repo.findAllByOrderByIdDesc();
    }

    public ContactMessage getOrThrow(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Message not found"));
    }

    public ContactMessage markResolved(Long id) {
        ContactMessage cm = getOrThrow(id);
        cm.setStatus(ContactStatus.RESOLVED);
        cm.setResolvedAt(LocalDateTime.now());
        return repo.save(cm);
    }
}