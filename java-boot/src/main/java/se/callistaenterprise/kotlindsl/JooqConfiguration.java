package se.callistaenterprise.kotlindsl;

import org.jooq.ExecuteListener;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.boot.autoconfigure.jooq.JooqExceptionTranslator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JooqConfiguration {

    @Bean
    public DefaultDSLContext dslContext(DefaultConfiguration configuration) {
        return new DefaultDSLContext(configuration);
    }

    @Bean
    public DefaultConfiguration dslJooqConfiguration(DataSourceConnectionProvider connectionProvider, ExecuteListener exceptionTranslator) {
        var jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.set(SQLDialect.POSTGRES);
        jooqConfiguration.set(connectionProvider);
        jooqConfiguration.set(new DefaultExecuteListenerProvider(exceptionTranslator));
        jooqConfiguration.set(new Settings().withRenderFormatted(true).withRenderSchema(false));
        return jooqConfiguration;
    }

    @Bean
    public ExecuteListener exceptionTranslator() {
        return new JooqExceptionTranslator();
    }

}
