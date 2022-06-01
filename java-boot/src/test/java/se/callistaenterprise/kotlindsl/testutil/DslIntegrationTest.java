package se.callistaenterprise.kotlindsl.testutil;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class DslIntegrationTest {
    public static final String DBNAME = "orderdsl";
    public static final String DBUSERNAME = "test";
    public static final String DBPASSWORD = "test";
    public static final String CONTAINER_VERSION = "postgres:14";

    private static final PostgreSQLContainer container;

    static {
        container = new PostgreSQLContainer<>(CONTAINER_VERSION)
                .withDatabaseName(DBNAME)
                .withDatabaseName(DBUSERNAME)
                .withPassword(DBPASSWORD);
        container.start();
    }

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", container::getUsername);
        registry.add("spring.datasource.password", container::getPassword);
    }
}
