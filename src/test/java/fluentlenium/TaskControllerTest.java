package fluentlenium;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import ninja.NinjaFluentLeniumTest;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author leichler
 */
public class TaskControllerTest extends NinjaFluentLeniumTest {

    @Before
    public void setUp() throws Exception {
        goTo(getServerAddress() + "task");
    }

    @Test
    public void addTask() throws Exception {
        addTaskOnSite("Test");
        assertThat(find("td b").first().getText()).isEqualTo("Test");
    }

    private void addTaskOnSite(String name) {
        find(".task-input").text(name);
        click(".add-button");
        await().atMost(5, TimeUnit.SECONDS).until("td b").hasText(name);
    }

    @Test
    public void deleteTask() throws Exception {
        addTaskOnSite("Test");
        click(".delete-button");
        await().atMost(5, TimeUnit.SECONDS).until("tr").hasSize(0);
    }

    @Test
    public void markTaskAsDone() throws Exception {
        addTaskOnSite("Test");
        click("td b");
        await().atMost(5, TimeUnit.SECONDS).until("td").hasAttribute("class", "strike");

    }

    @Override
    public WebDriver getDefaultDriver() {
        webDriver =  new HtmlUnitDriver(BrowserVersion.FIREFOX_17) {
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
        return webDriver;
    }
}
