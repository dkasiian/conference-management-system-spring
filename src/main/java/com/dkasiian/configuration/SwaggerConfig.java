package com.dkasiian.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket productApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dkasiian.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaInfo())
                .tags(new Tag("conference","Operations related to conferences"),
                        new Tag("report","Operations related to reports"),
                        new Tag("user","Operations related to users"));
    }

    private ApiInfo metaInfo(){
        return new ApiInfoBuilder()
                .title("Conference Management System REST API")
                .description("CMS REST API")
                .contact(new Contact("Dmytro Kasiianenko", "https://www.example.com", "dimkasiyan@gmail.com"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }

}
