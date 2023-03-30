package ru.job4j.todo.repository;

import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStore {

    boolean save(User user);

    Optional<User> findByLoginAndPassword(String login, String password);

    Collection<User> findAll();

    boolean deleteById(int id);
}
