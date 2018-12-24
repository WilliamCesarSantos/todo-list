package br.com.santos.william.todolist.task;

import br.com.santos.william.todolist.persistence.AbstractDAO;

import javax.persistence.EntityManager;
import java.util.List;

public class TaskDAO extends AbstractDAO<Task> {

    public TaskDAO(final EntityManager manager) {
        super(manager, Task.class);
    }

    public List<Task> list() {
        try {
            return super.manager
                    .createQuery("select t from Task t order by id", Task.class)
                    .getResultList();
        } finally {
            this.manager.close();
        }
    }
}
