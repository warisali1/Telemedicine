//package com.telemedicicne.telemedicicne.videoCallingTsting.viaApi;
//
//
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component
//public class WebSocketSessionManager {
//
//    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
//
//    public void addSession(String email, WebSocketSession session) {
//        sessions.put(email, session);
//    }
//
//    public void removeSession(String email) {
//        sessions.remove(email);
//    }
//
//    public void sendMessageToUser(String email, String message) throws IOException {
//        WebSocketSession session = sessions.get(email);
//        if (session != null && session.isOpen()) {
//            TextMessage textMessage = new TextMessage(message);
//            session.sendMessage(textMessage);
//        }
//    }
//}
