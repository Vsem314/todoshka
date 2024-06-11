package ru.mudrov.todo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mudrov.todo.model.Tasks;
import ru.mudrov.todo.model.User;
import ru.mudrov.todo.service.TaskService;

import java.time.LocalDate;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String tasks(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("tasks", taskService.getUserTasks(user));
        model.addAttribute("newTask", new Tasks());
        return "tasks";
    }

    @PostMapping
    public String addTask(@AuthenticationPrincipal User user,
                          @RequestParam String title,
                          @RequestParam(required = false) String description,
                          @RequestParam(required = false) LocalDate dueDate) {
        taskService.createTask(user, title, description, dueDate);
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/toggle")
    public String toggleTask(@PathVariable Long id) {
        taskService.toggleTaskCompletion(id);
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }
}
