package com.chibuisi.support;

import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@MicronautTest
@Testcontainers
public class IntegrationApiTestSupport {

    @Inject
    @Client("/")
    protected HttpClient client;

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15.0")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @BeforeEach
    void setUp() {
        postgresContainer.start();
    }

    @AfterEach
    void tearDown() {
        postgresContainer.stop();
    }
}
