package se.callistaenterprise.kotlindsl.testutil;

import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import se.callistaenterprise.kotlindsl.JooqConfiguration;
import se.callistaenterprise.kotlindsl.data.DataRepositories;
import se.callistaenterprise.kotlindsl.repo.CustomerRepository;
import se.callistaenterprise.kotlindsl.repo.OrderRepository;
import se.callistaenterprise.kotlindsl.repo.ProductRepository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ActiveProfiles("test")
@JooqTest
@AutoConfigureJson
@Import(
        {JooqConfiguration.class,
        DataRepositories.class,
        CustomerRepository.class,
        ProductRepository.class,
        OrderRepository.class}
)
public @interface DslJooqTest {
}
