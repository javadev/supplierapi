package com.cs.roomdbapi.ftpclient;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Configuration
@ConfigurationProperties(prefix = "ftp")
public class FTPProperties {

    private String server;

    private String username;

    private String password;

    @Min(0)
    @Max(65535)
    private int port;

    @PostConstruct
    public void init() {
        if (port == 0) {
            port = 21;
        }
    }
}
