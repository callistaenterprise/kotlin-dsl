package se.callistaenterprise.kotlindsl.testutil

import org.springframework.boot.test.autoconfigure.jooq.JooqTest
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import se.callistaenterprise.kotlindsl.JooqConfiguration
import se.callistaenterprise.kotlindsl.data.DataRepositories
import se.callistaenterprise.kotlindsl.repo.CustomerRepository
import se.callistaenterprise.kotlindsl.repo.OrderRepository
import se.callistaenterprise.kotlindsl.repo.ProductRepository

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@ActiveProfiles("test")
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
annotation class DslSpringBootTest

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@ActiveProfiles("test")
@JooqTest
@AutoConfigureJson
@Import(
    JooqConfiguration::class,
    DataRepositories::class,
    CustomerRepository::class,
    ProductRepository::class,
    OrderRepository::class,
)
annotation class DslJooqTest
