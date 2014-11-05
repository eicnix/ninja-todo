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

package controllers;

import model.Task;
import ninja.NinjaDocTester;
import org.doctester.testbrowser.Request;
import org.doctester.testbrowser.Response;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author leichler
 */
public class TaskControllerDocTest extends NinjaDocTester {

    private static final String URL_INDEX = "/task/api";

    @Test
    public void listEmptyTasks() throws Exception {
        Response response = makeRequest(
                Request.GET().url(
                        testServerUrl().path(URL_INDEX)
                )
        );

        assertTrue(response.payloadAs(List.class).isEmpty());
    }

    @Test
    public void saveAndRetrieveTask() throws Exception {
        addTask("Test");
        Response response = makeRequest(
                Request.GET().url(
                        testServerUrl().path(URL_INDEX)
                )
        );
        assertThat(response.payload).contains("Test");
    }

    private Task addTask(String name) {
        Task task = new Task();
        task.setName(name);
        Response response = makeRequest(
                Request.PUT().url(
                        testServerUrl().path(URL_INDEX)
                ).payload(task)
                        .contentTypeApplicationJson()
        );
        return response.payloadAs(Task.class);
    }

    @Test
    public void saveMultipleTask() throws Exception {
        for (int i = 0; i < 5; i++) {
            addTask("Test_" + i);
        }

        Response response = sayAndMakeRequest(
                Request.GET().url(
                        testServerUrl().path(URL_INDEX)
                )
        );
        assertThat(response.payloadJsonAs(List.class)).hasSize(5);
    }

    @Test
    public void deleteTask() throws Exception {
        Task task = addTask("Test");

        Response response = sayAndMakeRequest(
                Request.DELETE().url(
                        testServerUrl().path(URL_INDEX + "/" + task.getId())
                )
        );
        assertThat(response.httpStatus).isEqualTo(200);
    }
}
