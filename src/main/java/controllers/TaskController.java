package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dao.TaskDao;
import model.Task;
import ninja.Result;
import ninja.Results;
import ninja.jaxy.DELETE;
import ninja.jaxy.GET;
import ninja.jaxy.PUT;
import ninja.jaxy.Path;
import ninja.params.PathParam;
import org.bson.types.ObjectId;

/**
 * @author leichler
 */
@SuppressWarnings("UnusedDeclaration")
@Singleton
@Path("/task")
public class TaskController {

    private TaskDao dao;

    @Inject
    public TaskController(TaskDao dao) {
        this.dao = dao;
    }

    @GET
    @Path("*")
    public Result index() {
        return Results.ok().html().template("/views/TaskController/index.html");
    }

    @PUT
    @Path("/api")
    public Result saveTask(Task task) {
        dao.save(task);
        return Results.json().render(task);
    }

    @GET
    @Path("/api")
    public Result listTasks() {
        return Results.json().render(dao.getAll());
    }

    @DELETE
    @Path("/api/{id}")
    public Result deleteTask(@PathParam("id") String id) {
        dao.delete(new ObjectId(id));
        return Results.json().render("success");
    }
}
