package com.tiagods.apimongodb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages =
        "com.tiagods.apimongodb.primary",
        mongoTemplateRef = "primaryMongoTemplate")
public class PrimaryMongoConfig {
}
