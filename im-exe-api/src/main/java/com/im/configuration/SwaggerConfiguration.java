package com.im.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author ZhangPeng
 * @date 2017/12/8
 */
@Configuration
public class SwaggerConfiguration {
    @Bean
    public Docket createApi(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.dfjs.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("API文档")
                .description("API参数定义")
                .termsOfServiceUrl("http://localhost:9696/logisticPlatform/swagger-ui.html")
                .version("1.0")
                .build();
    }
}
