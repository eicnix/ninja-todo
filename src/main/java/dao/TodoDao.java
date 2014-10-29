package dao;

import com.google.inject.Inject;
import model.Todo;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

/**
 * @author leichler
 */
public class TodoDao {

    private Datastore ds;
    private Class<Todo> clazz = Todo.class;

    @Inject
    public TodoDao(Datastore ds) {
        this.ds = ds;
    }

    public void save(Object entity) {
        ds.save(entity);
    }

    public long getCount() {
        return ds.getCount(clazz);
    }

    public Todo get(Todo todo) {
        return ds.get(todo);
    }

    public Todo get(ObjectId id) {
        return ds.get(clazz, id);
    }
}
