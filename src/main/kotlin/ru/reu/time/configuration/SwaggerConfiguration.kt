package ru.reu.time.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.time.LocalDateTime

@Configuration
@EnableSwagger2
class SwaggerConfiguration {

    @Bean
    fun mainApi(): Docket =
        buildDocket(
            Docket(DocumentationType.SWAGGER_2)
                .groupName("groundControlApiV1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.reu.time"))
                .paths(
                    PathSelectors.ant("/api/v1/ground-control/**")
                )
        )

    private fun buildDocket(apiSelectorBuilder: ApiSelectorBuilder): Docket =
        apiSelectorBuilder
            .build()
            .pathMapping("/")
            .apiInfo(ApiInfo.DEFAULT)
            .forCodeGeneration(true)
            .genericModelSubstitutes(ResponseEntity::class.java)
            .directModelSubstitute(LocalDateTime::class.java, Long::class.java)

}
