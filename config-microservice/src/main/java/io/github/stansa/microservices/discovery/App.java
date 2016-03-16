package io.github.stansa.microservices.discovery;

import org.springframework.boot.SpringApplication;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
@EnableZuulProxy
/**
 * Config Server
 *
 */
public class App 
{
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
