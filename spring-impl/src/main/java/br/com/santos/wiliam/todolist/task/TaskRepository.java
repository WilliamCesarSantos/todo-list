package br.com.santos.wiliam.todolist.task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author william.santos
 * @since 2018-11-12
 * @version 1.0.0
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Find all task with status informed in parameter
     * 
     * @param status
     * @return {@link List} with task found
     */
    List<Task> findByStatus(final TaskStatus status);
}
