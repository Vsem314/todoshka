package ru.mudrov.todo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mudrov.todo.model.Tasks;
import ru.mudrov.todo.model.User;
import ru.mudrov.todo.repository.TaskRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Получение задач пользователя
    public List<Tasks> getUserTasks(User user) {
        return taskRepository.findByUserOrderByDueDateAsc(user);
    }

    // Создание новой задачи
    @Transactional
    public Tasks createTask(User user, String title, String description, LocalDate dueDate) {
        Tasks task = new Tasks();
        task.setTitle(title);
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setCompleted(false);
        task.setUser(user);

        return taskRepository.save(task);
    }

    // Переключение статуса задачи
    @Transactional
    public void toggleTaskCompletion(Long taskId) {
        Tasks task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setCompleted(!task.isCompleted());
        taskRepository.save(task);
    }

    // Удаление задачи
    @Transactional
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    // Дополнительные методы (опционально):

    // Получение просроченных задач
    public List<Tasks> getOverdueTasks(User user) {
        return taskRepository.findByUserAndDueDateBeforeAndCompletedFalse(
                user, LocalDate.now());
    }

    // Обновление задачи
    @Transactional
    public Tasks updateTask(Long taskId, String title, String description, LocalDate dueDate) {
        Tasks task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (title != null) task.setTitle(title);
        if (description != null) task.setDescription(description);
        if (dueDate != null) task.setDueDate(dueDate);

        return taskRepository.save(task);
    }
}