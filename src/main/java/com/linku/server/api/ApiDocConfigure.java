package com.linku.server.api;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.ant;

/**
 * Api文档配置
 *
 * @author WangWei
 */
@Configuration
@EnableSwagger2
public class ApiDocConfigure {

    @Bean
    public Docket createSysRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(buildHeadParameters())
                .apiInfo(apiInfo())
                .groupName("sys")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.linku.server.api.sys"))
                .paths(PathSelectors.any())
                .build();
    }

    private List<Parameter> buildHeadParameters(){
        Parameter tokenParam = new ParameterBuilder().
                name("Authorization")
                .description("认证令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
        return Collections.singletonList(tokenParam);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Linku API文档")
                .termsOfServiceUrl("http://apidoc.linku.com")
                .contact(new Contact("WangWei", "", "hhywangwei@gmail.com"))
                .version("1.0")
                .build();
    }

    @Bean
    public Docket createManageRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(buildHeadParameters())
                .apiInfo(apiInfo())
                .groupName("manage")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.linku.server.api.manage"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createClientRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(buildHeadParameters())
                .apiInfo(apiInfo())
                .groupName("client")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.linku.server.api.client"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public Docket createUploadRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(buildHeadParameters())
                .apiInfo(apiInfo())
                .groupName("common")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.linku.server.api.common"))
                .paths(PathSelectors.any())
                .build();
    }
}