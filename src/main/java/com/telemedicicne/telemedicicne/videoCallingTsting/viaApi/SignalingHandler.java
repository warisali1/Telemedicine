//package com.telemedicicne.telemedicicne.videoCallingTsting.viaApi;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//@Component
//public class SignalingHandler extends TextWebSocketHandler {
//
//    private static final Logger logger = LoggerFactory.getLogger(SignalingHandler.class);
//
//    @Autowired
//    private WebSocketSessionManager webSocketSessionManager;
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        // Assuming email is passed as a query parameter in the WebSocket URL
//        String email = session.getUri().getQuery().split("=")[1];
//        webSocketSessionManager.addSession(email, session);
//        logger.info("WebSocket session opened for email: {}", email);
//    }
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        logger.info("Received message from session {}: {}", session.getId(), message.getPayload());
//        // Handle signaling messages (e.g., offer, answer, ice candidates) here
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        String email = session.getUri().getQuery().split("=")[1];
//        webSocketSessionManager.removeSession(email);
//        logger.info("WebSocket session closed for email: {}", email);
//    }
//}
