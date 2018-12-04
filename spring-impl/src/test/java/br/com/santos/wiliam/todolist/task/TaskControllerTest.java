package br.com.santos.wiliam.todolist.task;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.santos.wiliam.todolist.exception.ResourceConflictException;
import br.com.santos.wiliam.todolist.exception.ResourceNotFoundException;

/**
 * Test all methods of {@link TaskController}
 * 
 * @author william.santos
 * @since 2018-11-13
 * @version 1.0.0
 */
@RunWith(SpringRunner.class)
public class TaskControllerTest {

    @Mock
    private TaskRepository repository;

    private TaskController controller;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        this.controller = new TaskController(this.repository);
    }

    @Test
    public void listAllTasksWhenDoesNotExistAnyItOnDatabase() {
        final Pageable pageable = Mockito.mock(Pageable.class);
        final Page<Task> page = Mockito.mock(Page.class);
        Mockito.when(this.repository.findAll(pageable)).thenReturn(page);

        final Page<Task> found = this.controller.list(pageable);
        Assert.assertNotNull(found);
        Assert.assertEquals(page, found);
    }

    @Test
    public void listAllTasksWhenDoesExistsSomeItOnDatabase() {
        final Pageable pageable = Mockito.mock(Pageable.class);
        Mockito.when(this.repository.findAll(pageable)).thenReturn(null);

        final Page<Task> found = this.controller.list(pageable);
        Assert.assertNull(found);
    }

    @Test(expected = NullPointerException.class)
    public void listNotAcceptNullOnParameterExceptionIsExpected() {
        this.controller.list(null);
    }

    @Test
    public void getTaskWhenExitingOnDatabase() {
        final Long taskId = System.currentTimeMillis();
        final Task task = new Task();
        task.setId(taskId);

        Mockito.when(this.repository.findById(taskId)).thenReturn(Optional.of(task));

        final Task found = this.controller.get(taskId);
        Assert.assertNotNull(found);
        Assert.assertEquals(task, found);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getTaskWhenDoesNotExistOnDatabaseExceptionIsExpected() {
        final Long taskId = System.currentTimeMillis();

        Mockito.when(this.repository.findById(taskId)).thenReturn(Optional.empty());
        this.controller.get(taskId);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getNotAcceptNullOnTaskIdParameterExpcetionIsExpected() {
        this.controller.get(null);
    }

    @Test
    public void createANewTaskReturnsCreated() {
        final Long taskId = System.currentTimeMillis();
        final Task task = new Task();
        task.setDescription("Unit-Test");
        task.setTitle("Unit-Test");
        Mockito.doAnswer(invocation -> {
            final Task toInsert = invocation.getArgument(0);;
            toInsert.setId(taskId);
            return toInsert;
        }).when(this.repository).save(task);

        final ResponseEntity<Task> response = this.controller.insert(task);
        final Task inserted = response.getBody();
        Assert.assertNotNull(inserted);
        Assert.assertEquals(taskId, inserted.getId());
        Assert.assertEquals(task.getDescription(), inserted.getDescription());
        Assert.assertEquals(task.getTitle(), inserted.getTitle());
    }

    @Test
    public void createANewTaskWithOpenStatusAndReturnsCreated() {
        final Task task = new Task();
        task.setStatus(TaskStatus.OPEN);

        Mockito.when(this.repository.save(task)).thenReturn(task);

        final ResponseEntity<Task> response = this.controller.insert(task);
        final Task inserted = response.getBody();
        Assert.assertNotNull(inserted);
        Assert.assertEquals(TaskStatus.OPEN, inserted.getStatus());
    }

    @Test
    public void createANewTaskWithCloseStatusAndReturnsCreated() {
        final Task task = new Task();
        task.setStatus(TaskStatus.CLOSE);

        Mockito.when(this.repository.save(task)).thenReturn(task);

        final ResponseEntity<Task> response = this.controller.insert(task);
        final Task inserted = response.getBody();
        Assert.assertNotNull(inserted);
        Assert.assertEquals(TaskStatus.CLOSE, inserted.getStatus());
    }

    @Test(expected = ResourceConflictException.class)
    public void createANewTaskWithIdExpectionIsExpected() {
        final Long taskId = System.currentTimeMillis();
        final Task task = new Task();
        task.setId(taskId);
        this.controller.insert(task);
    }

    @Test(expected = NullPointerException.class)
    public void createNotAcceptNullOnParameterExceptionIsExpected() {
        this.controller.insert(null);
    }

    @Test
    public void updateExistingTaskAndReturnsTaskUpdated() {
        final Long taskId = System.currentTimeMillis();
        final Task inDb = new Task();
        inDb.setId(taskId);

        Mockito.when(this.repository.findById(taskId)).thenReturn(Optional.of(inDb));
        Mockito.when(this.repository.save(inDb)).thenReturn(inDb);

        final Task toUpdate = new Task();
        toUpdate.setDescription("Unit-Test");
        toUpdate.setTitle("Unit-Test");
        final Task updated = this.controller.update(taskId, toUpdate);

        Assert.assertNotNull(updated);
        Assert.assertEquals(toUpdate.getDescription(), updated.getDescription());
        Assert.assertEquals(toUpdate.getTitle(), updated.getTitle());
        Assert.assertEquals(taskId, updated.getId());
        Assert.assertNull(updated.getClosedAt());

        Mockito.verify(this.repository, Mockito.times(1)).save(inDb);
    }

    @Test
    public void updateTaskToCloseStatus() {
        final Long taskId = System.currentTimeMillis();
        final Task inDb = new Task();
        inDb.setId(taskId);
        inDb.setStatus(TaskStatus.OPEN);

        Mockito.when(this.repository.findById(taskId)).thenReturn(Optional.of(inDb));
        Mockito.when(this.repository.save(inDb)).thenReturn(inDb);

        final Task toUpdate = new Task();
        toUpdate.setStatus(TaskStatus.CLOSE);
        final Task updated = this.controller.update(taskId, toUpdate);

        Assert.assertNotNull(updated);
        Assert.assertEquals(taskId, updated.getId());
        Assert.assertEquals(TaskStatus.CLOSE, updated.getStatus());
        Assert.assertNotNull(updated.getClosedAt());

        Mockito.verify(this.repository, Mockito.times(1)).save(inDb);
    }

    @Test
    public void updateTaskToCloseStatusWithClosedAt() {
        final Long taskId = System.currentTimeMillis();
        final Task inDb = new Task();
        inDb.setId(taskId);
        inDb.setStatus(TaskStatus.OPEN);

        Mockito.when(this.repository.findById(taskId)).thenReturn(Optional.of(inDb));
        Mockito.when(this.repository.save(inDb)).thenReturn(inDb);

        final LocalDate closedAt = LocalDate.now();
        final Task toUpdate = new Task();
        toUpdate.setStatus(TaskStatus.CLOSE);
        toUpdate.setClosedAt(closedAt);
        final Task updated = this.controller.update(taskId, toUpdate);

        Assert.assertNotNull(updated);
        Assert.assertEquals(taskId, updated.getId());
        Assert.assertEquals(TaskStatus.CLOSE, updated.getStatus());
        Assert.assertNotNull(updated.getClosedAt());
        Assert.assertEquals(closedAt, updated.getClosedAt());

        Mockito.verify(this.repository, Mockito.times(1)).save(inDb);
    }

    @Test
    public void updateClosedAtOnClosedTask() {
        final Long taskId = System.currentTimeMillis();
        final Task inDb = new Task();
        inDb.setId(taskId);
        inDb.setStatus(TaskStatus.CLOSE);
        inDb.setClosedAt(LocalDate.now());

        Mockito.when(this.repository.findById(taskId)).thenReturn(Optional.of(inDb));
        Mockito.when(this.repository.save(inDb)).thenReturn(inDb);

        final LocalDate closedAt = LocalDate.now().minusDays(1);
        final Task toUpdate = new Task();
        toUpdate.setStatus(TaskStatus.CLOSE);
        toUpdate.setClosedAt(closedAt);
        final Task updated = this.controller.update(taskId, toUpdate);

        Assert.assertNotNull(updated);
        Assert.assertEquals(taskId, updated.getId());
        Assert.assertEquals(TaskStatus.CLOSE, updated.getStatus());
        Assert.assertNotNull(updated.getClosedAt());
        Assert.assertEquals(closedAt, updated.getClosedAt());

        Mockito.verify(this.repository, Mockito.times(1)).save(inDb);
    }

    @Test
    public void updateTaskToOpenStatus() {
        final Long taskId = System.currentTimeMillis();
        final Task inDb = new Task();
        inDb.setId(taskId);
        inDb.setStatus(TaskStatus.CLOSE);
        inDb.setClosedAt(LocalDate.now());

        Mockito.when(this.repository.findById(taskId)).thenReturn(Optional.of(inDb));
        Mockito.when(this.repository.save(inDb)).thenReturn(inDb);

        final Task toUpdate = new Task();
        toUpdate.setStatus(TaskStatus.OPEN);
        final Task updated = this.controller.update(taskId, toUpdate);

        Assert.assertNotNull(updated);
        Assert.assertEquals(taskId, updated.getId());
        Assert.assertEquals(TaskStatus.OPEN, updated.getStatus());
        Assert.assertNull(updated.getClosedAt());

        Mockito.verify(this.repository, Mockito.times(1)).save(inDb);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateWhenDoesNotExistItOnDatabaseExpcetionIsExcepted() {
        final Long taskId = System.currentTimeMillis();
        Mockito.when(this.repository.findById(taskId)).thenReturn(null);

        this.controller.update(taskId, new Task());
    }

    @Test(expected = NullPointerException.class)
    public void updateTaskWithNullOnTaskParameterExpectionIsExpected() {
        this.controller.update(System.currentTimeMillis(), null);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateTaskWithNullOnIdParameterExceptionIsExpected() {
        this.controller.update(null, new Task());
    }

    // @Test
    // public void updateClosedAt

    @Test
    public void deleteExistingTaskOnDatabase() {
        final Long taskId = System.currentTimeMillis();
        final Task task = new Task();
        task.setId(taskId);

        Mockito.when(this.repository.findById(taskId)).thenReturn(Optional.of(task));

        final ResponseEntity<?> response = this.controller.delete(taskId);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        Mockito.verify(this.repository, Mockito.times(1)).delete(task);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteTaskWhenDoesNotExistItOnDatabaseExpectionIsExpected() {
        final Long taskId = System.currentTimeMillis();
        Mockito.when(this.repository.findById(taskId)).thenReturn(Optional.empty());

        this.controller.delete(taskId);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteTaskWithNullOnTaskIdParameterExpcetionIsExpected() {
        this.controller.delete(null);
    }
}
