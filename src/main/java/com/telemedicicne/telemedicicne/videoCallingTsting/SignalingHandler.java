package com.telemedicicne.telemedicicne.videoCallingTsting;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class SignalingHandler extends TextWebSocketHandler {

//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        // Broadcast the signaling message to all connected clients
//        for (WebSocketSession webSocketSession : session.getOpenSessions()) {
//            if (webSocketSession.isOpen() && !session.getId().equals(webSocketSession.getId())) {
//                webSocketSession.sendMessage(message);
//            }
//        }
//    }


    //old

    // Maintain a set of active sessions
//    private static final Set<WebSocketSession> sessions = new HashSet<>();
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        // Add new session to the set
//        sessions.add(session);
//    }
//@Override
//protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//    // Broadcast the signaling message to all connected clients
//    for (WebSocketSession webSocketSession : sessions) {
//        if (webSocketSession.isOpen() && !session.getId().equals(webSocketSession.getId())) {
//            webSocketSession.sendMessage(message);
//        }
//    }
//}
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus closeStatus) throws Exception {
//        // Remove closed session from the set
//        sessions.remove(session);
//    }

    private static final Logger logger = LoggerFactory.getLogger(SignalingHandler.class);

    private static final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

//    private static final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);

        logger.info("WebSocket session opened: {}", session.getId());


    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("Received message from session {}: {}", session.getId(), message.getPayload());

        synchronized (sessions) {
            for (WebSocketSession webSocketSession : sessions) {
                if (webSocketSession.isOpen() && !webSocketSession.equals(session)) {
                    webSocketSession.sendMessage(message);
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        logger.info("WebSocket session closed: {}", session.getId());

    }
}
