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
