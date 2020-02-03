package com.tiagods.apimongodb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages =
        "com.tiagods.apimongodb.secundary",
        mongoTemplateRef = "secondaryMongoTemplate")
public class SecundaryMongoConfig {
}
