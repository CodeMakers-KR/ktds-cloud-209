package com.ktdsuniversity.edu.apigatewayservice.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FailureContoller {

	@GetMapping("/fallback/failure")
	public ResponseEntity<String> fallback() {
		return new ResponseEntity<>("요청량이 너무 많습니다. 잠시 후 다시 시도해주세요.", 
					HttpStatus.TOO_MANY_REQUESTS);
	}
	
}
