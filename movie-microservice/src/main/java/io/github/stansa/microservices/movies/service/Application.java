package io.github.stansa.microservices.movies.service;

import io.github.stansa.microservices.movies.service.config.TopicPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import io.github.stansa.microservices.movies.service.data.domain.Movie;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import javax.annotation.PostConstruct;

@SpringBootApplication
@ComponentScan(basePackages = { "io.github.stansa.microservices.movies.service.config", "io.github.stansa.microservices.movies.service.data" })
@EnableZuulProxy
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private RepositoryRestMvcConfiguration repositoryRestConfiguration;

    @Autowired
    private TopicPublisher topicPublisher;

    @PostConstruct
    public void postConstructConfiguration() {

        topicPublisher.sendPong();
        repositoryRestConfiguration.config().exposeIdsFor(Movie.class);


    }
}
