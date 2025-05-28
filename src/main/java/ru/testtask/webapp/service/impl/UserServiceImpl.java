package ru.testtask.webapp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.testtask.webapp.common.TelegramUser;
import ru.testtask.webapp.domain.User;
import ru.testtask.webapp.repository.UserRepository;
import ru.testtask.webapp.service.UserService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void register(String initData) {
        userRepository.save(
                mapFromInitDataFieldValues(
                        mapInitData(initData)
                )
        );
    }

    @SneakyThrows
    private User mapFromInitDataFieldValues(Map<String, String> fieldValuesMap) {
        String userJson = fieldValuesMap.get("user");
        TelegramUser telegramUser = objectMapper.readValue(userJson, TelegramUser.class);
        User user = new User();
        user.setTelegramId(telegramUser.id());
        user.setFirstName(telegramUser.firstName());
        user.setLastName(telegramUser.lastName());
        user.setUsername(telegramUser.userName());
        user.setLanguageCode(telegramUser.languageCode());
        return user;
    }

    private Map<String, String> mapInitData(String initData) {
        return Arrays.stream(initData.split("&"))
                .map(param -> param.split("=", 2))
                .collect(Collectors.toMap(
                        arr -> URLDecoder.decode(arr[0], StandardCharsets.UTF_8),
                        arr -> arr.length > 1 ? URLDecoder.decode(arr[1], StandardCharsets.UTF_8) : "",
                        (existing, lastValue) -> lastValue,
                        HashMap::new
                ));
    }
}
