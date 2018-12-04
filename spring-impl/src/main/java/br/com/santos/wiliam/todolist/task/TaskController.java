package br.com.santos.wiliam.todolist.task;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.santos.wiliam.todolist.exception.ResourceConflictException;
import br.com.santos.wiliam.todolist.exception.ResourceNotFoundException;

/**
 * @author william.santos
 * @since 2018-11-12
 * @version 1.0.0
 */
@RestController
public class TaskController {

    private final TaskRepository repository;

    /**
     * @param repository
     */
    public TaskController(final TaskRepository repository) {
        this.repository = repository;
    }

    /**
     * List all {@link Task} on database
     * 
     * @param pageable
     * @return {@link Page}
     */
    @GetMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<Task> list(final Pageable pageable) {
        Objects.requireNonNull(pageable, "pageable cannot be null");
        return this.repository.findAll(pageable);
    }

    /**
     * Find {@link Task} on database, used taskId to found
     * 
     * @param taskId
     * @return {@link Task}
     */
    @GetMapping(value = "/tasks/{taskId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Task get(@PathVariable final Long taskId) {
        return Optional.ofNullable(taskId)
                        .map(this.repository::findById)
                        .orElseThrow(ResourceNotFoundException::new)
                        .orElseThrow(ResourceNotFoundException::new);
    }

    /**
     * Insert a new {@link Task} on database. Valid required fields
     * 
     * @param toInsert
     *            - Cannot be have id
     * @return {@link Task}
     */
    @PostMapping(value = "/tasks", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Task> insert(@Valid @RequestBody final Task toInsert) {
        Objects.requireNonNull(toInsert, "toInsert cannot be null");
        if (toInsert.getId() != null) {
            throw new ResourceConflictException();
        }
        final Task saved = this.repository.save(toInsert);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /**
     * Update {@link Task} with new values. Valid requires fields
     * 
     * @param taskId
     *            - Id of task to update
     * @param toUpdate
     *            - task to update. Does not requires id
     * @return {@link Task}
     */
    @PutMapping(value = "/tasks/{taskId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Task update(@PathVariable final Long taskId, @Valid @RequestBody final Task toUpdate) {
        Objects.requireNonNull(toUpdate);
        return Optional.ofNullable(taskId)
                    .map(this.repository::findById)
                    .map(optional -> optional.orElse(null))
                    .map(task -> {
                        task.setTitle(toUpdate.getTitle());
                        task.setDescription(toUpdate.getDescription());
                        task.setStatus(toUpdate.getStatus());
                        this.updateCloseAt(task, toUpdate.getClosedAt());
                        return this.repository.save(task);
                    }).orElseThrow(ResourceNotFoundException::new);
    }

    private void updateCloseAt(final Task task, final LocalDate closedAt) {
        if (task.getStatus() == TaskStatus.CLOSE) {
            final LocalDate newClosedAt = Optional.ofNullable(closedAt).orElse(LocalDate.now());
            task.setClosedAt(newClosedAt);
        } else if (task.getStatus() == TaskStatus.OPEN) {
            task.setClosedAt(null);
        }
    }

    /**
     * Delete {@link Task} on database
     * 
     * @param taskId
     *            - Id of task to delete
     * @return {@link ResponseEntity}
     */
    @DeleteMapping(value = "/tasks/{taskId}")
    public ResponseEntity<?> delete(@PathVariable final Long taskId) {
        final Task toDelete = get(taskId);
        this.repository.delete(toDelete);
        return ResponseEntity.noContent().build();
    }
}
