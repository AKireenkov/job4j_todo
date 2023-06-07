package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.SimpleTaskService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private static final int WEEK = 7;
    private final SimpleTaskService taskService;

    private final CategoryService categoryService;

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
        //  model.addAttribute("category", ta)
        return "task/one";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(Model model, @PathVariable int id) {
        taskService.deleteById(id);
        if (taskService.findById(id).isPresent()) {
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
        taskService.completeTask(task.get());
        if (task.get().isDone()) {
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
        taskService.update(task);
        return "redirect:/tasks";
    }

    @GetMapping("/create")
    public String getCreationPage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "task/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task,
                         @RequestParam(value = "category.id") List<Integer> categoriesId) {
        task.setCreated(LocalDateTime.now());
        Set<Category> categories = new HashSet<>();
        categoriesId.forEach(c -> categories.add(categoryService.findById(c).get()));
        task.setCategories(categories);
        taskService.save(task);
        return "redirect:/tasks";
    }
}
