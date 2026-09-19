package com.kumar.cloud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
		"eureka.client.register-with-eureka=false",
		"eureka.client.fetch-registry=false",
		"eureka.server.enableSelfPreservation=false",
		"eureka.server.waitTimeInMsWhenSyncEmpty=0"
})
public class EurekaServiceApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void contextLoads() {
		assertNotNull(restTemplate);
	}

	@Test
	public void dashboardIsReachable() {
		ResponseEntity<String> response = restTemplate.getForEntity("/", String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	@Test
	public void appsEndpointReturnsXmlRegistry() {
		ResponseEntity<String> response = restTemplate.getForEntity("/eureka/apps", String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertTrue(response.getBody().contains("applications"));
	}

	@Test
	public void unknownApplicationReturnsNotFound() {
		ResponseEntity<String> response = restTemplate.getForEntity("/eureka/apps/DOES-NOT-EXIST", String.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
}
