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
        Task task = new Task();
        task.setName("Test");
        makeRequest(
                Request.PUT().url(
                        testServerUrl().path(URL_INDEX)
                ).payload(task)
                .contentTypeApplicationJson()
        );

        Response response = makeRequest(
                Request.GET().url(
                        testServerUrl().path(URL_INDEX)
                )
        );
        assertThat(response.payload).contains("Test");
    }

    @Test
    public void saveMultipleTask() throws Exception {
        Task task = new Task();
        for (int i = 0; i < 5; i++) {
            task.setName("Test_" + i);
            makeRequest(
                    Request.PUT().url(
                            testServerUrl().path(URL_INDEX)
                    ).payload(task)
                            .contentTypeApplicationJson());
        }

        Response response = sayAndMakeRequest(
                Request.GET().url(
                        testServerUrl().path(URL_INDEX)
                )
        );
        assertThat(response.payloadJsonAs(List.class)).hasSize(5);
    }
}
