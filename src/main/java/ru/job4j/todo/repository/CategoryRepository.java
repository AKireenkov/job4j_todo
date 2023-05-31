package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CategoryRepository {

    private final CrudRepository crudRepository;

    public Optional<Category> findById(int id) {
        return crudRepository.optional("from Category where id=:fId", Category.class,
                Map.of("fId", id));
    }

    public Collection<Category> findAll() {
        return crudRepository.query("FROM Category", Category.class).stream().toList();
    }

}
