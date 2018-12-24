package br.com.santos.william.todolist.task;

import br.com.santos.william.todolist.persistence.JpaEntityManager;
import br.com.santos.william.todolist.resource.Id;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Objects;

@Path("tasks")
public class TaskResources {

    private final TaskController controller;

    public TaskResources() {
        this.controller = new TaskController(new TaskDAO(JpaEntityManager.getEntityManager()));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> list() {
        return this.controller.list();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Task get(@PathParam("id") Long id) {
        Objects.requireNonNull(id);
        return this.controller.get(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Id insert(@Valid final Task task) {
        Objects.requireNonNull(task);
        this.controller.insert(task);
        return new Id(task.getId());
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Id update(@PathParam("id") Long id, @Valid Task task) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(task);
        task.setId(id);
        this.controller.update(task);
        return new Id(task.getId());
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
        Objects.requireNonNull(id);
        this.controller.remove(id);
    }
}
