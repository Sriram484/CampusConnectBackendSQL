package com.example.campusconnectbackend.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequest {
    private String email;
    private String currentPassword;
    private String newPassword;
}
