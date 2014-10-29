package dao;

import com.github.fakemongo.junit.FongoRule;
import model.Todo;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author leichler
 */
public class TodoDaoTest {

    @Rule
    public FongoRule fongoRule = new FongoRule();

    private TodoDao dao;


    @Before
    public void setUp() throws Exception {
        String dbName = UUID.randomUUID().toString();
        Morphia morphia = new Morphia();
        morphia.map(Todo.class);
        Datastore ds = morphia.createDatastore(fongoRule.getFongo().getMongo(), dbName);
        dao = new TodoDao(ds);
    }

    @Test
    public void save() throws Exception {
        dao.save(new Todo());
        assertThat(dao.getCount()).isEqualTo(1);
    }

    @Test
    public void initialCount() throws Exception {
        assertThat(dao.getCount()).isZero();
    }

    @Test
    public void getWithObjectId() throws Exception {
        Todo todo = new Todo();
        todo.setName("Test");

        dao.save(todo);
        Todo todo2 = dao.get(todo.getId());
        assertThat(todo2.getName()).isEqualTo(todo.getName());
    }

    @Test
    public void getWithObject() throws Exception {
        Todo todo = new Todo();
        todo.setName("Test");

        dao.save(todo);
        Todo todo2 = dao.get(todo);
        assertThat(todo2.getName()).isEqualTo(todo.getName());
    }
}
