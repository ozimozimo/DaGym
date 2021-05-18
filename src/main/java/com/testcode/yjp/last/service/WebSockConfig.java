//package com.testcode.yjp.last.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
//@RequiredArgsConstructor
//@Configuration
//@EnableWebSocket
//public class WebSockConfig implements WebSocketConfigurer {
//
//    private final WebSocketHandler webSocketHandler;
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        //도메인이 다른 서버에서도 접속 가능하도록 * 설정
//        registry.addHandler(webSocketHandler, "/ws/chat").setAllowedOrigins("*");
//    }
//}
