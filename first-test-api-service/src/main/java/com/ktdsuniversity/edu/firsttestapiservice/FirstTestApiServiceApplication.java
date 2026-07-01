package com.ktdsuniversity.edu.firsttestapiservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FirstTestApiServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstTestApiServiceApplication.class, args);
	}

}
