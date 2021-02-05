package com.coderone95.secu;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot"))) //excludes the error handlers
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("SecU Info Management").
                version("V1.0").build();
    }
}
