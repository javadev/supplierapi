package com.cs.roomdbapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.cs.roomdbapi"})
@EntityScan(basePackages= {"com.cs.roomdbapi.model", "com.cs.roomdbapi.converter"})
@EnableJpaRepositories(basePackages= {"com.cs.roomdbapi.repository"})
@ServletComponentScan("com.cs.roomdbapi")
//@EnableAspectJAutoProxy
public class RoomdbapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RoomdbapiApplication.class, args);
    }

}
