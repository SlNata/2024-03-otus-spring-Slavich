package ru.slavich.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "loginpage";
    }

    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }
}
