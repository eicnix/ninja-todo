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
