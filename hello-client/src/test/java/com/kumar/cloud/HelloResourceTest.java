package com.kumar.cloud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(properties = {
		"eureka.client.enabled=false",
		"eureka.client.register-with-eureka=false",
		"eureka.client.fetch-registry=false",
		"spring.cloud.discovery.enabled=false",
		"ribbon.eureka.enabled=false"
})
public class HelloResourceTest {

	private static final String HELLO_SERVER_URL = "http://hello-server/rest/hello/server";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@MockBean
	private RestTemplate restTemplate;

	@Test
	public void getHello_returnsPayloadFromHelloServer() throws Exception {
		when(restTemplate.getForObject(eq(HELLO_SERVER_URL), eq(String.class)))
				.thenReturn("Hello-from-server");

		mockMvc.perform(get("/rest/hello/client"))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello-from-server"));

		verify(restTemplate).getForObject(eq(HELLO_SERVER_URL), eq(String.class));
	}

	@Test
	public void getHello_usesServiceDiscoveryNameNotHardCodedHost() throws Exception {
		when(restTemplate.getForObject(eq(HELLO_SERVER_URL), eq(String.class)))
				.thenReturn("Hello-from-server");

		mockMvc.perform(get("/rest/hello/client").accept(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk());

		verify(restTemplate).getForObject(eq("http://hello-server/rest/hello/server"), eq(String.class));
	}

	@Test
	public void getHello_whenHelloServerUnavailable_returnsServerError() {
		when(restTemplate.getForObject(eq(HELLO_SERVER_URL), eq(String.class)))
				.thenThrow(new RestClientException("hello-server is unavailable"));

		ResponseEntity<String> response = testRestTemplate.getForEntity("/rest/hello/client", String.class);

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
	public void postHello_returnsMethodNotAllowed() throws Exception {
		mockMvc.perform(post("/rest/hello/client"))
				.andExpect(status().isMethodNotAllowed());
	}

	@Test
	public void unknownPath_returnsNotFound() throws Exception {
		mockMvc.perform(get("/rest/hello/unknown"))
				.andExpect(status().isNotFound());
	}
}
