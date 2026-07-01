package com.ktdsuniversity.edu.firsttestapiservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

//@Controller
//@ResponseBody
@RestController
@RequestMapping("/first-service")
public class FirstTestApiController {

	private static final Logger logger = LoggerFactory.getLogger(FirstTestApiController.class);
	
	@GetMapping("/welcome")
	public String welcome(@RequestHeader("f-request") String header) {
		return "Welcome to the first-service";
	}
	
	@GetMapping("/message")
	public String message(@RequestHeader("f-request") String header) {
		logger.info("First-Service f-request: {}", header);
		return "%s in First Service".formatted(header);
	}
	
	@GetMapping("/check")
	public String check(
			HttpServletRequest request, 
			@RequestHeader("f-request") String header) {
		// 현재 Server의 port를 가져와서 반환.
		int port = request.getServerPort();
		logger.info("First-Service port: {}", port);
		return "First Service health check result: %d".formatted(port);
	}
	
}
