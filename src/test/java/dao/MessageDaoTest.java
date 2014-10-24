package dao;

import model.Message;
import ninja.mongodb.MongoDB;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

/**
 * @author leichler
 */
public class MessageDaoTest {

    private MongoDB mongoDB;
    private MessageDAO dao;

    @Before
    public void setUp() throws Exception {
        mongoDB = Mockito.mock(MongoDB.class);

        dao = new MessageDAO();
        dao.setMongoDB(mongoDB);
    }

    @Test
    public void listCallsGetAll() throws Exception {
        dao.list();
        verify(mongoDB).findAll(Message.class);

    }
}
