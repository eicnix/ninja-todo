/*
 * Copyright (C) 2014 Lukas Eichler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
