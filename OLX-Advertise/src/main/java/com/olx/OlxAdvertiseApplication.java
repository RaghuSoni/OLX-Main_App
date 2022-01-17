package com.olx;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.olx.utility.LocalDateAttributeConverter;
import com.olx.utility.Utility;

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
public class OlxAdvertiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(OlxAdvertiseApplication.class, args);
	}
	

	private ApiInfo getApiInfo() {

		return new ApiInfo("Advertisement REST API Documentation", "Advertisement REST API Released By Zensar Ltd", "2.5",
				"https:zensar.com/termsofservice",
				new Contact("RajendraSoni", "http://zensar.com,com", "rajendra.soni@zensar.com"), "GPL",
				"http://gpl.com", new ArrayList<VendorExtension>());

	}

	@Bean
	public Docket getCustomizedDocket() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.zensar.olx")).paths(PathSelectors.any())
				.build();

	}

	

	@Bean
	public ModelMapper getModelmapper() {

		return new ModelMapper();
	}

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {

		return new RestTemplate();

	}

	@Bean
	public Utility getUtility() {
		return new Utility();
	}

	@Bean
	public LocalDateAttributeConverter getDateConverter() {

		return new LocalDateAttributeConverter();
	}

}
