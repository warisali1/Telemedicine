//package com.telemedicicne.telemedicicne.Service;
//
//
//import com.telemedicicne.telemedicicne.Repository.HealthOfficerRepository;
//import com.telemedicicne.telemedicicne.Entity.HealthOfficer;
//import com.telemedicicne.telemedicicne.Entity.User;
//import com.telemedicicne.telemedicicne.Repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private HealthOfficerRepository docHSRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Optional<User> userOptional = userRepository.findByEmail(email);
//        if (userOptional.isPresent()) {
//            return userOptional.get();
//        }
//
//        Optional<HealthOfficer> docHsOptional = docHSRepository.findByEmail(email);
//        if (docHsOptional.isPresent()) {
//            return docHsOptional.get();
//        }
//
//        throw new UsernameNotFoundException("User not found with email: " + email);
//    }
//}
