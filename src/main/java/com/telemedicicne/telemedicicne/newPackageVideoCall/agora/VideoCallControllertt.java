//package com.telemedicicne.telemedicicne.newPackageVideoCall.agora;
//
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.sound.midi.Track;
//
//@RestController
//@RequestMapping("/api")
//public class VideoCallController {
//    @PostMapping("/join-channel")
//    public String joinChannel(@RequestBody String channelName) {
//        // Initialize Agora client
//        AgoraRTC client = new AgoraRTC();
//        client.init("Your App ID", () -> System.out.println("AgoraRTC client initialized"), (err) -> System.out.println("Error initializing AgoraRTC client: " + err));
//
//        // Join channel
//        client.join(null, channelName, null, (uid) -> {
//            // Publish local video and audio tracks
//            LocalAudioTrack localAudioTrack = client.createLocalAudioTrack();
//            LocalVideoTrack localVideoTrack = client.createLocalVideoTrack();
//            client.publish(new Track[]{localAudioTrack, localVideoTrack});
//        }, (err) -> System.out.println("Error joining channel: " + err));
//
//        return "Joined channel successfully";
//    }
//}