package fluentium;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import ninja.NinjaFluentLeniumTest;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.concurrent.TimeUnit;

/**
 * @author leichler
 */
public class TaskControllerTest extends NinjaFluentLeniumTest {

    @Before
    public void setUp() throws Exception {
        goTo(getServerAddress() + "task/");
    }

    @Test
    public void addTask() throws Exception {
        find(".task-input").text("Test2");
        click(".add-button");
        await().atMost(5, TimeUnit.SECONDS).until("td b").hasText("Test2");
    }

    @Override
    public WebDriver getDefaultDriver() {
        return new HtmlUnitDriver(BrowserVersion.CHROME){
            @Override
            protected WebClient modifyWebClient(WebClient client) {
                if (client == null) {
                    return null;
                }
                super.modifyWebClient(client);
                client.getOptions().setJavaScriptEnabled(true);
                return client;
            }
        };
    }
}
