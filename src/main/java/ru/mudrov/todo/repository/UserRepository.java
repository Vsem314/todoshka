package ru.mudrov.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mudrov.todo.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
