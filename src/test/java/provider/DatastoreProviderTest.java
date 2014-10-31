package provider;

import ninja.NinjaTest;
import org.junit.Test;
import org.mongodb.morphia.Datastore;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author leichler
 */
public class DatastoreProviderTest extends NinjaTest {

    @Test
    public void testModeHasUUIDDatabase() throws Exception {
        assertThat(UUID.fromString(getInjector().getInstance(Datastore.class).getDB().getName())).isNotNull();
    }
}
