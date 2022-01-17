package com.olx;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient
public class OlxLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(OlxLoginApplication.class, args);
	}
	private ApiInfo getApiInfo() {

		return new ApiInfo("Olx-Login Data API Documentation", "Olx Login API Released By Zensar Ltd", "2.5",
				"https:zensar.com/termsofservice",
				new Contact("RajendraSoni", "http://zensar.com", "rajendra.soni@zensar.com"), "GPL", "http://gpl.com",
				new ArrayList<VendorExtension>());

	}

	@Bean
	public Docket getCustomizedDocket() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.zensar")).paths(PathSelectors.any())
				.build();

	}

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

}
