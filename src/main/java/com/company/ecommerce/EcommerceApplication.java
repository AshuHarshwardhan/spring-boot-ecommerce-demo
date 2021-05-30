package com.company.ecommerce;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class EcommerceApplication {

	public static final Contact DEFAULT_CONTACT = new Contact("Ashu Harshwardhan",
			"https://www.linkedin.com/in/ashu-harshwardhan/", "ashu.sae@gmail.com");

	public static final ApiInfo DEFAULT_API_INFO = new ApiInfo("Sample E-Commerce Application APIs",
			"Sample E-Commerce Application APIs", "1.0", "urn:tos", DEFAULT_CONTACT, "Apache 2.0",
			"http://www.apache.org/licenses/LICENSE-2.0", Arrays.asList());

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	@Bean
	public Docket userApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(DEFAULT_API_INFO).select()
				.apis(RequestHandlerSelectors.basePackage("com.example.demo")).build();
	}
}
