package ru.testtask.webapp.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TelegramUser(
        long id,
        @JsonProperty(value = "first_name") String firstName,
        @JsonProperty(value = "last_name") String lastName,
        @JsonProperty(value = "username") String userName,
        @JsonProperty(value = "language_code") String languageCode
) {
}
