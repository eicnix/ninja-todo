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

package dao;

import com.google.inject.Inject;
import model.Task;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

import java.util.List;

/**
 * @author leichler
 */
public class TaskDao {

    private Datastore ds;
    private Class<Task> clazz = Task.class;

    @Inject
    public TaskDao(Datastore ds) {
        this.ds = ds;
    }

    public void save(Object entity) {
        ds.save(entity);
    }

    public long getCount() {
        return ds.getCount(clazz);
    }

    public Task get(Task task) {
        return ds.get(task);
    }

    public Task get(ObjectId id) {
        return ds.get(clazz, id);
    }

    public List<Task> getAll() {
        return ds.find(clazz).asList();
    }

    public void delete(ObjectId objectId) {
        ds.delete(clazz, objectId);
    }
}
