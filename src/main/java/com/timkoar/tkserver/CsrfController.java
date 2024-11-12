package com.timkoar.tkserver;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController("/api")
public class CsrfController {

    private static final Logger logger = LoggerFactory.getLogger(CsrfController.class);
    @GetMapping("/csrf-token")
    public CsrfToken csrfToken(HttpServletRequest request) {
        logger.info("CSRF Token retrieved");
        return (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    }
}
