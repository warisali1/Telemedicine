//package com.telemedicicne.telemedicicne.videoCallingTsting.viaApi;
//
//import com.telemedicicne.telemedicicne.Entity.Doctor;
//import com.telemedicicne.telemedicicne.Entity.HealthOfficer;
//import com.telemedicicne.telemedicicne.Exception.ResourceNotFoundException;
//import com.telemedicicne.telemedicicne.Repository.DoctorRepository;
//import com.telemedicicne.telemedicicne.Repository.HealthOfficerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.socket.TextMessage;
//
//import java.io.IOException;
//
//@Service
//public class VideoCallService {
//
//    @Autowired
//    private HealthOfficerRepository healthOfficerRepository;
//
//    @Autowired
//    private DoctorRepository doctorRepository;
//
//    @Autowired
//    private WebSocketSessionManager webSocketSessionManager; // A custom class to manage WebSocket sessions
//
//    public String initiateCall(Long healthOfficerId, Long doctorId) throws IOException {
//        HealthOfficer healthOfficer = healthOfficerRepository.findById(healthOfficerId)
//                .orElseThrow(() -> new ResourceNotFoundException("Health Officer not found"));
//        Doctor doctor = doctorRepository.findById(doctorId)
//                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
//
//        // Generate a unique video session token using a fictional video service
//        String videoSessionToken = VideoServiceAPI.generateToken(); // Replace with actual API call
//
//        // Update the Health Officer and Doctor with the call token and status
//        healthOfficer.setCurrentCallToken(videoSessionToken);
//        healthOfficer.setCurrentCallStatus("ringing");
//        doctor.setCurrentCallToken(videoSessionToken);
//        doctor.setCurrentCallStatus("ringing");
//
//        healthOfficerRepository.save(healthOfficer);
//        doctorRepository.save(doctor);
//
//        // Notify the doctor about the incoming call via WebSocket
//        webSocketSessionManager.sendMessageToUser(doctor.getEmail(), String.valueOf(new TextMessage("Incoming call from Health Officer: " + healthOfficer.getName())));
//
//        return videoSessionToken;
//    }
//
//
//    public void acceptCall(String videoSessionToken) throws IOException {
//        // Logic to accept the call based on the token
//        Doctor doctor = doctorRepository.findByCurrentCallToken(videoSessionToken)
//                .orElseThrow(() -> new ResourceNotFoundException("No active call found for this token"));
//
//        // Implement your logic to handle the acceptance of the call here
//        // For demonstration purposes, we simply clear the token and status
//        doctor.setCurrentCallToken(null);
//        doctor.setCurrentCallStatus(null);
//
//        doctorRepository.save(doctor);
//
//        // Example: You could initiate the actual video call service here
//        // ExampleService.initiateVideoCall(videoSessionToken);
//    }
//}
