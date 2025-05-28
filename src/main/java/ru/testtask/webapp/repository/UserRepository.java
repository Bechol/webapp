package ru.testtask.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.testtask.webapp.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
