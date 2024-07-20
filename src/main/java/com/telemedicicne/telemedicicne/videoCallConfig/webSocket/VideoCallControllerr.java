//package com.telemedicicne.telemedicicne.videoCallConfig.webSocket;
//
//import com.telemedicicne.telemedicicne.newVideoCall.CallRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/video")
//public class VideoCallController {
//
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
//
//    @MessageMapping("/call")
//    public void initiateCall(@Payload CallRequest callRequest) {
//        // Logic to process call request, fetch Doctor details, etc.
//        // Send signaling message to Doctor's client
//        messagingTemplate.convertAndSendToUser(callRequest.getDoctorUsername(), "/queue/call", callRequest);
//    }
//}
