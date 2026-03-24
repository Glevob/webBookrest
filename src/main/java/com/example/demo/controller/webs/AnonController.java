package com.example.demo.controller.webs;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AnonController {

    @GetMapping("/anon")
    public String anon(Model model) {
        model.addAttribute("title", "Главная страница");
        return "anon";
    }
}
