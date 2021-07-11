package com.testcode.yjp.last.config;

import com.testcode.yjp.last.handler.SocketPTHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebPTSocketConfig implements WebSocketConfigurer {

    @Autowired
    SocketPTHandler socketPTHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketPTHandler, "/ptChat/websocket");
    }
}
