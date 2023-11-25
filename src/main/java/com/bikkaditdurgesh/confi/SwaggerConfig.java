package com.bikkaditdurgesh.confi;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Builder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration

public class SwaggerConfig {
	
	public static final String AUTHORIZATION_HEADER="Authorization";
	@Bean
	public ApiKey apiKeys() {
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
		
	};
	private List<SecurityContext> securityContexts(){
		
		return Arrays.asList(SecurityContext.builder().securityReferences(sef()).build());
		
	}
	private List<SecurityReference> sef(){
		AuthorizationScope scopes=new AuthorizationScope("global", "accessEveryThing");
		
		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope[] {scopes}));
		
	}
	
	@Bean
	public Docket api() {
		
		
		return new Docket(DocumentationType.SWAGGER_2).
				apiInfo(getInfo())
				.securityContexts(securityContexts())
				.securitySchemes(Arrays.asList(apiKeys()))
				.select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();

	}//http://localhost:1004/swagger-ui/index.html

	private ApiInfo getInfo() {

		return new ApiInfo("Blogging App:Backend Devlopment", "This project is devlop by Bikkadit Manmohan Sharma ",
				"1.0", "Terms of Services ", new Contact("Manmohan", "com.bikadiit", "bikkadit@gmail.com"),
				"Licence of Api", "Api licence url", Collections.emptyList());
	};
}
