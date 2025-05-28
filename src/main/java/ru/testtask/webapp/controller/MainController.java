package ru.testtask.webapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.testtask.webapp.config.AppProperties;
import ru.testtask.webapp.domain.User;
import ru.testtask.webapp.service.InitDataValidatorService;
import ru.testtask.webapp.service.UserService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final AppProperties appProperties;
    private final InitDataValidatorService initDataValidatorService;
    private final UserService userService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("crypto_key", appProperties.getCryptoKey());
        model.addAttribute("bot_token", appProperties.getBotToken());
        return "index";
    }

    @GetMapping("/auth")
    public String home(@RequestParam(name = "initData") String initData, Model model) {
        log.info("Auth request with initData: {}", initData);
        if (initDataValidatorService.validate(initData)) {
            model.addAttribute("init_data", initData);
            User user = userService.register(initData);
            log.info("Saved user: {}", user);
        } else {
            model.addAttribute("init_data", "auth failed");
        }
        return "userinfo";
    }
}
