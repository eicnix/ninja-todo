package dao;

import com.google.inject.Inject;
import model.Message;
import ninja.mongodb.MongoDB;

import java.util.List;

/**
 * @author leichler
 */
public class MessageDAO {

    @Inject
    private MongoDB mongoDB;

    public List<Message> list() {
        return mongoDB.findAll(Message.class);
    }

    public void setMongoDB(MongoDB mongoDB) {
        this.mongoDB = mongoDB;
    }
}
