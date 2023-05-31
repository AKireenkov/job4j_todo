package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbnTaskRepository {
    private final CrudRepository crudRepository;

    public Task save(Task task) {
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    public void update(Task task) {
        crudRepository.run(session -> session.update(task));
    }

    public void deleteById(int id) {
        crudRepository.run(
                "delete from Task where id = :fId",
                Map.of("fId", id)
        );
    }

    public Optional<Task> findById(int id) {
        return crudRepository.optional("from Task t JOIN FETCH t.categories where Task.id=:fId", Task.class,
                Map.of("fId", id));
    }

    public Collection<Task> findAll() {
        return crudRepository.query("FROM Task f JOIN FETCH f.priority", Task.class);
    }

    public Collection<Task> getDoneList() {
        return crudRepository.query("from Task where done=true", Task.class);
    }

    public void completeTask(Task task) {
        crudRepository.run("UPDATE Task SET done=true WHERE id=:taskId", Map.of("taskId", task.getId()));
    }
}
