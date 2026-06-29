package com.example.apigatewayservice;

import java.util.function.BiFunction;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

public class CustomFilter {

	private static final Logger logger = LoggerFactory.getLogger(CustomFilter.class);
	
	public static Function<ServerRequest, ServerRequest> printPreRequestId() {
		return (request) -> {
			logger.info("<Pre Filter> Request ID: {}", request.servletRequest().getRequestId());
			logger.info("<Pre Filter> Request Path: {}", request.path());
			return request;
		};
	}
	
	public static BiFunction<ServerRequest, ServerResponse, ServerResponse> printPostResponseStatusCode() {
		return (request, response) -> {
			logger.info("<Post Filter> Request ID: {}", request.servletRequest().getRequestId());
			logger.info("<Post Filter> Request Path: {}", request.path());
			logger.info("<Post Filter> Response Status Code: {}", response.statusCode().value());
			return response;
		};
	}
	
}
