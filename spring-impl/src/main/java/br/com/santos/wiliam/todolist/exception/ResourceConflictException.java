package br.com.santos.wiliam.todolist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author william.santos
 * @since 2018-11-13
 * @version 1.0.0
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceConflictException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceConflictException() {
        super();
    }
    
    public ResourceConflictException(final String message) {
        super(message);
    }

    public ResourceConflictException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
