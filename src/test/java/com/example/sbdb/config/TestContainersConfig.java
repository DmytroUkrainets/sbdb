package com.example.sbdb.config;

import org.testcontainers.containers.MySQLContainer;

public abstract class TestContainersConfig {
    public static final MySQLContainer<?> MYSQL_CONTAINER;

    static {
        MYSQL_CONTAINER = new MySQLContainer<>("mysql:8.0")
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test")
                .withReuse(true);
        MYSQL_CONTAINER.start();
    }
}
