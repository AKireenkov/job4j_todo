package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbnTaskRepository implements TaskRepository {
    private final SessionFactory sf;

    @Override
    public boolean save(Task task) {
        Session session = sf.openSession();
        boolean isSave = true;
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            isSave = false;
        }
        session.close();
        return isSave;
    }

    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        boolean isUpdate = true;
        try {
            session.beginTransaction();
            session.update(task);
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            isUpdate = false;
        }
        session.close();
        return isUpdate;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = sf.openSession();
        boolean isDelete = true;
        try {
            session.beginTransaction();
            Task task = new Task();
            task.setId(id);
            session.delete(task);
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            isDelete = false;
        }
        session.close();
        return isDelete;
    }

    @Override
    public Optional<Task> findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Task task = session.get(Task.class, id);
        session.getTransaction().commit();
        session.close();
        return Optional.ofNullable(task);
    }

    @Override
    public Collection<Task> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Task> tasks = session.createQuery("from Task", Task.class).list();
        session.getTransaction().commit();
        session.close();
        return tasks;
    }
}
