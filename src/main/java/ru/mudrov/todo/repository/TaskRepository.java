package ru.mudrov.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.mudrov.todo.model.Tasks;
import ru.mudrov.todo.model.User;
import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Tasks, Long> {
    // Находим задачи пользователя, отсортированные по дате выполнения
    List<Tasks> findByUserOrderByDueDateAsc(User user);

    // Находим просроченные задачи
    List<Tasks> findByUserAndDueDateBeforeAndCompletedFalse(
            User user, LocalDate date);

    // Альтернативный вариант с JPQL
    @Query("SELECT t FROM Tasks t WHERE t.user = :user AND t.completed = false AND t.dueDate < CURRENT_DATE")
    List<Tasks> findUserOverdueTasks(User user);
}