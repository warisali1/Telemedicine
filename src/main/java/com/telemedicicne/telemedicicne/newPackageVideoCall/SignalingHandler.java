//package com.telemedicicne.telemedicicne.newPackageVideoCall;//package com.telemedicicne.telemedicicne.newPackageVideoCall;
////
////import com.fasterxml.jackson.databind.ObjectMapper;
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
////import org.springframework.web.socket.CloseStatus;
////import org.springframework.web.socket.TextMessage;
////import org.springframework.web.socket.WebSocketSession;
////import org.springframework.web.socket.handler.TextWebSocketHandler;
////
////import java.io.IOException;
////import java.util.HashMap;
////import java.util.Map;
////import java.util.concurrent.ConcurrentHashMap;
////
////public class SignalingHandler extends TextWebSocketHandler {
////
////    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
////    private static final Logger logger = LoggerFactory.getLogger(SignalingHandler.class);
////
////    @Override
////    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
////        sessions.put(session.getId(), session);
////
////        broadcastToAll("users:joined", session.getId());
////        sendMessage(session, "outgoing:call", Map.of("id", session.getId()));
////    }
////
////    @Override
////    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
////        String payload = message.getPayload();
////        System.out.println("Received message: " + payload);
////
////        // Handle the incoming message and send a response if necessary
////        session.sendMessage(new TextMessage("Message received: " + payload));
////    }
////
////    private void handleCandidate(WebSocketSession session, Map<String, Object> data) throws IOException {
////        Map<String, Object> candidateData = (Map<String, Object>) data.get("candidate");
////        // Process the candidate message here
////        // Example: Forward the candidate message to the appropriate recipient
////        String toUserId = (String) data.get("to");
////        WebSocketSession recipientSession = sessions.get(toUserId);
////        if (recipientSession != null) {
////            sendMessage(recipientSession, "candidate", Map.of("from", session.getId(), "candidate", candidateData));
////        }
////    }
////
////    private void handleOutgoingCall(WebSocketSession session, Map<String, Object> data) throws IOException {
////        String toUserId = "1"; // Example, replace with dynamic logic if needed
////        String fromOffer = (String) data.get("fromOffer");
////
////        if (toUserId != null) {
////            WebSocketSession recipientSession = sessions.get(toUserId);
////
////            if (recipientSession != null && recipientSession.isOpen()) {
////                sendMessage(recipientSession, "incoming:call", Map.of("from", session.getId(), "offer", fromOffer));
////            } else {
////                logger.warn("Recipient session not found or closed for user id: {}", toUserId);
////                // Optionally handle this case, e.g., notify the sender that the recipient is not available
////            }
////        } else {
////            logger.error("Invalid 'to' user id received in outgoing call message.");
////            // Optionally handle this case, e.g., notify the sender of an invalid recipient ID
////        }
////    }
////
////    private void handleCallAccepted(WebSocketSession session, Map<String, Object> data) throws IOException {
////        String toUserId = (String) data.get("to");
////        String answer = (String) data.get("answer");
////        if (toUserId != null) {
////            WebSocketSession recipientSession = sessions.get(toUserId);
////            if (recipientSession != null) {
////                sendMessage(recipientSession, "incoming:answer", Map.of("from", session.getId(), "answer", answer));
////            } else {
////                logger.warn("Recipient session not found for user id: {}", toUserId);
////            }
////        } else {
////            logger.error("Invalid 'to' user id received in call accepted message.");
////        }
////    }
////
////    @Override
////    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
////        sessions.remove(session.getId());
////
////        logger.info("Session closed with status: {}", status.toString());
////        broadcastToAll("user:disconnect", session.getId());
////    }
////
////    private void sendMessage(WebSocketSession session, String type, Map<String, Object> data) throws IOException {
////        if (session.isOpen()) {
////            Map<String, Object> messageData = new HashMap<>(data);
////            messageData.put("type", type);
////            String jsonMessage = new ObjectMapper().writeValueAsString(messageData);
////
////            // Use synchronized block to ensure sequential sending
////            synchronized (session) {
////                if (session.isOpen()) {
////                    session.sendMessage(new TextMessage(jsonMessage));
////                } else {
////                    logger.warn("Trying to send message to a closed session: {}", session.getId());
////                    sessions.remove(session.getId());
////                }
////            }
////        } else {
////            logger.warn("Trying to send message to a closed session: {}", session.getId());
////            sessions.remove(session.getId());
////        }
////    }
////
////    private void broadcastToAll(String type, String id) throws IOException {
////        Map<String, Object> data = Map.of("type", type, "id", id);
////        TextMessage message = new TextMessage(new ObjectMapper().writeValueAsString(data));
////
////        for (WebSocketSession session : sessions.values()) {
////            if (session.isOpen()) {
////                session.sendMessage(message);
////            } else {
////                logger.warn("Skipping sending message to closed session: {}", session.getId());
////                sessions.remove(session.getId());
////            }
////        }
////    }
////}
//
//
//
//
////import com.fasterxml.jackson.core.JsonProcessingException;
////import com.fasterxml.jackson.databind.ObjectMapper;
////import com.telemedicicne.telemedicicne.Entity.Doctor;
////import com.telemedicicne.telemedicicne.Entity.HealthOfficer;
////import com.telemedicicne.telemedicicne.Entity.Patient;
////import com.telemedicicne.telemedicicne.Repository.PatientRepository;
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Component;
////import org.springframework.web.socket.CloseStatus;
////import org.springframework.web.socket.TextMessage;
////import org.springframework.web.socket.WebSocketSession;
////import org.springframework.web.socket.handler.TextWebSocketHandler;
////
////import java.io.IOException;
////import java.util.Map;
////import java.util.concurrent.ConcurrentHashMap;
////
////@Component // or @Service
////public class SignalingHandler extends TextWebSocketHandler {
////
////    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
////    private static final Logger logger = LoggerFactory.getLogger(SignalingHandler.class);
////
////    @Autowired
////    private PatientRepository patientRepository; // Assuming you have repositories for Patient, Doctor, HealthOfficer
////
////    @Override
////    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
////        sessions.put(session.getId(), session);
////
////        broadcastToAll("users:joined", session.getId());
////    }
////
////    @Override
////    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
////        String payload = message.getPayload();
////        System.out.println("Received message: " + payload);
////
////        try {
////            ObjectMapper objectMapper = new ObjectMapper();
////            Map<String, Object> data = objectMapper.readValue(payload, Map.class);
////
////            String type = (String) data.get("type");
////            switch (type) {
////                case "video:callRequest":
////                    handleVideoCallRequest(session, data);
////                    break;
////                case "candidate":
////                    handleCandidate(session, data);
////                    break;
////                case "incoming:answer":
////                    handleCallAccepted(session, data);
////                    break;
////                default:
////                    logger.warn("Unknown message type received: {}", type);
////            }
////        } catch (IOException e) {
////            logger.error("Error parsing incoming message: {}", e.getMessage());
////        }
////    }
////
////
////
////    private void handleVideoCallRequest(WebSocketSession session, Map<String, Object> data) throws IOException {
////        String patientId = (String) data.get("patientId");
////
////        System.out.println("patient table patient id----------"+patientId);
////
////        // Fetch Patient details including associated HealthOfficer and Doctor
////        Patient patient = patientRepository.findById(Long.valueOf(patientId)).orElse(null);
////        if (patient != null) {
////            HealthOfficer healthOfficer = patient.getHealthOfficer();
////            Doctor doctor = patient.getDoctor();
////
////            // Validate if HealthOfficer is initiating the call
////            if (healthOfficer != null && healthOfficer.getSessionId().equals(session.getId())) {
////                // Optionally validate if the Doctor is available or online
////
////                // Send call request to Doctor
////                WebSocketSession doctorSession = sessions.get(doctor.getSessionId());
////                if (doctorSession != null && doctorSession.isOpen()) {
////                    sendMessage(doctorSession, "video:incomingCall", Map.of("from", session.getId(), "patientId", patientId));
////                } else {
////                    logger.warn("Doctor session not found or closed for doctor: {}", doctor.getDoctorId());
////                    // Handle case where Doctor session is closed or unavailable
////                }
////            } else {
////                logger.warn("Unauthorized attempt to initiate call by HealthOfficer: {}", session.getId());
////                // Handle unauthorized attempts
////            }
////        } else {
////            logger.error("Patient not found with ID: {}", patientId);
////            // Handle case where Patient is not found
////        }
////    }
////
////    private void handleCandidate(WebSocketSession session, Map<String, Object> data) throws IOException {
////        String from = (String) data.get("from");
////        String candidate = (String) data.get("candidate");
////
////        // Optionally validate if 'from' is a valid session ID or user ID
////
////        // Prepare the message to send to the recipient
////        Map<String, Object> messageData = Map.of("type", "candidate", "from", session.getId(), "candidate", candidate);
////
////        // Find the recipient session and send the ICE candidate message
////        WebSocketSession recipientSession = sessions.get(from);
////        if (recipientSession != null && recipientSession.isOpen()) {
////            sendMessage(recipientSession, "candidate", messageData);
////        } else {
////            logger.warn("Recipient session not found or closed for session ID: {}", from);
////            // Handle case where recipient session is not available
////        }
////    }
////
////
////    private void handleCallAccepted(WebSocketSession session, Map<String, Object> data) throws IOException {
////        String from = (String) data.get("from");
////        String patientId = (String) data.get("patientId");
////
////        // Optionally validate if 'from' is a valid session ID or user ID
////
////        // Prepare the message to send to the caller
////        Map<String, Object> messageData = Map.of("type", "incoming:answer", "from", session.getId());
////
////        // Find the caller session and send the call accepted message
////        WebSocketSession callerSession = sessions.get(from);
////        if (callerSession != null && callerSession.isOpen()) {
////            sendMessage(callerSession, "incoming:answer", messageData);
////        } else {
////            logger.warn("Caller session not found or closed for session ID: {}", from);
////            // Handle case where caller session is not available
////        }
////    }
////
////
////    @Override
////    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
////        sessions.remove(session.getId());
////
////        logger.info("Session closed with status: {}", status.toString());
////        broadcastToAll("user:disconnect", session.getId());
////    }
////
//////    private void sendMessage(WebSocketSession session, String type, Map<String, Object> data) throws IOException {
//////        if (session.isOpen()) {
//////            Map<String, Object> messageData = Map.of("type", type, "data", data);
//////            String jsonMessage = new ObjectMapper().writeValueAsString(messageData);
//////
//////            synchronized (session) {
//////                if (session.isOpen()) {
//////                    session.sendMessage(new TextMessage(jsonMessage));
//////                } else {
//////                    logger.warn("Trying to send message to a closed session: {}", session.getId());
//////                    sessions.remove(session.getId());
//////                }
//////            }
//////        } else {
//////            logger.warn("Trying to send message to a closed session: {}", session.getId());
//////            sessions.remove(session.getId());
//////        }
//////    }
//////private void sendMessage(WebSocketSession session, String type, Map<String, Object> data) {
//////    if (session.isOpen()) {
//////        Map<String, Object> messageData = Map.of("type", type, "data", data);
//////        String jsonMessage;
//////        try {
//////            jsonMessage = new ObjectMapper().writeValueAsString(messageData);
//////        } catch (JsonProcessingException e) {
//////            logger.error("Error converting message to JSON: {}", e.getMessage());
//////            return;
//////        }
//////
//////        synchronized (session) {
//////            try {
//////                if (session.isOpen()) {
//////                    session.sendMessage(new TextMessage(jsonMessage));
//////                } else {
//////                    logger.warn("Trying to send message to a closed session: {}", session.getId());
//////                    sessions.remove(session.getId());
//////                }
//////            } catch (IOException e) {
//////                logger.error("Error sending message: {}", e.getMessage());
//////            } catch (IllegalStateException e) {
//////                logger.warn("Session {} was in an invalid state for writing: {}", session.getId(), e.getMessage());
//////                // Handle the TEXT_PARTIAL_WRITING state or other invalid state if necessary
//////            }
//////        }
//////    } else {
//////        logger.warn("Trying to send message to a closed session: {}", session.getId());
//////        sessions.remove(session.getId());
//////    }
//////}
////private void sendMessage(WebSocketSession session, String type, Map<String, Object> data) {
////    if (session.isOpen()) {
////        ObjectMapper objectMapper = new ObjectMapper();
////        String jsonMessage;
////        try {
////            jsonMessage = objectMapper.writeValueAsString(Map.of("type", type, "data", data));
////        } catch (JsonProcessingException e) {
////            logger.error("Error converting message to JSON: {}", e.getMessage());
////            return;
////        }
////
////        try {
////            session.sendMessage(new TextMessage(jsonMessage));
////        } catch (IOException e) {
////            logger.error("Error sending message: {}", e.getMessage());
////        }
////    } else {
////        logger.warn("Trying to send message to a closed session: {}", session.getId());
////        sessions.remove(session.getId());
////    }
////}
////
////
////
////
////    private void broadcastToAll(String type, String id) throws IOException {
////        Map<String, Object> data = Map.of("type", type, "id", id);
////        TextMessage message = new TextMessage(new ObjectMapper().writeValueAsString(data));
////
////        for (WebSocketSession session : sessions.values()) {
////            if (session.isOpen()) {
////                session.sendMessage(message);
////            } else {
////                logger.warn("Skipping sending message to closed session: {}", session.getId());
////                sessions.remove(session.getId());
////            }
////        }
////    }
////}
//
//
////import com.fasterxml.jackson.core.JsonProcessingException;
////import com.fasterxml.jackson.core.type.TypeReference;
////import com.fasterxml.jackson.databind.ObjectMapper;
////import com.telemedicicne.telemedicicne.Entity.Doctor;
////import com.telemedicicne.telemedicicne.Entity.HealthOfficer;
////import com.telemedicicne.telemedicicne.Entity.Patient;
////import com.telemedicicne.telemedicicne.Repository.PatientRepository;
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Component;
////import org.springframework.web.socket.CloseStatus;
////import org.springframework.web.socket.TextMessage;
////import org.springframework.web.socket.WebSocketSession;
////import org.springframework.web.socket.handler.TextWebSocketHandler;
////
////import java.io.IOException;
////import java.util.Map;
////import java.util.concurrent.ConcurrentHashMap;
////
////@Component
////public class SignalingHandler extends TextWebSocketHandler {
////
////    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
////    private static final Logger logger = LoggerFactory.getLogger(SignalingHandler.class);
////
////    @Autowired
////    private PatientRepository patientRepository;
////
////    @Override
////    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
////        sessions.put(session.getId(), session);
////        broadcastToAll("users:joined", session.getId());
////    }
////
////    @Override
////    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
////        String payload = message.getPayload();
////        logger.info("Received message: {}", payload);
////
////        try {
////            ObjectMapper objectMapper = new ObjectMapper();
////            Map<String, Object> data = objectMapper.readValue(payload, new TypeReference<Map<String, Object>>() {});
////
////            String type = (String) data.get("type");
////            switch (type) {
////                case "video:callRequest":
////                    handleVideoCallRequest(session, data);
////                    break;
////                case "candidate":
////                    handleCandidate(session, data);
////                    break;
////                case "incoming:answer":
////                    handleCallAccepted(session, data);
////                    break;
////                default:
////                    logger.warn("Unknown message type received: {}", type);
////            }
////        } catch (IOException e) {
////            logger.error("Error parsing incoming message: {}", e.getMessage());
////        }
////    }
////
////    private void handleVideoCallRequest(WebSocketSession session, Map<String, Object> data) throws IOException {
////        String patientId = (String) data.get("patientId");
////
////        // Fetch Patient details including associated HealthOfficer and Doctor
////        Patient patient = patientRepository.findById(Long.parseLong(patientId)).orElse(null);
////        if (patient != null) {
////            HealthOfficer healthOfficer = patient.getHealthOfficer();
////            Doctor doctor = patient.getDoctor();
////
////            // Validate if HealthOfficer is initiating the call
////            if (healthOfficer != null && healthOfficer.getSessionId().equals(session.getId())) {
////                // Optionally validate if the Doctor is available or online
////
////                // Send call request to Doctor
////                WebSocketSession doctorSession = sessions.get(doctor.getSessionId());
////                if (doctorSession != null && doctorSession.isOpen()) {
////                    sendMessage(doctorSession, "video:incomingCall", Map.of("from", session.getId(), "patientId", patientId));
////                } else {
////                    logger.warn("Doctor session not found or closed for doctor: {}", doctor.getDoctorId());
////                    // Handle case where Doctor session is closed or unavailable
////                }
////            } else {
////                logger.warn("Unauthorized attempt to initiate call by HealthOfficer: {}", session.getId());
////                // Handle unauthorized attempts
////            }
////        } else {
////            logger.error("Patient not found with ID: {}", patientId);
////            // Handle case where Patient is not found
////        }
////    }
////
////    private void handleCandidate(WebSocketSession session, Map<String, Object> data) throws IOException {
////        String from = (String) data.get("from");
////        String candidate = (String) data.get("candidate");
////
////        // Optionally validate if 'from' is a valid session ID or user ID
////
////        // Prepare the message to send to the recipient
////        Map<String, Object> messageData = Map.of("type", "candidate", "from", session.getId(), "candidate", candidate);
////
////        // Find the recipient session and send the ICE candidate message
////        WebSocketSession recipientSession = sessions.get(from);
////        if (recipientSession != null && recipientSession.isOpen()) {
////            sendMessage(recipientSession, "candidate", messageData);
////        } else {
////            logger.warn("Recipient session not found or closed for session ID: {}", from);
////            // Handle case where recipient session is not available
////        }
////    }
////
////    private void handleCallAccepted(WebSocketSession session, Map<String, Object> data) throws IOException {
////        String from = (String) data.get("from");
////        String patientId = (String) data.get("patientId");
////
////        // Optionally validate if 'from' is a valid session ID or user ID
////
////        // Prepare the message to send to the caller
////        Map<String, Object> messageData = Map.of("type", "incoming:answer", "from", session.getId());
////
////        // Find the caller session and send the call accepted message
////        WebSocketSession callerSession = sessions.get(from);
////        if (callerSession != null && callerSession.isOpen()) {
////            sendMessage(callerSession, "incoming:answer", messageData);
////        } else {
////            logger.warn("Caller session not found or closed for session ID: {}", from);
////            // Handle case where caller session is not available
////        }
////    }
////
////    @Override
////    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
////        sessions.remove(session.getId());
////        logger.info("Session closed with status: {}", status.toString());
////        broadcastToAll("user:disconnect", session.getId());
////    }
////
////    private void sendMessage(WebSocketSession session, String type, Map<String, Object> data) {
////        if (session.isOpen()) {
////            ObjectMapper objectMapper = new ObjectMapper();
////            String jsonMessage;
////            try {
////                jsonMessage = objectMapper.writeValueAsString(Map.of("type", type, "data", data));
////            } catch (JsonProcessingException e) {
////                logger.error("Error converting message to JSON: {}", e.getMessage());
////                return;
////            }
////
////            try {
////                session.sendMessage(new TextMessage(jsonMessage));
////            } catch (IOException e) {
////                logger.error("Error sending message: {}", e.getMessage());
////            }
////        } else {
////            logger.warn("Trying to send message to a closed session: {}", session.getId());
////            sessions.remove(session.getId());
////        }
////    }
////
////    private void broadcastToAll(String type, String id) throws IOException {
////        Map<String, Object> data = Map.of("type", type, "id", id);
////        TextMessage message = new TextMessage(new ObjectMapper().writeValueAsString(data));
////
////        for (WebSocketSession session : sessions.values()) {
////            if (session.isOpen()) {
////                session.sendMessage(message);
////            } else {
////                logger.warn("Skipping sending message to closed session: {}", session.getId());
////                sessions.remove(session.getId());
////            }
////        }
////    }
////}
//
//
////
////
////import com.fasterxml.jackson.core.JsonProcessingException;
////import com.fasterxml.jackson.core.type.TypeReference;
////import com.fasterxml.jackson.databind.ObjectMapper;
////import com.telemedicicne.telemedicicne.Entity.Doctor;
////import com.telemedicicne.telemedicicne.Entity.HealthOfficer;
////import com.telemedicicne.telemedicicne.Entity.Patient;
////import com.telemedicicne.telemedicicne.Repository.PatientRepository;
////
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Component;
////import org.springframework.web.socket.CloseStatus;
////import org.springframework.web.socket.TextMessage;
////import org.springframework.web.socket.WebSocketSession;
////import org.springframework.web.socket.handler.TextWebSocketHandler;
////
////import java.io.IOException;
////import java.util.Map;
////import java.util.concurrent.ConcurrentHashMap;
////
////@Component
////public class SignalingHandler extends TextWebSocketHandler {
////
////    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
////    private static final Logger logger = LoggerFactory.getLogger(SignalingHandler.class);
////
////    @Autowired
////    private PatientRepository patientRepository;
////
////    @Override
////    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
////        sessions.put(session.getId(), session);
////        broadcastToAll("users:joined", session.getId());
////    }
////
////    @Override
////    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
////        String payload = message.getPayload();
////        logger.info("Received message: {}", payload);
////
////        try {
////            ObjectMapper objectMapper = new ObjectMapper();
////            Map<String, Object> data = objectMapper.readValue(payload, new TypeReference<Map<String, Object>>() {});
////
////            String type = (String) data.get("type");
////            switch (type) {
////                case "video:callRequest":
////                    handleVideoCallRequest(session, data);
////                    break;
////                case "candidate":
////                    handleCandidate(session, data);
////                    break;
////                case "incoming:answer":
////                    handleCallAccepted(session, data);
////                    break;
////                default:
////                    logger.warn("Unknown message type received: {}", type);
////            }
////        } catch (IOException e) {
////            logger.error("Error parsing incoming message: {}", e.getMessage());
////        }
////    }
////
////    private void handleVideoCallRequest(WebSocketSession session, Map<String, Object> data) throws IOException {
////        String patientId = (String) data.get("patientId");
////
////
////        // Fetch Patient details including associated HealthOfficer and Doctor
////        Patient patient = patientRepository.findById(Long.parseLong(patientId)).orElse(null);
////        if (patient != null) {
////            HealthOfficer healthOfficer = patient.getHealthOfficer();
////            Doctor doctor = patient.getDoctor();
////
////            // Validate if HealthOfficer is initiating the call
////            if (healthOfficer != null && healthOfficer.getSessionId().equals(session.getId())) {
////                // Optionally validate if the Doctor is available or online
////
////                // Send call request to Doctor
////                WebSocketSession doctorSession = sessions.get(doctor.getSessionId());
////                if (doctorSession != null && doctorSession.isOpen()) {
////                    sendMessage(doctorSession, "video:incomingCall", Map.of("from", session.getId(), "patientId", patientId));
////                } else {
////                    logger.warn("Doctor session not found or closed for doctor: {}", doctor.getDoctorId());
////                    // Handle case where Doctor session is closed or unavailable
////                }
////            } else {
////                logger.warn("Unauthorized attempt to initiate call by HealthOfficer: {}", session.getId());
////                // Handle unauthorized attempts
////            }
////        } else {
////            logger.error("Patient not found with ID: {}", patientId);
////            // Handle case where Patient is not found
////        }
////    }
////
//////    private void handleCandidate(WebSocketSession session, Map<String, Object> data) throws IOException {
//////        String from = (String) data.get("from");
//////        String candidate = (String) data.get("candidate");
//////
//////        // Optionally validate if 'from' is a valid session ID or user ID
//////
//////        // Prepare the message to send to the recipient
//////        Map<String, Object> messageData = Map.of("type", "candidate", "candidate", candidate);
//////        sendMessage(sessions.get(from), "candidate", messageData);
//////    }
////
////    private void handleCandidate(WebSocketSession session, Map<String, Object> data) throws IOException {
////        String from = (String) data.get("from");
////        Map<String, Object> candidateData = (Map<String, Object>) data.get("candidate");
////
////        // Extract candidate information from candidateData map
////        String candidate = (String) candidateData.get("candidate");
////
////        // Optionally validate if 'from' is a valid session ID or user ID
////
////        // Prepare the message to send to the recipient
////        Map<String, Object> messageData = Map.of("type", "candidate", "candidate", candidate);
////        sendMessage(sessions.get(from), "candidate", messageData);
////    }
////
////    private void handleCallAccepted(WebSocketSession session, Map<String, Object> data) throws IOException {
////        String from = (String) data.get("from");
////
////        // Optionally validate if 'from' is a valid session ID or user ID
////
////        // Prepare the message to send to the recipient
////        Map<String, Object> messageData = Map.of("type", "incoming:answer");
////        sendMessage(sessions.get(from), "incoming:answer", messageData);
////    }
////
////    @Override
////    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
////        sessions.remove(session.getId());
////        broadcastToAll("users:left", session.getId());
////    }
////
////    private void broadcastToAll(String type, String sessionId) throws IOException {
////        for (WebSocketSession session : sessions.values()) {
////            if (session.isOpen()) {
////                sendMessage(session, type, Map.of("sessionId", sessionId));
////            }
////        }
////    }
////
//
////    private void sendMessage(WebSocketSession session, String type, Map<String, Object> data) {
////        try {
////            if (session.isOpen()) {
////                ObjectMapper objectMapper = new ObjectMapper();
////                String payload = objectMapper.writeValueAsString(Map.of("type", type, "data", data));
////                session.sendMessage(new TextMessage(payload));
////            } else {
////                logger.warn("WebSocket session is not open. Cannot send message.");
////                // Handle accordingly, e.g., retry or notify the user
////            }
////        } catch (IOException e) {
////            logger.error("Error sending message over WebSocket: {}", e.getMessage());
////            // Handle the exception, retry, or log the issue
////        }
////    }
////
////}
////
//
//
////
////
////import com.fasterxml.jackson.core.type.TypeReference;
////import com.fasterxml.jackson.databind.ObjectMapper;
////import com.telemedicicne.telemedicicne.Entity.Doctor;
////import com.telemedicicne.telemedicicne.Entity.HealthOfficer;
////import com.telemedicicne.telemedicicne.Entity.Patient;
////
////import com.telemedicicne.telemedicicne.Repository.DoctorRepository;
////import com.telemedicicne.telemedicicne.Repository.HealthOfficerRepository;
////import com.telemedicicne.telemedicicne.Repository.PatientRepository;
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Component;
////import org.springframework.web.socket.CloseStatus;
////import org.springframework.web.socket.TextMessage;
////import org.springframework.web.socket.WebSocketSession;
////import org.springframework.web.socket.handler.TextWebSocketHandler;
////
////import java.io.IOException;
////import java.util.Map;
////import java.util.concurrent.ConcurrentHashMap;
////
////@Component
////public class SignalingHandler extends TextWebSocketHandler {
////
////    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
////    private static final Logger logger = LoggerFactory.getLogger(SignalingHandler.class);
////
////    @Autowired
////    private PatientRepository patientRepository;
////
//////    @Override
//////    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//////        sessions.put(session.getId(), session);
//////        broadcastToAll("users:joined", session.getId());
//////    }
////
//////    @Override
//////    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//////        String userType = (String) session.getAttributes().get("userType");
//////        Long userId = (Long) session.getAttributes().get("userId");
//////
//////        if ("doctor".equals(userType)) {
//////            Doctor doctor = doctorRepository.findById(userId).orElse(null);
//////            if (doctor != null) {
//////                doctor.setSessionId(session.getId());
//////                doctorRepository.save(doctor);
//////            }
//////        } else {
//////            HealthOfficer healthOfficer = healthOfficerRepository.findById(userId).orElse(null);
//////            if (healthOfficer != null) {
//////                healthOfficer.setSessionId(session.getId());
//////                healthOfficerRepository.save(healthOfficer);
//////            }
//////        }
//////
//////        sessions.put(session.getId(), session);
//////        broadcastToAll("users:joined", session.getId());
//////    }
//////@Override
//////public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//////    String userType = (String) session.getAttributes().get("userType");
//////    Long userId = (Long) session.getAttributes().get("userId");
//////
//////    if ("doctor".equals(userType) && userId != null) {
//////        Doctor doctor = doctorRepository.findById(userId).orElse(null);
//////        if (doctor != null) {
//////            doctor.setSessionId(session.getId());
//////            doctorRepository.save(doctor);
//////        }
//////    } else if (userId != null) {
//////        HealthOfficer healthOfficer = healthOfficerRepository.findById(userId).orElse(null);
//////        if (healthOfficer != null) {
//////            healthOfficer.setSessionId(session.getId());
//////            healthOfficerRepository.save(healthOfficer);
//////        }
//////    }
//////
//////    sessions.put(session.getId(), session);
//////    broadcastToAll("users:joined", session.getId());
//////}
////@Override
////public void afterConnectionEstablished(WebSocketSession session) throws Exception {
////    sessions.put(session.getId(), session);
////    System.out.println("Connection established with session id: " + session.getId());
////}
////
////
//////    @Override
//////    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//////        String payload = message.getPayload();
//////        logger.info("Received message: {}", payload);
//////
//////        try {
//////            ObjectMapper objectMapper = new ObjectMapper();
//////            Map<String, Object> data = objectMapper.readValue(payload, new TypeReference<Map<String, Object>>() {});
//////
//////            String type = (String) data.get("type");
//////            switch (type) {
//////                case "video:callRequest":
//////                    handleVideoCallRequest(session, data);
//////                    break;
//////                case "candidate":
//////                    handleCandidate(session, data);
//////                    break;
//////                case "incoming:answer":
//////                    handleCallAccepted(session, data);
//////                    break;
//////                default:
//////                    logger.warn("Unknown message type received: {}", type);
//////            }
//////        } catch (IOException e) {
//////            logger.error("Error parsing incoming message: {}", e.getMessage());
//////        }
//////    }
////@Override
////protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
////    String payload = message.getPayload();
////    System.out.println("Received message: " + payload);
////    // Handle the received message
////}
////
////
//////    private void handleVideoCallRequest(WebSocketSession session, Map<String, Object> data) throws IOException {
//////        String patientId = (String) data.get("patientId");
//////
//////
//////        System.out.println("patient table patient id ---------"+patientId);
//////
//////
//////        // Fetch Patient details including associated HealthOfficer and Doctor
//////        Patient patient = patientRepository.findById(Long.parseLong(patientId)).orElse(null);
//////        System.out.println("patient table  ---------"+patient);
//////
//////
//////        if (patient != null) {
//////            HealthOfficer healthOfficer = patient.getHealthOfficer();
//////            Doctor doctor = patient.getDoctor();
//////
//////            // Validate if HealthOfficer is initiating the call
//////            if (healthOfficer != null && healthOfficer.getSessionId().equals(session.getId())) {
//////                // Optionally validate if the Doctor is available or online
//////
//////                // Send call request to Doctor
//////                WebSocketSession doctorSession = sessions.get(doctor.getSessionId());
//////                if (doctorSession != null && doctorSession.isOpen()) {
//////                    sendMessage(doctorSession, "video:incomingCall", Map.of("from", session.getId(), "patientId", patientId));
//////                } else {
//////                    logger.warn("Doctor session not found or closed for doctor: {}", doctor.getDoctorId());
//////                    // Handle case where Doctor session is closed or unavailable
//////                }
//////            } else {
//////                logger.warn("Unauthorized attempt to initiate call by HealthOfficer: {}", session.getId());
//////                // Handle unauthorized attempts
//////            }
//////        } else {
//////            logger.error("Patient not found with ID: {}", patientId);
//////            // Handle case where Patient is not found
//////        }
//////    }
////
////    @Autowired
////    private DoctorRepository doctorRepository;
////    @Autowired
////    private HealthOfficerRepository healthOfficerRepository;
////private void handleVideoCallRequest(WebSocketSession session, Map<String, Object> data) throws IOException {
////    String callerId = (String) data.get("callerId");
////    String receiverId = (String) data.get("receiverId");
////    String receiverType = (String) data.get("receiverType");
////
////    System.out.println("Caller ID: " + callerId);
////    System.out.println("Receiver ID: " + receiverId);
////    System.out.println("Receiver Type: " + receiverType);
////
////    // Fetch the receiver details based on the type (Doctor or HealthOfficer)
////    WebSocketSession receiverSession;
////    if ("doctor".equals(receiverType)) {
////        Doctor doctor = doctorRepository.findById(Long.parseLong(receiverId)).orElse(null);
////        if (doctor != null) {
////            receiverSession = sessions.get(doctor.getSessionId());
////        } else {
////            logger.error("Doctor not found with ID: {}", receiverId);
////            return;
////        }
////    } else {
////        HealthOfficer healthOfficer = healthOfficerRepository.findById(Long.parseLong(receiverId)).orElse(null);
////        if (healthOfficer != null) {
////            receiverSession = sessions.get(healthOfficer.getSessionId());
////        } else {
////            logger.error("HealthOfficer not found with ID: {}", receiverId);
////            return;
////        }
////    }
////
////    // Send call request to the receiver
////    if (receiverSession != null && receiverSession.isOpen()) {
////        sendMessage(receiverSession, "video:incomingCall", Map.of("from", session.getId(), "callerId", callerId));
////    } else {
////        logger.warn("Receiver session not found or closed for ID: {}", receiverId);
////    }
////}
////
////
////    //    private void handleCandidate(WebSocketSession session, Map<String, Object> data) throws IOException {
//////        String from = (String) data.get("from");
//////        Map<String, Object> candidateData = (Map<String, Object>) data.get("candidate");
//////
//////        // Optionally validate if 'from' is a valid session ID or user ID
//////
//////        // Prepare the message to send to the recipient
//////        Map<String, Object> messageData = Map.of("type", "candidate", "candidate", candidateData);
//////        sendMessage(sessions.get(from), "candidate", messageData);
//////    }
////private void handleCandidate(WebSocketSession session, Map<String, Object> data) throws IOException {
////    String from = (String) data.get("from");
////    Map<String, Object> candidateData = (Map<String, Object>) data.get("candidate");
////
////    if (from == null) {
////        logger.error("Received candidate message with null 'from' key.");
////        return;  // or handle the null case appropriately
////    }
////
////    WebSocketSession doctorSession = sessions.get(from);
////    if (doctorSession != null && doctorSession.isOpen()) {
////        sendMessage(doctorSession, "candidate", Map.of("candidate", candidateData));
////    } else {
////        logger.warn("Doctor session not found or closed for session ID: {}", from);
////        // Handle case where Doctor session is closed or unavailable
////    }
////}
////
////    private void handleCallAccepted(WebSocketSession session, Map<String, Object> data) throws IOException {
////        String from = (String) data.get("from");
////        String patientId = (String) data.get("patientId");
////
////        System.out.println("patient table patient id ---------"+patientId);
////
////        // Validate if 'from' is a valid session ID or user ID
////
////        // Notify the HealthOfficer that the call has been accepted
////        WebSocketSession healthOfficerSession = sessions.get(from);
////        if (healthOfficerSession != null && healthOfficerSession.isOpen()) {
////            sendMessage(healthOfficerSession, "video:callAccepted", Map.of("patientId", patientId));
////        } else {
////            logger.warn("HealthOfficer session not found or closed for session ID: {}", from);
////            // Handle case where HealthOfficer session is closed or unavailable
////        }
////    }
////
////    private void broadcastToAll(String type, String sessionId) throws IOException {
////        for (WebSocketSession session : sessions.values()) {
////            if (session.isOpen()) {
////                sendMessage(session, type, Map.of("sessionId", sessionId));
////            }
////        }
////    }
////
////    private void sendMessage(WebSocketSession session, String type, Map<String, Object> data) throws IOException {
////        ObjectMapper mapper = new ObjectMapper();
////        String json = mapper.writeValueAsString(data);
////        session.sendMessage(new TextMessage(json));
////        logger.info("Sent message type '{}' to session '{}'", type, session.getId());
////    }
////
//////    @Override
//////    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//////        sessions.remove(session.getId());
//////        broadcastToAll("users:left", session.getId());
//////    }
////@Override
////public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
////    sessions.remove(session.getId());
////    System.out.println("Connection closed with session id: " + session.getId());
////}
////
////}
////
//
////
////import com.fasterxml.jackson.core.type.TypeReference;
////import com.fasterxml.jackson.databind.ObjectMapper;
////import com.telemedicicne.telemedicicne.Entity.Doctor;
////import com.telemedicicne.telemedicicne.Entity.HealthOfficer;
////import com.telemedicicne.telemedicicne.Repository.DoctorRepository;
////import com.telemedicicne.telemedicicne.Repository.HealthOfficerRepository;
////import com.telemedicicne.telemedicicne.Repository.PatientRepository;
////
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Component;
////import org.springframework.web.socket.CloseStatus;
////import org.springframework.web.socket.TextMessage;
////import org.springframework.web.socket.WebSocketSession;
////import org.springframework.web.socket.handler.TextWebSocketHandler;
////
////import java.io.IOException;
////import java.util.Map;
////import java.util.concurrent.ConcurrentHashMap;
////
////@Component
////public class SignalingHandler extends TextWebSocketHandler {
////
////    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
////    private static final Logger logger = LoggerFactory.getLogger(SignalingHandler.class);
////
////    @Autowired
////    private PatientRepository patientRepository;
////
////    @Autowired
////    private DoctorRepository doctorRepository;
////
////    @Autowired
////    private HealthOfficerRepository healthOfficerRepository;
////
////    @Override
////    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
////        sessions.put(session.getId(), session);
////        System.out.println("Connection established with session id: " + session.getId());
////    }
////
////    @Override
////    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
////        String payload = message.getPayload();
////        System.out.println("Received message: " + payload);
////
////        try {
////            ObjectMapper objectMapper = new ObjectMapper();
////            Map<String, Object> data = objectMapper.readValue(payload, new TypeReference<Map<String, Object>>() {});
////
////            String type = (String) data.get("type");
////            switch (type) {
////                case "video:callRequest":
////                    handleVideoCallRequest(session, data);
////                    break;
////                case "candidate":
////                    handleCandidate(session, data);
////                    break;
////                case "incoming:answer":
////                    handleCallAccepted(session, data);
////                    break;
////                default:
////                    logger.warn("Unknown message type received: {}", type);
////            }
////        } catch (IOException e) {
////            logger.error("Error parsing incoming message: {}", e.getMessage());
////        }
////    }
////
//////    private void handleVideoCallRequest(WebSocketSession session, Map<String, Object> data) throws IOException {
//////        String callerId = (String) data.get("callerId");
//////        String receiverId = (String) data.get("receiverId");
//////        String receiverType = (String) data.get("receiverType");
//////
//////        System.out.println("Caller ID: " + callerId);
//////        System.out.println("Receiver ID: " + receiverId);
//////        System.out.println("Receiver Type: " + receiverType);
//////
//////        WebSocketSession receiverSession;
//////        if ("doctor".equals(receiverType)) {
////////            Doctor doctor = doctorRepository.findById(Long.parseLong(receiverId)).orElse(null);
//////            Doctor doctor = doctorRepository.findByEmailDirect(receiverId);
//////
//////            if (doctor != null) {
//////                receiverSession = sessions.get(doctor.getSessionId());
//////            } else {
//////                logger.error("Doctor not found with ID: {}", receiverId);
//////                return;
//////            }
//////        } else {
//////            HealthOfficer healthOfficer = healthOfficerRepository.findByEmail(receiverId).orElse(null);
//////            if (healthOfficer != null) {
//////                receiverSession = sessions.get(healthOfficer.getSessionId());
//////            } else {
//////                logger.error("HealthOfficer not found with ID: {}", receiverId);
//////                return;
//////            }
//////        }
//////
//////        if (receiverSession != null && receiverSession.isOpen()) {
//////            sendMessage(receiverSession, "video:incomingCall", Map.of("from", session.getId(), "callerId", callerId));
//////        } else {
//////            logger.warn("Receiver session not found or closed for ID: {}", receiverId);
//////        }
//////    }
////
////
//////    private void handleVideoCallRequest(WebSocketSession session, Map<String, Object> data) throws IOException {
//////        String callerId = (String) data.get("callerId");
//////        String receiverId = (String) data.get("receiverId");
//////        String receiverType = (String) data.get("receiverType");
//////
//////        System.out.println("Caller ID: " + callerId);
//////        System.out.println("Receiver ID: " + receiverId);
//////        System.out.println("Receiver Type: " + receiverType);
//////
//////        WebSocketSession receiverSession = null;
//////        System.out.println("session -----------------"+receiverSession);
//////
//////        try {
//////            if ("doctor".equals(receiverType)) {
//////                Doctor doctor = doctorRepository.findByEmailDirect(receiverId);
//////                if (doctor != null) {
//////                    receiverSession = sessions.get(doctor.getSessionId());
//////                    if (receiverSession == null) {
//////                        logger.error("Receiver session not found for doctor ID: {}", receiverId);
//////                        return;
//////                    }
//////                } else {
//////                    logger.error("Doctor not found with ID: {}", receiverId);
//////                    return;
//////                }
//////            } else {
//////                HealthOfficer healthOfficer = healthOfficerRepository.findByEmail(receiverId).orElse(null);
//////                if (healthOfficer != null) {
//////                    receiverSession = sessions.get(healthOfficer.getSessionId());
//////                    if (receiverSession == null) {
//////                        logger.error("Receiver session not found for health officer ID: {}", receiverId);
//////                        return;
//////                    }
//////                } else {
//////                    logger.error("HealthOfficer not found with ID: {}", receiverId);
//////                    return;
//////                }
//////            }
//////
//////            if (receiverSession.isOpen()) {
//////                sendMessage(receiverSession, "video:incomingCall", Map.of("from", session.getId(), "callerId", callerId));
//////            } else {
//////                logger.warn("Receiver session is closed for ID: {}", receiverId);
//////            }
//////        } catch (Exception e) {
//////            logger.error("Exception while handling video call request: {}", e.getMessage(), e);
//////            throw e;
//////        }
//////    }
////private void handleVideoCallRequest(WebSocketSession session, Map<String, Object> data) throws IOException {
////    String callerId = (String) data.get("callerId");
////    String receiverId = (String) data.get("receiverId");
////    String receiverType = (String) data.get("receiverType");
////
////    System.out.println("Caller ID: " + callerId);
////    System.out.println("Receiver ID: " + receiverId);
////    System.out.println("Receiver Type: " + receiverType);
////
////    WebSocketSession receiverSession = null;
////
////    try {
////        if ("doctor".equals(receiverType)) {
////            Doctor doctor = doctorRepository.findByEmailDirect(receiverId);
////            if (doctor != null) {
////                System.out.println("Doctor found: " + doctor.getEmail() + ", Session ID: " + doctor.getSessionId());
////                receiverSession = sessions.get(doctor.getSessionId());
////                if (receiverSession == null) {
////                    logger.error("Receiver session not found for doctor ID: {}", receiverId);
////                    return;
////                }
////            } else {
////                logger.error("Doctor not found with ID: {}", receiverId);
////                return;
////            }
////        } else {
////            HealthOfficer healthOfficer = healthOfficerRepository.findByEmail(receiverId).orElse(null);
////            if (healthOfficer != null) {
////                System.out.println("Health Officer found: " + healthOfficer.getEmail() + ", Session ID: " + healthOfficer.getSessionId());
////                receiverSession = sessions.get(healthOfficer.getSessionId());
////                if (receiverSession == null) {
////                    logger.error("Receiver session not found for health officer ID: {}", receiverId);
////                    return;
////                }
////            } else {
////                logger.error("HealthOfficer not found with ID: {}", receiverId);
////                return;
////            }
////        }
////
////        System.out.println("Receiver session found: " + receiverSession.getId());
////
////        if (receiverSession.isOpen()) {
////            sendMessage(receiverSession, "video:incomingCall", Map.of("from", session.getId(), "callerId", callerId));
////        } else {
////            logger.warn("Receiver session is closed for ID: {}", receiverId);
////        }
////    } catch (Exception e) {
////        logger.error("Exception while handling video call request: {}", e.getMessage(), e);
////        throw e;
////    }
////}
////
////
////    //    private void handleCandidate(WebSocketSession session, Map<String, Object> data) throws IOException {
//////        String from = (String) data.get("from");
//////        Map<String, Object> candidateData = (Map<String, Object>) data.get("candidate");
//////
//////        if (from == null) {
//////            logger.error("Received candidate message with null 'from' key.");
//////            return;
//////        }
//////
//////        WebSocketSession receiverSession = sessions.get(from);
//////        if (receiverSession != null && receiverSession.isOpen()) {
//////            sendMessage(receiverSession, "candidate", Map.of("candidate", candidateData));
//////        } else {
//////            logger.warn("Receiver session not found or closed for ID: {}", from);
//////        }
//////    }
////private void handleCandidate(WebSocketSession session, Map<String, Object> data) throws IOException {
////    Map<String, Object> candidateData = (Map<String, Object>) data.get("payload");
////    String from = (String) candidateData.get("from");
////
////    if (from == null) {
////        logger.error("Received candidate message with null 'from' key.");
////        return;
////    }
////
////    WebSocketSession receiverSession = sessions.get(from);
////    if (receiverSession != null && receiverSession.isOpen()) {
////        sendMessage(receiverSession, "candidate", candidateData);  // Send candidateData directly
////    } else {
////        logger.warn("Receiver session not found or closed for ID: {}", from);
////    }
////}
////
////
////    private void handleCallAccepted(WebSocketSession session, Map<String, Object> data) throws IOException {
////        String from = (String) data.get("from");
////        Map<String, Object> answerData = (Map<String, Object>) data.get("answer");
////
////        if (from == null) {
////            logger.error("Received answer message with null 'from' key.");
////            return;
////        }
////
////        WebSocketSession receiverSession = sessions.get(from);
////        if (receiverSession != null && receiverSession.isOpen()) {
////            sendMessage(receiverSession, "incoming:answer", Map.of("answer", answerData));
////        } else {
////            logger.warn("Receiver session not found or closed for ID: {}", from);
////        }
////    }
////
////    private void sendMessage(WebSocketSession session, String type, Map<String, Object> payload) throws IOException {
////        ObjectMapper objectMapper = new ObjectMapper();
////        Map<String, Object> message = Map.of("type", type, "payload", payload);
////        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
////    }
////
////    @Override
////    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
////        sessions.remove(session.getId());
////        logger.info("Connection closed with session id: {} and status: {}", session.getId(), status);
////    }
////}
//
//
//
//
////almost final
//
//
////import com.fasterxml.jackson.core.type.TypeReference;
////import com.fasterxml.jackson.databind.ObjectMapper;
////import com.telemedicicne.telemedicicne.Entity.Doctor;
////import com.telemedicicne.telemedicicne.Entity.HealthOfficer;
////import com.telemedicicne.telemedicicne.Repository.DoctorRepository;
////import com.telemedicicne.telemedicicne.Repository.HealthOfficerRepository;
////import com.telemedicicne.telemedicicne.Repository.PatientRepository;
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Component;
////import org.springframework.web.socket.CloseStatus;
////import org.springframework.web.socket.TextMessage;
////import org.springframework.web.socket.WebSocketSession;
////import org.springframework.web.socket.handler.TextWebSocketHandler;
////
////import java.io.IOException;
////import java.util.Map;
////import java.util.concurrent.ConcurrentHashMap;
////
////@Component
////public class SignalingHandler extends TextWebSocketHandler {
////
////    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
////    private static final Logger logger = LoggerFactory.getLogger(SignalingHandler.class);
////
////    @Autowired
////    private PatientRepository patientRepository;
////
////    @Autowired
////    private DoctorRepository doctorRepository;
////
////    @Autowired
////    private HealthOfficerRepository healthOfficerRepository;
////
////    @Override
////    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
////        sessions.put(session.getId(), session);
////        System.out.println("Connection established with session id: " + session.getId());
////    }
////
////    @Override
////    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
////        String payload = message.getPayload();
////        System.out.println("Received message: " + payload);
////
////        try {
////            ObjectMapper objectMapper = new ObjectMapper();
////            Map<String, Object> data = objectMapper.readValue(payload, new TypeReference<Map<String, Object>>() {});
////
////            String type = (String) data.get("type");
////            System.out.println("type sdf---------------"+type);
////            switch (type) {
////                case "video:callRequest":
////                    handleVideoCallRequest(session, data);
////                    break;
////                case "candidate":
////                    handleCandidate(session, data);
////                    break;
////                case "incoming:answer":
////                    handleCallAccepted(session, data);
////                    break;
////                default:
////                    logger.warn("Unknown message type received: {}", type);
////            }
////        } catch (IOException e) {
////            logger.error("Error parsing incoming message: {}", e.getMessage());
////        }
////    }
////
////    private void handleVideoCallRequest(WebSocketSession session, Map<String, Object> data) throws IOException {
////        String callerId = (String) data.get("callerId");
////        String receiverId = (String) data.get("receiverId");
////        String receiverType = (String) data.get("receiverType");
////
////        System.out.println("Caller ID: " + callerId);
////        System.out.println("Receiver ID: " + receiverId);
////        System.out.println("Receiver Type: " + receiverType);
////
////        WebSocketSession receiverSession = null;
////
////        try {
////            if ("doctor".equals(receiverType)) {
////                Doctor doctor = doctorRepository.findByEmailDirect(receiverId);
////                if (doctor != null) {
////                    System.out.println("Doctor found: " + doctor.getEmail() + ", Session ID: " + doctor.getSessionId());
////                    receiverSession = sessions.get(doctor.getSessionId());
////                    if (receiverSession == null) {
////                        logger.error("Receiver session not found for doctor ID: {}", receiverId);
////                        return;
////                    }
////                } else {
////                    logger.error("Doctor not found with ID: {}", receiverId);
////                    return;
////                }
////            } else {
////                HealthOfficer healthOfficer = healthOfficerRepository.findByEmail(receiverId).orElse(null);
////                if (healthOfficer != null) {
////                    System.out.println("Health Officer found: " + healthOfficer.getEmail() + ", Session ID: " + healthOfficer.getSessionId());
////                    receiverSession = sessions.get(healthOfficer.getSessionId());
////                    if (receiverSession == null) {
////                        logger.error("Receiver session not found for health officer ID: {}", receiverId);
////                        return;
////                    }
////                } else {
////                    logger.error("HealthOfficer not found with ID: {}", receiverId);
////                    return;
////                }
////            }
////
////            System.out.println("Receiver session found: " + receiverSession.getId());
////
////            if (receiverSession.isOpen()) {
////                sendMessage(receiverSession, "video:incomingCall", Map.of("from", session.getId(), "callerId", callerId));
////            } else {
////                logger.warn("Receiver session is closed for ID: {}", receiverId);
////            }
////        } catch (Exception e) {
////            logger.error("Exception while handling video call request: {}", e.getMessage(), e);
////            throw e;
////        }
////    }
////
////    private void handleCandidate(WebSocketSession session, Map<String, Object> data) throws IOException {
////        Map<String, Object> candidateData = (Map<String, Object>) data.get("payload");
////        String from = (String) candidateData.get("from");
////
////        if (from == null) {
////            logger.error("Received candidate message with null 'from' key.");
////            return;
////        }
////
////        WebSocketSession receiverSession = sessions.get(from);
////        if (receiverSession != null && receiverSession.isOpen()) {
////            sendMessage(receiverSession, "candidate", candidateData);  // Send candidateData directly
////        } else {
////            logger.warn("Receiver session not found or closed for ID: {}", from);
////        }
////    }
////
////    private void handleCallAccepted(WebSocketSession session, Map<String, Object> data) throws IOException {
////        String from = (String) data.get("from");
////        Map<String, Object> answerData = (Map<String, Object>) data.get("answer");
////
////        if (from == null) {
////            logger.error("Received answer message with null 'from' key.");
////            return;
////        }
////
////        WebSocketSession receiverSession = sessions.get(from);
////        if (receiverSession != null && receiverSession.isOpen()) {
////            sendMessage(receiverSession, "incoming:answer", Map.of("answer", answerData));
////        } else {
////            logger.warn("Receiver session not found or closed for ID: {}", from);
////        }
////    }
////
////    private void sendMessage(WebSocketSession session, String type, Map<String, Object> payload) throws IOException {
////        ObjectMapper objectMapper = new ObjectMapper();
////        Map<String, Object> message = Map.of("type", type, "payload", payload);
////        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
////    }
////
////    @Override
////    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
////        sessions.remove(session.getId());
////        logger.info("Connection closed with session id: {} and status: {}", session.getId(), status);
////    }
////}
////
//
//
//
//
//
//
//
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.telemedicicne.telemedicicne.Entity.Doctor;
//import com.telemedicicne.telemedicicne.Entity.HealthOfficer;
//import com.telemedicicne.telemedicicne.Repository.DoctorRepository;
//import com.telemedicicne.telemedicicne.Repository.HealthOfficerRepository;
//import com.telemedicicne.telemedicicne.Repository.PatientRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Component
//public class SignalingHandler extends TextWebSocketHandler {
//
//    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
//
//    private static final Logger logger = LoggerFactory.getLogger(SignalingHandler.class);
//
//    @Autowired
//    private PatientRepository patientRepository;
//
//    @Autowired
//    private DoctorRepository doctorRepository;
//
//    @Autowired
//    private HealthOfficerRepository healthOfficerRepository;
//
////    @Override
////    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
////        sessions.put(session.getId(), session);
////        System.out.println("Connection established with session: "+session);
////        System.out.println("Connection established with session id: " + session.getId());
////    }
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        sessions.put(session.getId(), session);
//        System.out.println("Connection established with session id: " + session.getId());
//    }
//
//    // Add a method to print sessions map content
//    public void printSessions() {
//        System.out.println("Sessions Map:");
//        for (Map.Entry<String, WebSocketSession> entry : sessions.entrySet()) {
//            System.out.println("Session ID: " + entry.getKey() + ", Session: " + entry.getValue());
//        }
//    }
//
////@Override
////public void afterConnectionEstablished(WebSocketSession session) throws Exception {
////    // Assuming the email is part of the session URI or headers
////    String email = (String) session.getAttributes().get("email"); // Adjust this based on your implementation
////
////    if (email != null) {
////        emailToSessionIdMap.put(email, session.getId());
////    }
////    sessions.put(session.getId(), session);
////    System.out.println("Connection established with session id: " + session.getId() + ", email: " + email);
////}
//
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String payload = message.getPayload();
//        System.out.println("Received message: " + payload);
//
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            Map<String, Object> data = objectMapper.readValue(payload, new TypeReference<Map<String, Object>>() {});
//
//            String type = (String) data.get("type");
//            System.out.println("Type: " + type);
//            switch (type) {
//                case "video:callRequest":
//                    handleVideoCallRequest(session, data);
//                    break;
//                case "candidate":
//                    handleCandidate(session, data);
//                    break;
//                case "incoming:answer":
//                    handleCallAccepted(session, data);
//                    break;
//                default:
//                    logger.warn("Unknown message type received: {}", type);
//            }
//        } catch (IOException e) {
//            logger.error("Error parsing incoming message: {}", e.getMessage());
//        }
//    }
//
////    private void handleVideoCallRequest(WebSocketSession session, Map<String, Object> data) throws IOException {
////        String callerId = (String) data.get("callerId");
////        String receiverId = (String) data.get("receiverId");
////        String receiverType = (String) data.get("receiverType");
////
////        System.out.println("Caller ID: " + callerId);
////        System.out.println("Receiver ID: " + receiverId);
////        System.out.println("Receiver Type: " + receiverType);
////
////        WebSocketSession receiverSession = null;
////
////        try {
////            if ("doctor".equals(receiverType)) {
////                Doctor doctor = doctorRepository.findByEmailDirect(receiverId);
////                if (doctor != null) {
////                    System.out.println("Doctor found: " + doctor.getEmail() + ", Session ID: " + doctor.getSessionId());
////                    receiverSession = sessions.get(doctor.getSessionId());
////                    if (receiverSession == null) {
////                        logger.error("Receiver session not found for doctor ID: {}", receiverId);
////                        return;
////                    }
////                } else {
////                    logger.error("Doctor not found with ID: {}", receiverId);
////                    return;
////                }
////            } else {
////                HealthOfficer healthOfficer = healthOfficerRepository.findByEmail(receiverId).orElse(null);
////                if (healthOfficer != null) {
////                    System.out.println("Health Officer found: " + healthOfficer.getEmail() + ", Session ID: " + healthOfficer.getSessionId());
////                    receiverSession = sessions.get(healthOfficer.getSessionId());
////                    if (receiverSession == null) {
////                        logger.error("Receiver session not found for health officer ID: {}", receiverId);
////                        return;
////                    }
////                } else {
////                    logger.error("HealthOfficer not found with ID: {}", receiverId);
////                    return;
////                }
////            }
////
////            System.out.println("Receiver session found: " + receiverSession.getId());
////
////            if (receiverSession.isOpen()) {
////                sendMessage(receiverSession, "video:incomingCall", Map.of("from", session.getId(), "callerId", callerId));
////            } else {
////                logger.warn("Receiver session is closed for ID: {}", receiverId);
////            }
////        } catch (Exception e) {
////            logger.error("Exception while handling video call request: {}", e.getMessage(), e);
////            throw e;
////        }
////    }
//private void handleVideoCallRequest(WebSocketSession session, Map<String, Object> data) throws IOException {
//    String callerId = (String) data.get("callerId");
//    String receiverId = (String) data.get("receiverId");
//
//    System.out.println("Caller ID: " + callerId);
//    System.out.println("Receiver ID: " + receiverId);
//
//    // Print sessions before processing candidate message
//    System.out.println("handle video call session");
//        printSessions();
//
//    WebSocketSession receiverSession = sessions.get(receiverId);
//
//    System.out.println("Reciver Session --------"+receiverSession);
//
//    if (receiverSession != null && receiverSession.isOpen()) {
//        sendMessage(receiverSession, "video:incomingCall", Map.of("from", session.getId(), "callerId", callerId));
//    } else {
//        logger.warn("Receiver session not found or closed for ID: {}", receiverId);
//    }
//}
//
//    private void handleCandidate(WebSocketSession session, Map<String, Object> data) throws IOException {
//        Map<String, Object> candidateData = (Map<String, Object>) data.get("payload");
//        String from = (String) data.get("from");
//
//
//        System.out.println("handle candidate ------------"+candidateData);
//        System.out.println("frome============"+from);
//
//
//        // Print sessions before processing candidate message
//        System.out.println("handle candiate session");
//        printSessions();
//
//
//        if (from == null) {
//            logger.error("Received candidate message with null 'from' key.");
//            return;
//        }
//
//        WebSocketSession receiverSession = sessions.get(from);
//
//        System.out.println("hansle session id ----------"+receiverSession);
//
//        if (receiverSession != null && receiverSession.isOpen()) {
//            sendMessage(receiverSession, "candidate", candidateData);  // Send candidateData directly
//        } else {
//            logger.warn("Receiver session not found or closed for ID: {}", from);
//        }
//    }
//
////    private void handleCandidate(WebSocketSession session, Map<String, Object> data) throws IOException {
////        Map<String, Object> candidateData = (Map<String, Object>) data.get("payload");
////        String from = (String) data.get("from");
////
////        if (from == null) {
////            logger.error("Received candidate message with null 'from' key.");
////            return;
////        }
////
////
////
////        // Print sessions before processing candidate message
////        printSessions();
////        // Retrieve HealthOfficer by email
////        HealthOfficer healthOfficer = healthOfficerRepository.findByEmail(from).orElse(null);
////
////        if (healthOfficer == null) {
////            logger.warn("HealthOfficer not found with email: {}", from);
////            return;
////        }
////
////        String sessionId = healthOfficer.getSessionId();
////        System.out.println("session Id-----------------"+sessionId);
////        WebSocketSession receiverSession = sessions.get(sessionId);
////
////        System.out.println("receiver session ---------------"+receiverSession);
////
////        if (receiverSession != null && receiverSession.isOpen()) {
////            sendMessage(receiverSession, "candidate", candidateData);
////        } else {
////            logger.warn("Receiver session not found or closed for ID: {}", from);
////        }
////    }
//
//
////private void handleCandidate(WebSocketSession session, Map<String, Object> data) throws IOException {
////    Map<String, Object> candidateData = (Map<String, Object>) data.get("payload");
////    String from = (String) data.get("from");
////
////    System.out.println("handle candidate ------------" + candidateData);
////    System.out.println("frome============" + from);
////
////    if (from == null) {
////        logger.error("Received candidate message with null 'from' key.");
////        return;
////    }
////
////    String sessionId = emailToSessionIdMap.get(from); // Retrieve session ID using email
////    if (sessionId == null) {
////        Doctor doctor = doctorRepository.findByEmailDirect(from);
////        HealthOfficer healthOfficer = healthOfficerRepository.findByEmail(from).orElse(null);
////
////        if (doctor != null) {
////            sessionId = doctor.getSessionId();
////        } else if (healthOfficer != null) {
////            sessionId = healthOfficer.getSessionId();
////        } else {
////            logger.warn("No user found with email: {}", from);
////            return;
////        }
////
////        // Cache the session ID in emailToSessionIdMap
////        emailToSessionIdMap.put(from, sessionId);
////    }
////
////    WebSocketSession receiverSession = sessions.get(sessionId);
////    System.out.println("handle session id ----------" + receiverSession);
////
////    if (receiverSession != null && receiverSession.isOpen()) {
////        sendMessage(receiverSession, "candidate", candidateData);  // Send candidateData directly
////    } else {
////        logger.warn("Receiver session not found or closed for ID: {}", from);
////    }
////}
//
////private void handleCandidate(WebSocketSession session, Map<String, Object> data) throws IOException {
////    Map<String, Object> candidateData = (Map<String, Object>) data.get("payload");
////    String from = (String) data.get("from");
////
////    System.out.println("handle candidate ------------" + candidateData);
////    System.out.println("frome============" + from);
////
////    if (from == null) {
////        logger.error("Received candidate message with null 'from' key.");
////        return;
////    }
////
////    String sessionId = emailToSessionIdMap.get(from); // Get session ID using email
////    WebSocketSession receiverSession = sessions.get(sessionId);
////
////    System.out.println("handle session id ----------" + receiverSession);
////
////    if (receiverSession != null && receiverSession.isOpen()) {
////        sendMessage(receiverSession, "candidate", candidateData);  // Send candidateData directly
////    } else {
////        logger.warn("Receiver session not found or closed for ID: {}", from);
////    }
////}
//
//
//    private void handleCallAccepted(WebSocketSession session, Map<String, Object> data) throws IOException {
//        String from = (String) data.get("from");
//        Map<String, Object> answerData = (Map<String, Object>) data.get("answer");
//
//        if (from == null) {
//            logger.error("Received answer message with null 'from' key.");
//            return;
//        }
//
//        WebSocketSession receiverSession = sessions.get(from);
//        if (receiverSession != null && receiverSession.isOpen()) {
//            sendMessage(receiverSession, "incoming:answer", Map.of("answer", answerData));
//        } else {
//            logger.warn("Receiver session not found or closed for ID: {}", from);
//        }
//    }
//
//    private void sendMessage(WebSocketSession session, String type, Map<String, Object> payload) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map<String, Object> message = Map.of("type", type, "payload", payload);
//        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
//    }
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        sessions.remove(session.getId());
//        logger.info("Connection closed with session id: {} and status: {}", session.getId(), status);
//    }
//}
//
//
//
//
//
//
//
//
//
//
////import com.fasterxml.jackson.databind.ObjectMapper;
////import org.slf4j.Logger;
////import org.slf4j.LoggerFactory;
////import org.springframework.stereotype.Component;
////import org.springframework.web.socket.CloseStatus;
////import org.springframework.web.socket.TextMessage;
////import org.springframework.web.socket.WebSocketSession;
////import org.springframework.web.socket.handler.TextWebSocketHandler;
////
////import java.io.IOException;
////import java.net.URI;
////import java.util.Map;
////import java.util.concurrent.ConcurrentHashMap;
////
////@Component
////public class SignalingHandler extends TextWebSocketHandler {
////
////    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
////    private static final Logger logger = LoggerFactory.getLogger(SignalingHandler.class);
////
////    @Override
////    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
////        sessions.put(session.getId(), session);
////        System.out.println("Connection established with session id: " + session.getId());
////    }
////
////
//////    @Override
//////    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//////        // Get the URI from the session
//////        URI uri = session.getUri();
//////        if (uri != null) {
//////            String queryString = uri.getQuery();
//////            if (queryString != null && !queryString.isBlank()) {
//////                String[] params = queryString.split("=");
//////                if (params.length >= 2) {
//////                    String userId = params[1];
//////                    sessions.put(userId, session);
//////                    System.out.println("Connection established with user id: " + userId + ", session id: " + session.getId());
//////                } else {
//////                    logger.warn("Invalid query parameter format in WebSocket URI: " + queryString);
//////                }
//////            } else {
//////                logger.warn("No query parameters found or empty in WebSocket URI: " + session.getId());
//////            }
//////        } else {
//////            logger.error("WebSocket session URI is null: " + session.getId());
//////        }
//////    }
////
//////    @Override
//////    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//////        // Assuming the user identifier is sent as a query parameter
//////        String userId = session.getUri().getQuery().split("=")[1];
//////        sessions.put(userId, session);
//////        System.out.println("Connection established with user id: " + userId + ", session id: " + session.getId());
//////    }
//
////    @Override
////    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
////        String payload = message.getPayload();
////        System.out.println("Received message: " + payload);
////
////        try {
////            ObjectMapper objectMapper = new ObjectMapper();
////            Map<String, Object> data = objectMapper.readValue(payload, Map.class);
////
////            String type = (String) data.get("type");
////            switch (type) {
////                case "video:callRequest":
////                    handleVideoCallRequest(session, data);
////                    break;
////                case "candidate":
////                    handleCandidate(session, data);
////                    break;
////                case "incoming:answer":
////                    handleCallAccepted(session, data);
////                    break;
////                default:
////                    logger.warn("Unknown message type received: {}", type);
////            }
////        } catch (IOException e) {
////            logger.error("Error parsing incoming message: {}", e.getMessage());
////        }
////    }
////
////    private void handleVideoCallRequest(WebSocketSession session, Map<String, Object> data) throws IOException {
////        String callerId = (String) data.get("callerId");
////        String receiverId = (String) data.get("receiverId");
////
////        System.out.println("Caller ID: " + callerId);
////        System.out.println("Receiver ID: " + receiverId);
////
////        WebSocketSession receiverSession = sessions.get(receiverId);
////
////        System.out.println("Reciver Session --------"+receiverSession);
////
////        if (receiverSession != null && receiverSession.isOpen()) {
////            sendMessage(receiverSession, "video:incomingCall", Map.of("from", session.getId(), "callerId", callerId));
////        } else {
////            logger.warn("Receiver session not found or closed for ID: {}", receiverId);
////        }
////    }
////
////    private void handleCandidate(WebSocketSession session, Map<String, Object> data) throws IOException {
////        String from = (String) data.get("from");
////        Map<String, Object> candidateData = (Map<String, Object>) data.get("candidate");
////
////        WebSocketSession receiverSession = sessions.get(from);
////        if (receiverSession != null && receiverSession.isOpen()) {
////            sendMessage(receiverSession, "candidate", Map.of("candidate", candidateData));
////        } else {
////            logger.warn("Receiver session not found or closed for ID: {}", from);
////        }
////    }
////
////    private void handleCallAccepted(WebSocketSession session, Map<String, Object> data) throws IOException {
////        String from = (String) data.get("from");
////        Map<String, Object> answerData = (Map<String, Object>) data.get("answer");
////
////        WebSocketSession receiverSession = sessions.get(from);
////        if (receiverSession != null && receiverSession.isOpen()) {
////            sendMessage(receiverSession, "incoming:answer", Map.of("answer", answerData));
////        } else {
////            logger.warn("Receiver session not found or closed for ID: {}", from);
////        }
////    }
////
////    private void sendMessage(WebSocketSession session, String type, Map<String, Object> payload) throws IOException {
////        ObjectMapper objectMapper = new ObjectMapper();
////        Map<String, Object> message = Map.of("type", type, "payload", payload);
////        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
////    }
////
////    @Override
////    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
////        sessions.entrySet().removeIf(entry -> entry.getValue().equals(session));
////        logger.info("Connection closed with session id: {} and status: {}", session.getId(), status);
////    }
////}
