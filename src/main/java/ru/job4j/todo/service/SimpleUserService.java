package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.HbnUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Service
@AllArgsConstructor
@Slf4j
public class SimpleUserService {
    private HbnUserRepository userStore;

    public void save(User user, String userTimeZone) {
        user.setUserTimeZone(userTimeZone);
        userStore.save(user);
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        return userStore.findByLoginAndPassword(login, password);
    }

    public List<TimeZone> timeZones() {
        return userStore.timeZones();
    }
}
