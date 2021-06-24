package com.testcode.yjp.last;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@SpringBootApplication
public class LastApplication {

    public static void main(String[] args) {

        SpringApplication.run(LastApplication.class, args);


    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }


}
