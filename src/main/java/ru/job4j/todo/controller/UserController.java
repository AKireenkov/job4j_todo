package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.SimpleUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.ZoneId;

@AllArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {
    private final SimpleUserService userService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "users/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model, HttpServletRequest request) {
        var userOptional = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (userOptional.isEmpty()) {
            model.addAttribute("message", "Логин или пароль введены неверно");
            return "errors/404";
        }
        var session = request.getSession();
        session.setAttribute("user", userOptional.get());
        return "redirect:/tasks";
    }

    @GetMapping("/register")
    public String getRegistrationPage(Model model) {
        model.addAttribute("timeZones", userService.timeZones());
        return "users/registration";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model,
                           @RequestParam(value = "zone.getID()") String zoneId) {
        userService.save(user, ZoneId.of(zoneId));
        var savedUser = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (savedUser.isEmpty()) {
            model.addAttribute("message", "Пользователь с таким логином уже существует");
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }
}
