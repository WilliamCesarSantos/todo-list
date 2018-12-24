package br.com.santos.william.todolist.persistence;

import javax.persistence.EntityManager;
import java.io.Closeable;
import java.util.List;
import java.util.Objects;

public abstract class AbstractDAO<T> implements Closeable, Transactional {

    protected final EntityManager manager;
    protected final Class<T> clazz;

    public AbstractDAO(final EntityManager manager, final Class<T> clazz) {
        this.manager = manager;
        this.clazz = clazz;
    }

    public T insert(final T object) {
        Objects.requireNonNull(object);
        manager.persist(object);
        return object;
    }

    public T update(final T object) {
        Objects.requireNonNull(object);
        return manager.merge(object);
    }

    public T remove(final T object) {
        Objects.requireNonNull(object);
        manager.remove(object);
        return object;
    }

    public T findById(final Long id) {
        Objects.requireNonNull(id);
        return this.manager.find(this.clazz, id);
    }

    public void beginTransaction() {
        this.manager.getTransaction().begin();
    }

    public void commitTransaction() {
        this.manager.getTransaction().commit();
    }

    public void rollbackTransaction() {
        this.manager.getTransaction().rollback();
    }

    public void close() {
        this.manager.close();
    }

    public abstract List<T> list();

}
