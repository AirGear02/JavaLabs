package com.lab3.restaurant.controller;

import com.lab3.restaurant.dto.user.UserRegisterDto;
import com.lab3.restaurant.model.UserEntity;
import com.lab3.restaurant.service.user.UserService;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegisterDto userRegistrationDto() {
        return new UserRegisterDto();
    }

    @GetMapping("/register")
    public String showRegisterForm(UserEntity user) {
        return "register";
    }

    @GetMapping("/login")
    public String showLoginForm() { return "login"; }

    @PostMapping("/register")
    public RedirectView registerUserAccount(@ModelAttribute("user") UserRegisterDto registrationDto) {
        userService.save(registrationDto);
        return new RedirectView("/");
    }
}
