package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.HbnUserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleUserService {
    private HbnUserRepository userStore;

    public void save(User user) {
        userStore.save(user);
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        return userStore.findByLoginAndPassword(login, password);
    }
}
