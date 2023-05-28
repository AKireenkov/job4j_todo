package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TaskAndCategory {
    private final CrudRepository crudRepository;

    public <T> T create(T model) {
        crudRepository.run(session -> session.persist(model));
        return model;
    }

    public <T> void update(T task) {
        crudRepository.run(session -> session.merge(task));
    }

    public void delete(Integer id) {
        crudRepository.run(
                "delete from Task where id = :fId",
                Map.of("fId", id)
        );
    }

    public <T> List<Task> findAll() {
        return crudRepository.query("from Task", Task.class).stream().toList();
    }

    public Optional<Task> findById(Integer id) {
        return crudRepository.optional("from Task order by id asc", Task.class,
                Map.of("fId", id));
    }
}
