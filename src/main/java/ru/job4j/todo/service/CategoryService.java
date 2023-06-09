package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryRepository categoryRepository;

    public Optional<Category> findById(int id) {
        return categoryRepository.findById(id);
    }

    public Collection<Category> findAllById(List<Integer> id) {
        return categoryRepository.findAllById(id);
    }

    public Collection<Category> findAll() {
        return categoryRepository.findAll();
    }
}
