package com.chisw.testtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration
@EnableDiscoveryClient
@SpringBootApplication
public class GraphNodesDemandsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphNodesDemandsApplication.class, args);
	}
}
