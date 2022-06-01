package se.callistaenterprise.kotlindsl.data;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;

import static se.callistaenterprise.kotlindsl.data.DslUtil.evalScript;

/*
  This configuration ensures that src/main/resources/dsl/demodata.orderdsl.kts is automatically interpreted by the dsl functionality at startup.
 */

@Configuration
@Profile("!test")
public class OrderDslConfiguration {

    private final DataRepositories repos;

    OrderDslConfiguration(DataRepositories repos) {
        this.repos = repos;
    }

    @PostConstruct
    void setupTestData() throws URISyntaxException {
        var dataFile = "demodata.orderdsl.kts";
        try {
            Resource resource = new ClassPathResource("/dsl/" + dataFile);
            var contents = StreamUtils.copyToString(resource.getInputStream(), Charset.defaultCharset());
            var dsl = evalScript(contents, dataFile);
            repos.persist(dsl, dataFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
