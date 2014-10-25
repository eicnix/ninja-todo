package controllers;

import com.google.inject.Singleton;
import model.Todo;
import ninja.Result;
import ninja.Results;

import java.util.Arrays;

/**
 * @author leichler
 */
@Singleton
public class MessageController {

    private Todo todo;

    public Result index() {
        return Results.json().html().template("/views/MessageController/index.html");
    }

    public Result saveTodo(Todo todo) {
        this.todo = todo;
        return Results.json().render("success");
    }

    public Result listTodos() {
        if (todo != null) {
            return Results.json().render(Arrays.asList(todo));
        } else {
            return Results.json().render(Arrays.asList());
        }
    }
}
