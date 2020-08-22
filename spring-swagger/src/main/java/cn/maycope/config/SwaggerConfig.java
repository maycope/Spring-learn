package cn.maycope.config;

/**
 * @Author Maycope
 * @Date 2020/8/21
 * @Version 1.0
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 1.既然你是声明为 config 就需要我们对应的 configuration 注解,使其变成配置类。
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 配置扫描包
                .apis(RequestHandlerSelectors.basePackage("cn.maycope.controller"))
                // 配置过滤条件
                .paths(PathSelectors.regex("/.*"))
                .build();

    }
    private ApiInfo apiInfo(){
        //contact 表示的是作者信息。
        return  new ApiInfoBuilder()
                .title("Spring Boot集成Swagger构建API 文档")
                .description("具体的源码地址参见")
                .termsOfServiceUrl("https://www.maycope.cn")
                .contact(new Contact("Maycope","https://www.maycope.cn",""))
                .version("1.0")
                .build();
    }
}
