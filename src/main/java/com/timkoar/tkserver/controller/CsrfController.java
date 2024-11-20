package com.timkoar.tkserver.controller;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class CsrfController {

    private static final Logger logger = LoggerFactory.getLogger(CsrfController.class);
    @GetMapping("/csrf-token")
    public CsrfToken csrfToken(HttpServletRequest request) {
        logger.info("CSRF Token retrieved");
        CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        System.out.println("CSRF Token: " + token);
        return (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    }
}
