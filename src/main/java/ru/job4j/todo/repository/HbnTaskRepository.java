package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.ArrayList;
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
        boolean isSave = false;
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
            isSave = true;
        } catch (Exception ex) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return isSave;
    }

    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        boolean isUpdate = false;
        try {
            session.beginTransaction();
            session.update(task);
            session.getTransaction().commit();
            isUpdate = true;
        } catch (Exception ex) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return isUpdate;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = sf.openSession();
        boolean isDelete = false;
        try {
            session.beginTransaction();
            Task task = new Task();
            task.setId(id);
            session.delete(task);
            session.getTransaction().commit();
            isDelete = true;
        } catch (Exception ex) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return isDelete;
    }

    @Override
    public Optional<Task> findById(int id) {
        Session session = sf.openSession();
        Task task = null;
        try {
            session.beginTransaction();
            task = session.get(Task.class, id);
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return Optional.ofNullable(task);
    }

    @Override
    public Collection<Task> findAll() {
        Session session = sf.openSession();
        List<Task> tasks = new ArrayList<>();
        try {
            session.beginTransaction();
            tasks = session.createQuery("from Task", Task.class).list();
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return tasks;
    }

    @Override
    public Collection<Task> getDoneList() {
        Session session = sf.openSession();
        List<Task> tasks = new ArrayList<>();
        try {
            session.beginTransaction();
            tasks = session.createQuery("from Task where done=true", Task.class).list();
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return tasks;
    }

    @Override
    public boolean completeTask(Task task) {
        Session session = sf.openSession();
        boolean isUpdate = false;
        try {
            session.beginTransaction();
            task.setDone(true);
            session.update(task);
            session.getTransaction().commit();
            isUpdate = true;
        } catch (Exception ex) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return isUpdate;
    }
}
