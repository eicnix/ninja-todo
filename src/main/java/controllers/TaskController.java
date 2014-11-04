package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import dao.TaskDao;
import model.Task;
import ninja.Result;
import ninja.Results;

/**
 * @author leichler
 */
@SuppressWarnings("UnusedDeclaration")
@Singleton
public class TaskController {

    private TaskDao dao;

    @Inject
    public TaskController(TaskDao dao) {
        this.dao = dao;
    }

    public Result index() {
        return Results.json().html().template("/views/MessageController/index.html");
    }

    public Result saveTask(Task task) {
        dao.save(task);
        return Results.json().render(task);
    }

    public Result listTasks() {
        return Results.json().render(dao.getAll());
    }
}
