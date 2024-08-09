package com.example.campusconnectbackend.Controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.campusconnectbackend.DTO.ReportDTO;
import com.example.campusconnectbackend.Model.ReportedUser;
import com.example.campusconnectbackend.Service.ReportedUserService;

@RestController

public class ReportedController {
    @Autowired
    private ReportedUserService reportedService;

    @PutMapping("/adminuser/api/reports/{reportedPersonId}")
    public void reportUser(@PathVariable Integer reportedPersonId, @RequestBody ReportDTO reportDTO) {
        reportedService.reportUser(reportDTO.getReportedById(), reportedPersonId, reportDTO.getReason(), reportDTO.getReview());
    }

    @DeleteMapping("/admin/api/reports/{reportedPersonId}")
    public void removeReportedUser(@PathVariable Integer reportedPersonId) {
        reportedService.removeReportedUser(reportedPersonId);
    }

    @GetMapping("/admin/api/reports/get")
    public List<ReportedUser> getAllReportedUsers() {
        return reportedService.getAllReportedUsers();
    }
}