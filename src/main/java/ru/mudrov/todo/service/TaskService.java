package ru.mudrov.todo.service;

import org.springframework.stereotype.Service;
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

    public List<Tasks> getUserTasks(User user) {
        return taskRepository.findByUser(user);
    }

    public Tasks createTask(User user, String title, String description, LocalDate dueDate) {
        Tasks task = new Tasks();
        task.setTitle(title);
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setCompleted(false);
        task.setUser(user);
        return taskRepository.save(task);
    }

    public void toggleTaskCompletion(Long taskId) {
        Tasks task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        task.setCompleted(!task.isCompleted());
        taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}