package com.clj.demo.configuration;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author lujia chen
 * @Created 2020/12/21
 * @Description Swagger配置
 * @date 2020/12/21
 * @Version 1.0.version
 **/
@Data
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
@Configuration
@ConfigurationProperties(prefix = "spring.swagger")
public class SwaggerConfiguration {
    /**
     * 管理组名称
     */
    private String groupName;
    /**
     * swagger主题
     */
    private String title;
    /**
     * swagger描述功能
     */
    private String description;
    /**
     * 参考文档
     */
    private String termsOfServiceUrl;
    /**
     * swagger发行版本
     */
    private String version;

    /**
     * 映射包名
     */
    private String basePackage;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(groupName)
                .apiInfo(new ApiInfoBuilder()
                        .title(title)
                        .description(description)
                        .termsOfServiceUrl(termsOfServiceUrl)
                        .version(version)
                        .build()
                )
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.any())
                .build();
    }
  /*  @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户管理组")
                .apiInfo(new ApiInfoBuilder()
                        .title("用户测试")
                        .description("前后对接swagger文档")
                        .termsOfServiceUrl("http://localhost;8080/doc.html")
                        .version("1.0")
                        .build()
                )
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ffzs.webflux.r2dbc_test.controllersers"))
                .paths(PathSelectors.any())
                .build();
    }*/
}
