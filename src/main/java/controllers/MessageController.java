package controllers;

import com.google.inject.Singleton;
import ninja.Result;
import ninja.Results;

/**
 * @author leichler
 */
@Singleton
public class MessageController {

    public Result index() {
        return Results.json().render("Hallo Welt");
    }
}
