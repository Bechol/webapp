package ru.testtask.webapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.testtask.webapp.config.AppProperties;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final AppProperties appProperties;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("crypto_key", appProperties.getCryptoKey());
        model.addAttribute("bot_token", appProperties.getBotToken());
        return "index";
    }
}
