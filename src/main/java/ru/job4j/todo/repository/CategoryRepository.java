package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CategoryRepository {

    private final CrudRepository crudRepository;

    public Optional<Category> findById(int id) {
        return crudRepository.optional("FROM Category WHERE id=:fId", Category.class,
                Map.of("fId", id));
    }

    public Collection<Category> findAllById(List<Integer> id) {
        return crudRepository.query("FROM Category WHERE id IN :fId", Category.class,
                Map.of("fId", id)).stream().toList();
    }

    public Collection<Category> findAll() {
        return crudRepository.query("FROM Category", Category.class).stream().toList();
    }

}
