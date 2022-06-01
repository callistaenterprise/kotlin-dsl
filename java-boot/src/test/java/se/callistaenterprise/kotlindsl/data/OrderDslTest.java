package se.callistaenterprise.kotlindsl.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.shaded.com.google.common.io.Resources;
import se.callistaenterprise.kotlindsl.testutil.DslIntegrationTest;
import se.callistaenterprise.kotlindsl.testutil.DslSpringBootTest;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@DslSpringBootTest
public class OrderDslTest extends DslIntegrationTest {

    private final DataRepositories repos;

    public OrderDslTest(@Autowired DataRepositories repos) {
        this.repos = repos;
    }

    @Test
    public void testDemodataFile() throws IOException {
        var dsl = DslUtil.evalScript(fileContents("dsl/demodata.orderdsl.kts"), "demodata.orderdsl.kts");
        repos.persist(dsl);
    }

    private String fileContents(String file) throws IOException {
        URL url = Resources.getResource(file);
        return Resources.toString(url, StandardCharsets.UTF_8);
    }

}
