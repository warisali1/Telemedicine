//package com.telemedicicne.telemedicicne.videoCallingTsting.viaApi;
//
//import com.telemedicicne.telemedicicne.Exception.ResourceNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//
//@RestController
//@RequestMapping("/api/video")
//public class VideoCallController {
//
//    @Autowired
//    private VideoCallService videoCallService;
//
//    @PostMapping("/initiate")
//    public ResponseEntity<String> initiateCall(@RequestParam Long healthOfficerId, @RequestParam Long doctorId) throws IOException {
//        String token = videoCallService.initiateCall(healthOfficerId, doctorId);
//        return ResponseEntity.ok("Call initiated successfully with token: " + token);
//    }
//
//    @PostMapping("/accept")
//    public ResponseEntity<String> acceptCall(@RequestParam Long doctorId) {
//        try {
//            videoCallService.acceptCall(doctorId);
//            return ResponseEntity.ok("Call accepted successfully");
//        } catch (IOException e) {
//            return ResponseEntity.status(500).body("Error accepting call: " + e.getMessage());
//        } catch (ResourceNotFoundException e) {
//            return ResponseEntity.status(404).body(e.getMessage());
//        }
//    }
//}
