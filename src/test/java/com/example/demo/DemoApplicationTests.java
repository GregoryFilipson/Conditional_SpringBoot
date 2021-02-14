package com.example.demo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
	@Autowired
	TestRestTemplate restTemplate;
	public static GenericContainer<?> devapp = new GenericContainer<>("devapp")
			.withExposedPorts(8080);
	public static GenericContainer<?> prodapp = new GenericContainer<>("prodapp")
			.withExposedPorts(8081);

	@BeforeAll
	public static void setUp() {
		devapp.start();
		prodapp.start();
	}

	@Test
	void contextLoads() {
		ResponseEntity<String> forEntity_02 = restTemplate.getForEntity(String.format("http://localhost:%s/profile",
				devapp.getMappedPort(8080)), String.class);
		ResponseEntity<String> forEntity_01 = restTemplate.getForEntity(String.format("http://localhost:%s/profile",
				prodapp.getMappedPort(8081)), String.class);
		assertEquals("Current profile is dev", forEntity_02.getBody());
		assertEquals("Current profile is production", forEntity_01.getBody());

	}

}