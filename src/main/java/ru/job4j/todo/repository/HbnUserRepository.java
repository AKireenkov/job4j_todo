package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbnUserRepository {
    private final CrudRepository crudRepository;

    public Optional<User> save(User user) {
        try {
            crudRepository.run(session -> session.persist(user));
        } catch (Exception ex) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional("from User where login=:login and password=:password", User.class,
                Map.of("login", login, "password", password));
    }

    public Collection<User> findAll() {
        return crudRepository.query("from User", User.class);
    }

    public void deleteById(int id) {
        crudRepository.run("delete from User where id = :fId",
                Map.of("fId", id));
    }
}
