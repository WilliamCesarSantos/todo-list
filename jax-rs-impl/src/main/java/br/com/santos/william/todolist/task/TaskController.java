package br.com.santos.william.todolist.task;

import org.eclipse.jetty.http.HttpStatus;

import javax.ws.rs.WebApplicationException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class TaskController {

    private final TaskDAO taskDAO;

    public TaskController(final TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public void insert(final Task task) {
        Objects.requireNonNull(task);
        if (task.getId() != null) {
            throw new WebApplicationException("Task with ID it''s not permitted to create a new", HttpStatus.CONFLICT_409);
        }
        try {
            this.taskDAO.beginTransaction();
            this.taskDAO.insert(task);
            this.taskDAO.commitTransaction();
        } catch (final Exception ex) {
            this.taskDAO.rollbackTransaction();
            throw ex;
        }
    }

    public void update(final Task task) {
        Objects.requireNonNull(task);
        if (task.getId() == null) {
            throw new WebApplicationException("Task without id on update it''s not possible", HttpStatus.UNPROCESSABLE_ENTITY_422);
        }else if (this.taskDAO.findById(task.getId()) == null) {
            throw new WebApplicationException(String.format("ID %d not found on resource", task.getId()), HttpStatus.NOT_FOUND_404);
        }
        try {
            this.taskDAO.beginTransaction();
            if(task.getStatus() == TaskStatus.CLOSE && task.getCloseAt() == null) {
                task.setCloseAt(LocalDate.now());
            } else if(task.getStatus() == TaskStatus.OPEN && task.getCloseAt() != null) {
                task.setCloseAt(null);
            }
            this.taskDAO.update(task);
            this.taskDAO.commitTransaction();
        } catch (final Exception ex) {
            this.taskDAO.rollbackTransaction();
            throw ex;
        }
    }

    public void remove(final Long id) {
        Objects.requireNonNull(id);
        final Task task = this.taskDAO.findById(id);
        if (task == null) {
            throw new WebApplicationException(String.format("ID %d not found on resource", id), HttpStatus.NOT_FOUND_404);
        }
        try {
            this.taskDAO.beginTransaction();
            this.taskDAO.remove(task);
            this.taskDAO.commitTransaction();
        } catch (final Exception ex) {
            this.taskDAO.rollbackTransaction();
            throw ex;
        }
    }

    public Task get(final Long id) {
        Objects.requireNonNull(id);
        final Task task = this.taskDAO.findById(id);
        if (task == null) {
            throw new WebApplicationException(String.format("ID %d not found on resource", id), HttpStatus.NOT_FOUND_404);
        }
        return task;
    }

    public List<Task> list() {
        return this.taskDAO.list();
    }
}
