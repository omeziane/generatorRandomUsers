package com.generator.user.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport
{

   @Value("${app.swagger.title}")
   private String title;

   @Value("${app.swagger.description}")
   private String description;

   @Value("${app.swagger.version}")
   private String version;

   @Value("${app.swagger.license.name}")
   private String license;

   @Value("${app.swagger.license.url}")
   private String licenseUrl;

   @Value("${app.swagger.license.contact.name}")
   private String contactName;

   @Value("${app.swagger.license.contact.url}")
   private String contactUrl;

   @Value("${app.swagger.license.contact.mail}")
   private String contactMail;

   @Value("${app.swagger.basepackage}")
   private String basePackage;

   @Bean
   public Docket productApi()
   {
      return new Docket(DocumentationType.SWAGGER_2)
               .select().paths(PathSelectors.any())
               .apis(RequestHandlerSelectors.basePackage(basePackage))
               .build()
               .pathProvider(new RelativePathProvider(null) {
                 @Override
                 public String getApplicationBasePath() {
                    return "/api"; // Specify your base URL here
                 }
              }).apiInfo(apiInfo());
   }

   public ApiInfo apiInfo()
   {
      return new ApiInfoBuilder().title(title).description(description).version(version).license(license).licenseUrl(licenseUrl)
            .contact(new Contact(contactName, contactUrl, contactMail)).build();
   }

   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry)
   {
      registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
      registry.addResourceHandler("/h2-console/**").addResourceLocations("classpath:/META-INF/resources/");
      registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
   }

   public void setBasePackage(String basePackage) {
      this.basePackage = basePackage;
   }

   public String getBasePackage() {
      return basePackage;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getTitle() {
      return title;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getDescription() {
      return description;
   }

   public void setVersion(String version) {
      this.version = version;
   }

   public String getVersion() {
      return version;
   }

   public void setContactName(String contactName) {
      this.contactName = contactName;
   }

   public String getContactName() {
      return contactName;
   }

   public void setContactUrl(String contactUrl) {
      this.contactUrl = contactUrl;
   }

   public String getContactUrl() {
      return contactUrl;
   }

   public void setContactMail(String contactMail) {
      this.contactMail = contactMail;
   }

   public String getContactMail() {
      return contactMail;
   }
}
