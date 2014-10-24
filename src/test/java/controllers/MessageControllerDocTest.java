package controllers;

import ninja.NinjaDocTester;
import org.doctester.testbrowser.Request;
import org.doctester.testbrowser.Response;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author leichler
 */
public class MessageControllerDocTest extends NinjaDocTester {

    private static final String URL_INDEX = "/message";

    @Test
    public void returnHelloWorld() throws Exception {
        Response response = makeRequest(
                Request.GET().url(
                        testServerUrl().path(URL_INDEX)
                )
        );
        assertThat(response.payload, containsString("Hallo Welt"));

    }
}
