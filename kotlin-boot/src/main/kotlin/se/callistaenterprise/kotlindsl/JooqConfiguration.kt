package se.callistaenterprise.kotlindsl

import org.jooq.ExecuteListener
import org.jooq.SQLDialect
import org.jooq.conf.Settings
import org.jooq.impl.DataSourceConnectionProvider
import org.jooq.impl.DefaultConfiguration
import org.jooq.impl.DefaultDSLContext
import org.jooq.impl.DefaultExecuteListenerProvider
import org.springframework.boot.autoconfigure.jooq.JooqExceptionTranslator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JooqConfiguration {

    @Bean
    fun dslContext(configuration: DefaultConfiguration): DefaultDSLContext = DefaultDSLContext(configuration)

    @Bean
    fun dslJooqConfiguration(connectionProvider: DataSourceConnectionProvider, exceptionTranslator: ExecuteListener): DefaultConfiguration {
        val jooqConfiguration = DefaultConfiguration()
        jooqConfiguration.set(SQLDialect.POSTGRES)
        jooqConfiguration.set(connectionProvider)
        jooqConfiguration.set(DefaultExecuteListenerProvider(exceptionTranslator))
        jooqConfiguration.set(Settings().withRenderFormatted(true).withRenderSchema(false))
        return jooqConfiguration
    }

    @Bean
    fun exceptionTranslator(): ExecuteListener = JooqExceptionTranslator()

}
