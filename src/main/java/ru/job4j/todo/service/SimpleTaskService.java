package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.HbnTaskRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleTaskService {
    private HbnTaskRepository taskRepository;

    public void save(Task task) {
        taskRepository.save(task);
    }

    public void update(Task task) {
        taskRepository.update(task);
    }

    public void deleteById(int id) {
        taskRepository.deleteById(id);
    }

    public Optional<Task> findById(int id) {
        return taskRepository.findById(id);
    }

    public Collection<Task> findAll() {
        return taskRepository.findAll();
    }

    public Collection<Task> getDoneList() {
        return taskRepository.getDoneList();
    }

    public void completeTask(Task task) {
        taskRepository.completeTask(task);
    }
}
