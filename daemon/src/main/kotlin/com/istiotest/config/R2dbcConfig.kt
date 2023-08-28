package com.istiotest.config

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

@Configuration
@EnableR2dbcRepositories
class R2dbcConfig: AbstractR2dbcConfiguration() {

    @Value("\${spring.r2dbc.username}")
    lateinit var username: String

    @Value("\${spring.r2dbc.password}")
    lateinit var password: String

    @Bean
    override fun connectionFactory(): ConnectionFactory =
        PostgresqlConnectionFactory(PostgresqlConnectionConfiguration.builder()
            .host("postgres")
            .port(5432)
            .database("demo")
            .username(username)
            .password(password)
            .build()
        )
}