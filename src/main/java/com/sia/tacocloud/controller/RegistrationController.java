package com.sia.tacocloud.controller;

import com.sia.tacocloud.controller.model.Registration;
import com.sia.tacocloud.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public RegistrationController(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @GetMapping
    public String registerForm(Model model){
        model.addAttribute("registration",new Registration());
        return "registration";
    }

    @PostMapping
    public String processRegistration(Registration registration){
        userRepo.save(registration.toUser(encoder));
        return "redirect:/login";
    }
}

