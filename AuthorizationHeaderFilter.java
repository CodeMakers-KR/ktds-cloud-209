package com.ktdsuniversity.edu.apigatewayservice.filters;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

	private static final Logger logger = 
			LoggerFactory.getLogger(AuthorizationHeaderFilter.class);
	
	@Autowired
	private Environment env;
	
	public AuthorizationHeaderFilter() {
		super(Config.class);
	}

	public static class Config {
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			
		};
	}

	private Map<String, String> getTokenContent(String jwt) {
		
		Map<String, String> tokenContent = null;
		
		SecretKey secretKey = Keys.hmacShaKeyFor(this.env.getProperty("token.secret").getBytes());
		Claims claims = null;

		try {
			claims = Jwts.parser()
						  .verifyWith(secretKey)
						  .build()
						  .parseSignedClaims(jwt)
						  .getPayload();
			
			tokenContent = new HashMap<>();
			tokenContent.put("userId", claims.getSubject());
			tokenContent.put("email", claims.get("email").toString());
			tokenContent.put("roles", claims.get("roles").toString());
			return tokenContent;
		} catch (JwtException | IllegalArgumentException ex) {
			return null;
		}
	}

	private Mono<Void> onError(ServerWebExchange exchange, String string, HttpStatus httpStatus) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(httpStatus);
		byte[] bytes = "JWT가 올바르지 않습니다.".getBytes();
		DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
		return response.writeWith(Flux.just(buffer));
	}
}
