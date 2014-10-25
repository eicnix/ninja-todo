package controllers;

import model.Todo;
import ninja.NinjaDocTester;
import org.doctester.testbrowser.Request;
import org.doctester.testbrowser.Response;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author leichler
 */
public class MessageControllerDocTest extends NinjaDocTester {

    private static final String URL_INDEX = "/message/api";

    @Test
    public void listEmptyTodos() throws Exception {
        Response response = makeRequest(
                Request.GET().url(
                        testServerUrl().path(URL_INDEX)
                )
        );

        assertTrue(response.payloadAs(List.class).isEmpty());
    }

    @Test
    public void saveAndRetrieveTodo() throws Exception {
        Todo todo = new Todo();
        todo.setName("Test");
        makeRequest(
                Request.PUT().url(
                        testServerUrl().path(URL_INDEX)
                ).payload(todo)
                .contentTypeApplicationJson()
        );

        Response response = makeRequest(
                Request.GET().url(
                        testServerUrl().path(URL_INDEX)
                )
        );
        assertThat(response.payload, containsString("Test"));
    }
}
