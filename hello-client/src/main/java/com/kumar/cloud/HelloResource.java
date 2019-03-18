package com.kumar.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/rest/hello/client")
public class HelloResource {

    @Autowired
    private RestTemplate restTemplate;
    @GetMapping
    public String hello(){
        //String url="http:localhost:8071/rest/hello/server"
        String url="http://hello-server/rest/hello/server";
        return restTemplate.getForObject(url, String.class);
    }
}
