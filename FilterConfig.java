package com.example.apigatewayservice;

import org.springframework.cloud.gateway.server.mvc.filter.AfterFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class FilterConfig {

	// Custom filter 적용
	// Custom Filter가 적용되면, applicaiton.yml에 적용한 Routing 설정은 무시된다.
	// first-service에 대한 routing
	@Bean
	RouterFunction<ServerResponse> firstServiceFilter() {
		return GatewayRouterFunctions.route("first-service")
					// uri 설정
					.filter(LoadBalancerFilterFunctions.lb("FIRST-TEST-API-SERVICE"))
					// predicates
					.GET("/first-service/**", HandlerFunctions.http())
					// filters
					.before(BeforeFilterFunctions.addRequestHeader("f-request", "First test header"))
					.before(CustomFilter.printPreRequestId())
					.after(AfterFilterFunctions.addResponseHeader("f-response", "First Response"))
					.after(CustomFilter.printPostResponseStatusCode())
					.build();
	}
	
	@Bean
	RouterFunction<ServerResponse> secondServiceFilter() {
		return GatewayRouterFunctions.route("second-service")
					// uri 설정
					.filter(LoadBalancerFilterFunctions.lb("SECOND-TEST-API-SERVICE"))
					// predicates
					.GET("/second-service/**", HandlerFunctions.http())
					// filters
					.before(BeforeFilterFunctions.addRequestHeader("s-request", "Second test header"))
					.before(CustomFilter.printPreRequestId())
					.after(AfterFilterFunctions.addResponseHeader("s-response", "Second Response"))
					.after(CustomFilter.printPostResponseStatusCode())
					.build();
	}
	
}






