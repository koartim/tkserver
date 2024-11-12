package com.timkoar.tkserver;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/contact")
@Validated
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);
    private final JavaMailSender mailSender;
    private final Bucket bucket = Bucket4j.builder()
            .addLimit(Bandwidth.classic(10, Refill.greedy(10, Duration.ofMinutes(1))))
            .build();

    @Value("${spring.mail.username}")
    private String recipientEmail;

    public ContactController(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> sendContactEmail(
            @RequestPart("name") @NotEmpty String name,
            @RequestPart("email") @NotEmpty String email,
            @RequestPart("message") @NotEmpty @Size(max = 255) String message,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        if (!bucket.tryConsume(1)) {  // Tries to consume 1 token
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Too many requests. Please try again later.");
        }

        try {
            String sanitizedMessage = HtmlUtils.htmlEscape(message);
            String sanitizedSubject = HtmlUtils.htmlEscape(name);
            List<String> allowedMimeTypes = Arrays.asList("application/pdf", "image/jpeg", "image/png", "text/plain");

            if (file != null && !file.isEmpty()) {
                String fileType = file.getContentType();
                if (!allowedMimeTypes.contains(fileType)) {
                    throw new IllegalArgumentException("File type not allowed. Only PDF, JPEG, PNG, and text files are allowed.");
                }
            }
            MimeMessage messageToSend  = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(messageToSend, true);
            helper.setTo(recipientEmail);
            helper.setSubject(sanitizedSubject);
            helper.setText(sanitizedMessage);
            // Set the "Reply-To" header to the user's email address
            helper.setReplyTo(email);

            if (file != null && !file.isEmpty()) {
                helper.addAttachment(file.getOriginalFilename(), file);
                logger.info("File attached to email: " + file.getOriginalFilename());
            }
            mailSender.send(messageToSend);
            return ResponseEntity.ok("Email sent successfully!");
        } catch (MessagingException e) {
            logger.error("Failed to send email", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email. Please try again later.");
        }
    }
}
