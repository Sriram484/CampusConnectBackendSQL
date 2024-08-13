package com.example.campusconnectbackend.Controller;

import com.example.campusconnectbackend.DTO.ResetPasswordRequest;
import com.example.campusconnectbackend.Model.Users;
import com.example.campusconnectbackend.Service.UsersManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adminuser")
public class PasswordResetController {

    @Autowired
    private UsersManagementService usersManagementService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            Users user = usersManagementService.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                return ResponseEntity.status(400).body("Current password is incorrect");
            }

            if (request.getNewPassword().length() < 6) {
                return ResponseEntity.status(400).body("New password must be at least 6 characters long");
            }

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            usersManagementService.save(user);

            return ResponseEntity.ok("Password successfully updated");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    
}
