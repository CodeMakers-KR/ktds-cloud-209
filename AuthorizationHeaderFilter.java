package com.example.apigatewayservice;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
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
public class AuthorizationHeaderFilter  extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

	private static final Logger logger = 
			LoggerFactory.getLogger(AuthorizationHeaderFilter.class);
	
//	@Autowired
//	private Environment env;
	@Value("${token.secret}")
	private String jwtSecret;
	
	public AuthorizationHeaderFilter() {
		super(Config.class);
	}

	public static class Config {
	}

	@Override
	public GatewayFilter apply(Config config) {
		// Pre filter
		
		// Postman, Fetch, Axios
		// request header
		// Authorization : Bearer Token (JWT)
		
		return (exchange, chain) -> {
			// Pre filter
			logger.info("JWT Secret: {}", this.jwtSecret);
			
			// Authorization JWT 취득.
			ServerHttpRequest request = exchange.getRequest();
			// Authorization JWT가 존재하는지 먼저 확인.
			if ( !request.getHeaders().containsHeader(HttpHeaders.AUTHORIZATION) ) {
				return this.onError(exchange, "인증이 필요합니다.", HttpStatus.UNAUTHORIZED);
			}
			
			// Bearer Tokenvalue...
			String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			String jwt = authorizationHeader.replace("Bearer ", "");
			
			Map<String, String> tokenContent = this.getTokenContent(jwt);
			if (tokenContent == null) {
				return this.onError(exchange, "만료되거나 변조된 토큰입니다.", HttpStatus.UNAUTHORIZED);
			}
			
			// Request Header 에 Token Content를 작성.
			ServerHttpRequest newRequest = exchange.getRequest().mutate()
					.header("USER_ID", tokenContent.get("userId"))
					.header("EMAIL", tokenContent.get("email"))
					.header("ROLES", tokenContent.get("roles"))
					.build();
			
			// 새롭게 만들어진 ServerHttpRequest를 exchange에 등록.
			ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
			
			// 다음 필터가 존재할 경우 실행.
			// 필터가 없을 경우 라우팅 실행.
			return chain.filter(newExchange);
			
		};
	}

	private Map<String, String> getTokenContent(String jwt) {
		
		Map<String, String> tokenContent = null;
		
		SecretKey secretKey = Keys.hmacShaKeyFor(this.jwtSecret.getBytes());
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
		byte[] bytes = string.getBytes();
		DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
		return response.writeWith(Flux.just(buffer));
	}
}
