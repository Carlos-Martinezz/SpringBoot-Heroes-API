package com.carlos.springboot.cmheroesapi.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Carlos Martínez
 * @version 1
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	public static final String BASE_PACKAGE = "com.carlos.springboot.cmheroesapi.app.controllers";
	private static final List<String> DEFAULT_PRODUCES_CONSUMES = Arrays.asList("application/json");
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String DEFAULT_INCLUDE_PATTERN = "/(?!auth).*";

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
							.host("localhost:8080")
							.produces(new HashSet<>(DEFAULT_PRODUCES_CONSUMES))
							.consumes(new HashSet<>(DEFAULT_PRODUCES_CONSUMES))
							.useDefaultResponseMessages(false)
							.securitySchemes(Arrays.asList(apiKey()))
							.securityContexts(Arrays.asList(securityContext()))
							.select()
							.apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
							.paths(PathSelectors.any())
							.build()
							.tags(
									new Tag("Héroes", "Endpoints para operaciones CRUD y más con Héroes.", 0),
									new Tag("Seguridad", "Endpoint para iniciar sesión en el servicio", 1)
							);
	}

	private ApiInfo apiInfo() {
		var contact = new Contact("Carlos Martínez", "https://carlos.com/", "carlos33820@gmail.com");
		var builder = new ApiInfoBuilder() 
							.title("API Héroes - Documentación") 
							.description("Ésta documentación le permitirá consumir los distintos endpoints de la API de Héroes.") 
							.version("1") 
							.termsOfServiceUrl("")
							.contact(contact) 
							.license("") 
							.licenseUrl("") 
							.extensions(new ArrayList<>());

		return builder.build();
	}

	private ApiKey apiKey() {
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext
				.builder() //
				.securityReferences(defaultAuth()) //
				.forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN)) //
				.build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
	}

}
