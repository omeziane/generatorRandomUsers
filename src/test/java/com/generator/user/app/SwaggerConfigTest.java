package com.generator.user.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spring.web.plugins.Docket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SwaggerConfigTest {

    private SwaggerConfig swaggerConfig;

    @BeforeEach
    void setUp() {
        swaggerConfig = new SwaggerConfig();
    }

    @Test
    void testProductApi() {
        Docket docket = swaggerConfig.productApi();
        assertNotNull(docket);
        // Additional assertions for the docket configuration
    }

    @Test
    void testApiInfo() {
        ApiInfo apiInfo = swaggerConfig.apiInfo();
        assertNotNull(apiInfo);
        // Additional assertions for the API info configuration
    }

    @Test
    void testBasePackage() {
        String basePackage = "com.example";
        swaggerConfig.setBasePackage(basePackage);
        assertEquals(basePackage, swaggerConfig.getBasePackage());
    }

    @Test
    void testSwaggerConfigProperties() {
        String title = "My API";
        String description = "API documentation";
        String version = "1.0.0";
        String contactName = "John Doe";
        String contactUrl = "http://www.example.com";
        String contactMail = "john@example.com";
        String basePackage = "com.example";

        swaggerConfig.setTitle(title);
        swaggerConfig.setDescription(description);
        swaggerConfig.setVersion(version);
        swaggerConfig.setContactName(contactName);
        swaggerConfig.setContactUrl(contactUrl);
        swaggerConfig.setContactMail(contactMail);
        swaggerConfig.setBasePackage(basePackage);

        ApiInfo apiInfo = swaggerConfig.apiInfo();
        assertEquals(title, apiInfo.getTitle());
        assertEquals(description, apiInfo.getDescription());
        assertEquals(version, apiInfo.getVersion());
        assertEquals(contactName, apiInfo.getContact().getName());
        assertEquals(contactUrl, apiInfo.getContact().getUrl());
        assertEquals(contactMail, apiInfo.getContact().getEmail());

        Docket docket = swaggerConfig.productApi();
        assertNotNull(docket);
        // Additional assertions for the docket configuration

        assertEquals(basePackage, swaggerConfig.getBasePackage());
    }

    // Add more test methods to cover other aspects of the SwaggerConfig class
}
