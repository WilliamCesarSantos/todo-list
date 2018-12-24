package br.com.santos.william.todolist.persistence;

public interface Transactional {

    void beginTransaction();

    void commitTransaction();

    void rollbackTransaction();
}
