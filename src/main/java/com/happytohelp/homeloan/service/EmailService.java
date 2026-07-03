/*
 * package com.happytohelp.homeloan.service;
 * 
 * 
 * import org.springframework.beans.factory.annotation.Value; import
 * org.springframework.core.io.ByteArrayResource; import
 * org.springframework.mail.javamail.JavaMailSender; import
 * org.springframework.stereotype.Service;
 * 
 * import jakarta.mail.internet.MimeMessage; import
 * org.springframework.mail.javamail.MimeMessageHelper;
 * 
 * @Service public class EmailService {
 * 
 * private final JavaMailSender mailSender;
 * 
 * @Value("${spring.mail.username}") private String from;
 * 
 * public EmailService(JavaMailSender mailSender) { this.mailSender =
 * mailSender; }
 * 
 * public void sendPdfAttachment(String to, String subject, String bodyText,
 * String filename, byte[] pdfBytes) { try { MimeMessage message =
 * mailSender.createMimeMessage(); MimeMessageHelper helper = new
 * MimeMessageHelper(message, true);
 * 
 * helper.setFrom(from); helper.setTo(to); helper.setSubject(subject);
 * helper.setText(bodyText, false); helper.addAttachment(filename, new
 * ByteArrayResource(pdfBytes));
 * 
 * mailSender.send(message); } catch (Exception e) { throw new
 * RuntimeException("Failed to send email: " + e.getMessage(), e); } } }
 */


/*
 * package com.happytohelp.homeloan.service;
 * 
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.beans.factory.annotation.Value; import
 * org.springframework.core.io.ByteArrayResource; import
 * org.springframework.mail.javamail.JavaMailSender; import
 * org.springframework.stereotype.Service;
 * 
 * import jakarta.mail.internet.MimeMessage; import
 * org.springframework.mail.javamail.MimeMessageHelper;
 * 
 * @Service public class EmailService {
 * 
 * private static final Logger log =
 * LoggerFactory.getLogger(EmailService.class);
 * 
 * private final JavaMailSender mailSender;
 * 
 * @Value("${spring.mail.username}") private String from;
 * 
 * public EmailService(JavaMailSender mailSender) { this.mailSender =
 * mailSender; }
 * 
 * public void sendPdfAttachment(String to, String subject, String bodyText,
 * String filename, byte[] pdfBytes) { try {
 * log.info("Attempting email send: from={}, to={}, subject={}, file={}", from,
 * to, subject, filename);
 * 
 * MimeMessage message = mailSender.createMimeMessage(); MimeMessageHelper
 * helper = new MimeMessageHelper(message, true, "UTF-8");
 * 
 * helper.setFrom(from); helper.setTo(to); helper.setSubject(subject);
 * helper.setText(bodyText, false); helper.addAttachment(filename, new
 * ByteArrayResource(pdfBytes));
 * 
 * mailSender.send(message); log.info("Email sent OK to={}", to);
 * 
 * } catch (Exception e) { log.error("Email sending failed", e); throw new
 * RuntimeException("Failed to send email: " + e.getMessage(), e); } } }
 * 
 * 
 * 
 * 
 *//*
	 * 
	 * package com.happytohelp.homeloan.service;
	 * 
	 * import org.springframework.beans.factory.annotation.Value; import
	 * org.springframework.core.io.ByteArrayResource; import
	 * org.springframework.mail.javamail.JavaMailSender; import
	 * org.springframework.stereotype.Service;
	 * 
	 * import jakarta.mail.internet.MimeMessage; import
	 * org.springframework.mail.javamail.MimeMessageHelper;
	 * 
	 * @Service public class EmailService {
	 * 
	 * private final JavaMailSender mailSender;
	 * 
	 * @Value("${spring.mail.username}") private String from;
	 * 
	 * public EmailService(JavaMailSender mailSender) { this.mailSender =
	 * mailSender; }
	 * 
	 * // NEW: simple text email public void sendTextEmail(String to, String
	 * subject, String bodyText) { try { MimeMessage message =
	 * mailSender.createMimeMessage(); MimeMessageHelper helper = new
	 * MimeMessageHelper(message, false, "UTF-8");
	 * 
	 * helper.setFrom(from); helper.setTo(to); helper.setSubject(subject);
	 * helper.setText(bodyText, false);
	 * 
	 * mailSender.send(message); } catch (Exception e) { throw new
	 * RuntimeException("Failed to send email: " + e.getMessage(), e); } }
	 * 
	 * // Existing: PDF attachment public void sendPdfAttachment(String to, String
	 * subject, String bodyText, String filename, byte[] pdfBytes) { try {
	 * MimeMessage message = mailSender.createMimeMessage(); MimeMessageHelper
	 * helper = new MimeMessageHelper(message, true, "UTF-8");
	 * 
	 * helper.setFrom(from); helper.setTo(to); helper.setSubject(subject);
	 * helper.setText(bodyText, false); helper.addAttachment(filename, new
	 * ByteArrayResource(pdfBytes));
	 * 
	 * mailSender.send(message); } catch (Exception e) { throw new
	 * RuntimeException("Failed to send email: " + e.getMessage(), e); } } }
	 */


package com.happytohelp.homeloan.service;

import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Existing: simple text email (throws)
    public void sendTextEmail(String to, String subject, String bodyText) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(bodyText, false);

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }
    }

    // Existing: PDF attachment (throws)
    public void sendPdfAttachment(String to, String subject, String bodyText,
                                  String filename, byte[] pdfBytes) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(bodyText, false);
            helper.addAttachment(filename, new ByteArrayResource(pdfBytes));

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }
    }

    // NEW: SAFE text email (does NOT throw)
    public boolean trySendTextEmail(String to, String subject, String bodyText) {
        try {
            sendTextEmail(to, subject, bodyText);
            return true;
        } catch (Exception ex) {
            log.warn("Email failed (text). to={}, subject={}, reason={}", to, subject, ex.getMessage());
            return false;
        }
    }

    // NEW: SAFE PDF email (does NOT throw)
    public boolean trySendPdfAttachment(String to, String subject, String bodyText,
                                        String filename, byte[] pdfBytes) {
        try {
            sendPdfAttachment(to, subject, bodyText, filename, pdfBytes);
            return true;
        } catch (Exception ex) {
            log.warn("Email failed (pdf). to={}, subject={}, reason={}", to, subject, ex.getMessage());
            return false;
        }
    }
}