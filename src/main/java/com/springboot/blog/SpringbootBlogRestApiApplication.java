package com.springboot.blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Main entry point for the Spring Boot Blog REST API application.
 * This class bootstraps the application and configures OpenAPI (Swagger) documentation.
 */
@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Spring Boot Blog App REST APIs",  // API Title displayed in Swagger UI
				description = "Comprehensive documentation for the Spring Boot Blog REST APIs",  // Short API description
				version = "v1.0",  // API version
				contact = @Contact(
						name = "Zaki Haidari",  // API owner's name
						email = "zakihaidari@gmail.com",  // Contact email
						url = "https://www.example.com"  // Your personal or project website
				),
				license = @License(
						name = "Apache 2.0",  // License type
						url = "https://www.apache.org/licenses/LICENSE-2.0.html"  // Link to the license details
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog App Detailed Documentation",  // Additional documentation details
				url = "https://docs.example.com"  // External documentation link (e.g., GitHub, Wiki)
		)
)
public class SpringbootBlogRestApiApplication {

	/**
	 * Provides a ModelMapper bean for mapping DTOs to entities and vice versa.
	 *
	 * @return a configured instance of ModelMapper
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	/**
	 * The main method serves as the entry point for the Spring Boot application.
	 *
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}
}
