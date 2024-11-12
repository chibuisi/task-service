package com.chibuisi.support;

import io.micronaut.context.annotation.Property;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.TransactionMode;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.support.TestPropertyProvider;
import jakarta.inject.Inject;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;
import java.util.UUID;

@MicronautTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ApiTestSupport implements TestPropertyProvider {

    @Inject
    @Client("/")
    protected HttpClient client;

    @Override
    public @NonNull Map<String, String> getProperties() {
        String uniqueDbName = "testdb_" + UUID.randomUUID();
        return Map.of("datasources.default.driver-class-name", "org.testcontainers.jdbc.ContainerDatabaseDriver",
                "datasources.default.url", "jdbc:tc:postgresql:15.0-bullseye:///",
                "datasources.default.username", "",
                "datasources.default.password", "");
    }

}
