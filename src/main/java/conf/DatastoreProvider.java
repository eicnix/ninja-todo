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


