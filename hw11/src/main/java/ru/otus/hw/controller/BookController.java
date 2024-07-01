package ru.otus.hw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController {

    @GetMapping("/")
    public String listAllBooks() {
        return "listbooks";
    }

    @GetMapping("/edit")
    public String editPage() {
        return "editbook";
    }

    @GetMapping("/insert")
    public String insertPage() {
        return "insertbook";
    }

    @GetMapping("/delete")
    public String deletePage() {
        return "deletebook";
    }
}