package com.ra.book.controller;
import com.ra.book.model.service.Category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    CategoryService categoryService;
    @GetMapping
    public String home(Model model) {
        return "home";
    }
}
