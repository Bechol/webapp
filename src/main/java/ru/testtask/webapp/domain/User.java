package ru.testtask.webapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tst_user")
public class User {

    @Id
    @Column(name = "telegram_id", nullable = false, unique = true)
    private Long telegramId;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "user_name")
    private String username;

    @UpdateTimestamp
    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "language_code")
    private String languageCode;

}
