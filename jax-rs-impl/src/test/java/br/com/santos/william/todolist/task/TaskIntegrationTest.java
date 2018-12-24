package br.com.santos.william.todolist.task;

import br.com.santos.william.todolist.resource.Id;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.WebApplicationException;
import java.time.LocalDate;

public class TaskIntegrationTest {

    private TaskResources resources;

    @Before
    public void before() {
        this.resources = new TaskResources();
    }

    @Test
    public void insertAndRecoveryTask() {
        final Task task = new Task();
        task.setDescription("Task 1");
        task.setTitle("Task 1");
        task.setStatus(TaskStatus.OPEN);
        final Id inserted = this.resources.insert(task);

        final Task found = this.resources.get(inserted.getId());
        Assert.assertNotNull(found);
    }

    @Test
    public void updateTask() {
        final Task task = new Task();
        task.setDescription("Task 1");
        task.setTitle("Task 1");
        task.setStatus(TaskStatus.OPEN);
        final Id inserted = this.resources.insert(task);

        task.setDescription("task 2");
        this.resources.update(inserted.getId(), task);

        final Task found = this.resources.get(inserted.getId());
        Assert.assertNotNull(found);
        Assert.assertEquals("task 2", found.getDescription());
    }

    @Test
    public void deleteTask() {
        final Task task = new Task();
        task.setDescription("Task 1");
        task.setTitle("Task 1");
        task.setStatus(TaskStatus.OPEN);
        final Id inserted = this.resources.insert(task);
        task.setDescription("task 2");
        this.resources.update(inserted.getId(), task);

        final Task found = this.resources.get(inserted.getId());
        Assert.assertNotNull(found);

        this.resources.delete(inserted.getId());

        try {
            this.resources.get(inserted.getId());
        } catch (WebApplicationException e) {
            Assert.assertEquals(HttpStatus.NOT_FOUND_404, e.getResponse().getStatus());
        }
    }

    @Test
    public void closeTask() {
        final Task task = new Task();
        task.setDescription("Task Open");
        task.setTitle("Task Open");
        task.setStatus(TaskStatus.OPEN);
        final Id inserted = this.resources.insert(task);

        task.setStatus(TaskStatus.CLOSE);
        this.resources.update(inserted.getId(), task);

        final Task found = this.resources.get(inserted.getId());
        Assert.assertNotNull(found);
        Assert.assertNotNull(found.getCloseAt());
        Assert.assertEquals(task.getCloseAt(), found.getCloseAt());
    }

    @Test
    public void reopenTask() {
        final Task task = new Task();
        task.setDescription("Task Open");
        task.setTitle("Task Open");
        task.setStatus(TaskStatus.CLOSE);
        task.setCloseAt(LocalDate.now());
        final Id inserted = this.resources.insert(task);

        task.setStatus(TaskStatus.OPEN);
        this.resources.update(inserted.getId(), task);

        final Task found = this.resources.get(inserted.getId());
        Assert.assertNotNull(found);
        Assert.assertNull(found.getCloseAt());
    }
}
