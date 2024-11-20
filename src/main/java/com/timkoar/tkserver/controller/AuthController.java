package com.timkoar.tkserver.controller;

import com.timkoar.tkserver.model.user.User;
import com.timkoar.tkserver.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("username is already in user");
        }
        userService.saveUser(user);
        return ResponseEntity.ok("User created successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        // placeholder until we get the jwt logic setup
        Optional<User> exitingUser = userService.findByUsername(user.getUsername());
        if (exitingUser.isPresent() && exitingUser.get().getPassword().equals(user.getPassword())) {
            return ResponseEntity.ok("login was successful");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("incorrect username and password combination");
    }
}
