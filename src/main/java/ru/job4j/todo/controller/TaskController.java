package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private static final int WEEK = 7;
    private final TaskService taskService;

    @GetMapping
    public String getAllTasks(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "task/list";
    }

    @GetMapping("/done")
    public String getDoneTasks(Model model) {
        model.addAttribute("tasks", taskService.getDoneList());
        return "task/list";
    }

    @GetMapping("/new")
    public String getNewTasks(Model model) {
        List<Task> doneList = taskService.findAll()
                .stream()
                .filter(t -> t.getCreated().isAfter(LocalDateTime.now().minusDays(WEEK)))
                .toList();
        model.addAttribute("tasks", doneList);
        return "task/list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id) {
        var task = taskService.findById(id);
        if (task.isEmpty()) {
            model.addAttribute("message", "Не удалось найти задание");
            return "404";
        }
        model.addAttribute("task", task.get());
        return "task/one";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(Model model, @PathVariable int id) {
        var isDeleted = taskService.deleteById(id);
        if (!isDeleted) {
            model.addAttribute("message", "Не удалось удалить задание");
            return "404";
        }
        return "redirect:/tasks";
    }

    @PostMapping("/complete/{id}")
    public String completeTask(Model model, @PathVariable int id) {
        var task = taskService.findById(id);
        if (task.isEmpty()) {
            model.addAttribute("message", "Не удалось найти задание");
            return "errors/404";
        }
        var isUpdated = taskService.completeTask(task.get());
        if (!isUpdated) {
            model.addAttribute("message", "Не удалось выполнить задание");
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("edit/{id}")
    public String editTask(Model model, @PathVariable int id) {
        var task = taskService.findById(id);
        if (task.isEmpty()) {
            model.addAttribute("message", "Не удалось найти задание");
            return "errors/404";
        }
        model.addAttribute("task", task.get());
        return "task/edit";
    }

    @PostMapping("/update")
    public String update(Model model, @ModelAttribute Task task) {
        var isUpdated = taskService.update(task);
        if (!isUpdated) {
            model.addAttribute("message", "Не удалось обновить задачу");
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/create")
    public String getCreationPage() {
        return "task/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task) {
        task.setCreated(LocalDateTime.now());
        taskService.save(task);
        return "redirect:/tasks";
    }
}
