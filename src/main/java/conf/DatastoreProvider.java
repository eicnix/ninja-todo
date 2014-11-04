package conf;

import com.github.fakemongo.Fongo;
import com.google.inject.Inject;
import com.google.inject.Provider;
import ninja.mongodb.MongoDB;
import ninja.utils.NinjaProperties;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.UUID;

/**
 * @author leichler
 */
public class DatastoreProvider implements Provider<Datastore> {

    private static final String MORPHIA_PACKAGE = "ninja.morphia.package";
    private NinjaProperties properties;
    private MongoDB mongoDB;

    @Inject
    public DatastoreProvider(NinjaProperties properties, MongoDB mongoDB) {
        this.properties = properties;
        this.mongoDB = mongoDB;
    }

    public Datastore get() {
        if (properties.isTest()) {
            return initiateDatestore();
        } else {
            return mongoDB.getDatastore();
        }
    }

    private Datastore initiateDatestore() {
        String dbName = UUID.randomUUID().toString();
        Fongo fongo = new Fongo(dbName);
        Morphia morphia = new Morphia();
        morphia.mapPackage(properties.get(MORPHIA_PACKAGE));
        return morphia.createDatastore(fongo.getMongo(), dbName);
    }
}


