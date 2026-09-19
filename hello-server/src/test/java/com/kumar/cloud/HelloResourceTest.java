package com.kumar.cloud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(properties = {
		"eureka.client.enabled=false",
		"eureka.client.register-with-eureka=false",
		"eureka.client.fetch-registry=false",
		"spring.cloud.discovery.enabled=false"
})
public class HelloResourceTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getHello_returnsHelloFromServer() throws Exception {
		mockMvc.perform(get("/rest/hello/server"))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello-from-server"));
	}

	@Test
	public void getHello_returnsPlainText() throws Exception {
		mockMvc.perform(get("/rest/hello/server").accept(MediaType.TEXT_PLAIN))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN));
	}

	@Test
	public void postHello_returnsMethodNotAllowed() throws Exception {
		mockMvc.perform(post("/rest/hello/server"))
				.andExpect(status().isMethodNotAllowed());
	}

	@Test
	public void putHello_returnsMethodNotAllowed() throws Exception {
		mockMvc.perform(put("/rest/hello/server"))
				.andExpect(status().isMethodNotAllowed());
	}

	@Test
	public void unknownPath_returnsNotFound() throws Exception {
		mockMvc.perform(get("/rest/hello/unknown"))
				.andExpect(status().isNotFound());
	}
}
