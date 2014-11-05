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

import com.github.fakemongo.junit.FongoRule;
import model.Task;
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
public class TaskDaoTest {

    @Rule
    public FongoRule fongoRule = new FongoRule();

    private TaskDao dao;


    @Before
    public void setUp() throws Exception {
        String dbName = UUID.randomUUID().toString();
        Morphia morphia = new Morphia();
        morphia.map(Task.class);
        Datastore ds = morphia.createDatastore(fongoRule.getFongo().getMongo(), dbName);
        dao = new TaskDao(ds);
    }

    @Test
    public void save() throws Exception {
        dao.save(new Task());
        assertThat(dao.getCount()).isEqualTo(1);
    }

    @Test
    public void initialCount() throws Exception {
        assertThat(dao.getCount()).isZero();
    }

    @Test
    public void getWithObjectId() throws Exception {
        Task task = new Task();
        task.setName("Test");

        dao.save(task);
        Task task2 = dao.get(task.getId());
        assertThat(task2.getName()).isEqualTo(task.getName());
    }

    @Test
    public void getWithObject() throws Exception {
        Task task = new Task();
        task.setName("Test");

        dao.save(task);
        Task task2 = dao.get(task);
        assertThat(task2.getName()).isEqualTo(task.getName());
    }

    @Test
    public void getAll() throws Exception {
        for (int i = 0; i < 5; i++) {
            Task task = new Task();
            task.setName("Test_"+i);
            dao.save(task);
        }
        assertThat(dao.getAll()).hasSize(5);
    }

    @Test
    public void delete() throws Exception {
        Task task = new Task();
        dao.save(task);
        dao.delete(task.getId());

        assertThat(dao.get(task.getId())).isNull();
    }
}
