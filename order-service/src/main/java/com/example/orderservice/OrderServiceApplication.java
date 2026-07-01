package com.example.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.Logger;
import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}
	
	@Bean
	RequestInterceptor reqeustIntercepor() {
		return requestTemplate -> {
			ServletRequestAttributes requestAttributes = 
					(ServletRequestAttributes) RequestContextHolder
							.getRequestAttributes();

			if (requestAttributes != null) {
				HttpServletRequest request = requestAttributes.getRequest();
				String userId = request.getHeader("USER_ID");
				String email = request.getHeader("EMAIL");
				String roles = request.getHeader("ROLES");
				
				requestTemplate.header("USER_ID", userId);
				requestTemplate.header("EMAIL", email);
				requestTemplate.header("ROLES", roles);
			}

		};
	}
}
