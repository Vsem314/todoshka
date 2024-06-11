package ru.mudrov.todo.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import ru.mudrov.todo.model.Tasks;
import ru.mudrov.todo.model.User;

import java.util.List;

public interface TaskRepository extends JpaRepository<Tasks, Long> {
    List<Tasks> findByUser(User user);
    List<Tasks> findByUserAndCompleted(User user, boolean completed);
}