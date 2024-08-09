package com.example.campusconnectbackend.DTO;

import lombok.Data;

@Data
public class ReportDTO {
    private Integer reportedById;
    private String reason;
    private String review;
    private Integer reportedPersonId;
}