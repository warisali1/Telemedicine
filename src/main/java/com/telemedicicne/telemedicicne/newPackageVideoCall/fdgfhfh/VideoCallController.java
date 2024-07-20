//package com.telemedicicne.telemedicicne.newPackageVideoCall.fdgfhfh;
//
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class VideoCallController {
//
//    @MessageMapping("/video.offer")
//    @SendTo("/topic/video.offer")
//    public String offer(@Payload String offer) {
//        return offer;
//    }
//
//    @MessageMapping("/video.answer")
//    @SendTo("/topic/video.answer")
//    public String answer(@Payload String answer) {
//        return answer;
//    }
//
//    @MessageMapping("/video.ice")
//    @SendTo("/topic/video.ice")
//    public String iceCandidate(@Payload String iceCandidate) {
//        return iceCandidate;
//    }
//}
