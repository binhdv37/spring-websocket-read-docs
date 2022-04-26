package com.example.springwebsocket.config;

import com.example.springwebsocket.handler.MyHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/myHandler")
                .setAllowedOrigins("*")
                .addInterceptors(new HttpSessionHandshakeInterceptor()) // interceptor has before and after method => customize if needed
                .withSockJS() // enable sockjs
//                .setClientLibraryUrl("http://localhost:8080/myapp/js/sockjs-client.js")
//                .setStreamBytesLimit(512 * 1024) // 512KB - default 128KB
//                .setHttpMessageCacheSize(1000) // default 100
//                .setDisconnectDelay(30 * 1000); // 30s - default 5s
        ;
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new MyHandler();
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        // setting websocket message info
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }

}
