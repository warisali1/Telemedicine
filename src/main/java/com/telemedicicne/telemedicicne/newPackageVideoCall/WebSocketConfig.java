//package com.telemedicicne.telemedicicne.newPackageVideoCall;//package com.telemedicicne.telemedicicne.newPackageVideoCall;
////
////import org.springframework.context.annotation.Configuration;
////import org.springframework.messaging.simp.config.MessageBrokerRegistry;
////import org.springframework.web.socket.config.annotation.*;
////
////
////@Configuration
////@EnableWebSocket
//////@EnableWebSocketMessageBroker
////
////public class WebSocketConfig implements WebSocketConfigurer {
//////public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
////
////
////    @Override
////    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
////        registry.addHandler(new SignalingHandler(), "/ws").setAllowedOrigins("*");
////    }
////
////
//////    @Override
//////    public void configureMessageBroker(MessageBrokerRegistry config) {
//////        config.enableSimpleBroker("/topic");
//////        config.setApplicationDestinationPrefixes("/app");
//////    }
////
//////    @Override
//////    public void registerStompEndpoints(StompEndpointRegistry registry) {
//////        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
//////    }
////
////
////
//////    @Override
//////    public void registerStompEndpoints(StompEndpointRegistry registry) {
//////        registry.addEndpoint("/ws")
////////                .setAllowedOriginPatterns("*") // Allow all origins
//////                .setAllowedOriginPatterns("http://localhost:3000", "http://localhost:3001") // Replace with your frontend's URL
//////
//////                .withSockJS();
//////    }
////}
//
//import com.telemedicicne.telemedicicne.newPackageVideoCall.SignalingHandler;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.messaging.simp.config.MessageBrokerRegistry;
////import org.springframework.web.socket.config.annotation.*;
////import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
////
////@Configuration
////@EnableWebSocket
////@EnableWebSocketMessageBroker
////public class WebSocketConfig implements WebSocketConfigurer, WebSocketMessageBrokerConfigurer {
////
////    @Override
////    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
////        registry.addHandler(new SignalingHandler(), "/signaling")
////                .setAllowedOrigins("*");
////    }
////
////    @Override
////    public void configureMessageBroker(MessageBrokerRegistry registry) {
////        registry.enableSimpleBroker("/topic");
////        registry.setApplicationDestinationPrefixes("/app");
////    }
////
////    @Override
////    public void registerStompEndpoints(StompEndpointRegistry registry) {
////        registry.addEndpoint("/websocket")
////                .setAllowedOrigins("*")
////                .addInterceptors(new HttpSessionHandshakeInterceptor())
////                .withSockJS();
////    }
////}
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.*;
//
//@Configuration
//@EnableWebSocket
//@EnableWebSocketMessageBroker
//public class WebSocketConfig implements WebSocketConfigurer, WebSocketMessageBrokerConfigurer {
//
////    @Autowired
////    private SignalingHandler signalingHandler;
////
////    @Override
////    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
////        registry.addHandler(signalingHandler, "/signaling")
////                .setAllowedOrigins("*");
////    }
////
////    @Override
////    public void configureMessageBroker(MessageBrokerRegistry registry) {
////        registry.enableSimpleBroker("/topic");
////        registry.setApplicationDestinationPrefixes("/app");
////    }
////
////    @Override
////    public void registerStompEndpoints(StompEndpointRegistry registry) {
////        registry.addEndpoint("/websocket")
////                .setAllowedOrigins("*")
////                .withSockJS();
////    }
//@Autowired
//private SignalingHandler signalingHandler;
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(signalingHandler, "/signaling")
//                .setAllowedOrigins("*");
//    }
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.enableSimpleBroker("/topic");
//        registry.setApplicationDestinationPrefixes("/app");
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/websocket")
//                .setAllowedOrigins("*")
//                .withSockJS();
//    }
//}
