package com.timkoar.tkserver;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/contact")
@Validated
@CrossOrigin(origins = "http://localhost:3000")
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);
    private final JavaMailSender mailSender;
    private final ContactSubmissionRepository contactSubmissionRepository;
    private static final String UPLOADED_FOLDER = "C:/Users/timothy.koar/Downloads";
    public ContactController(JavaMailSender mailSender, ContactSubmissionRepository contactSubmissionRepository) {
        this.mailSender = mailSender;
        this.contactSubmissionRepository = contactSubmissionRepository;
    }

    @PostMapping
    public void sendContactEmail(
            @Valid @RequestBody ContactSubmission form,
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException, MessagingException {

        // handle the file upload if we have one
        if (file != null && !file.isEmpty()) {
            // convert the file to byte array
            byte[] bytes = file.getBytes();
            // path on our server where we will store the files they uploaded
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());

            // write the files to the path with the byte array
            Files.write(path, bytes);

            // Set the file name in the form
            form.setFileName(file.getOriginalFilename());
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo("koartimothy@gmail.com");
        helper.setSubject("New Contact Form Submission");
        helper.setText("Name: " + form.getName() + "\nMessage: " + form.getMessage());

        // Attach the file
        if (file != null && !file.isEmpty()) {
            helper.addAttachment(file.getOriginalFilename(), file);
            logger.info("File attached to email: " + file.getOriginalFilename());
        }
        mailSender.send(message);
    }
}
