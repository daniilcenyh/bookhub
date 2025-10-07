package com.hamming.bookhub.infrastructure.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Import(LiquibaseProperties.class)
public class MongoLiquibaseRunner implements CommandLineRunner {

    private final MongoProperties mongoProperties;
    private final LiquibaseProperties liquibaseProperties;
    @Qualifier("gridFsTemplate")
    private final ResourceLoader resourceLoader;

    /**
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
//        MongoLi
    }
}
