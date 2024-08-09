package com.example.campusconnectbackend.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.campusconnectbackend.Model.ReportedUser;
import com.example.campusconnectbackend.Model.Users;
import com.example.campusconnectbackend.Repository.ReportedRepo;
import com.example.campusconnectbackend.Repository.UserRepo;

import jakarta.transaction.Transactional;

@Service
public class ReportedUserService {
    @Autowired
    private ReportedRepo reportedRepo;

    @Autowired
    private UserRepo usersRepo;


    @Transactional
    public void reportUser(Integer reportedById, Integer reportedPersonId, String reason, String review) {
        Users reportedBy = usersRepo.findById(reportedById).orElseThrow(() -> new RuntimeException("Reporting user not found"));
        Users reportedPerson = usersRepo.findById(reportedPersonId).orElseThrow(() -> new RuntimeException("Reported user not found"));

        ReportedUser reportedUser = new ReportedUser();
        reportedUser.setReportedBy(reportedBy.getEmail()); // Assuming you want to store the email of the reporting user
        reportedUser.setReportedPerson(reportedPerson);
        reportedUser.setReportedReason(reason);
        if (review.length() > 1024) {
            review = review.substring(0, 1024);
        }
        reportedUser.setReportedReview(review);
        reportedUser.setReportedUserId(reportedPersonId);
         LocalDate currentDate = LocalDate.now();
        String dateString = currentDate.toString();
        reportedUser.setReportedOn(dateString);
        Optional<Users> reportedPersonObj = usersRepo.findById(reportedPersonId);

        if (reportedPersonObj.isPresent()) {
            // If the Optional contains a Users object, retrieve it
            Users user = reportedPersonObj.get();
            // Use the user's name to set the reported person's name
            reportedUser.setReportedPersonName(user.getName());
        } else {
            // Handle the case where the user is not found (though unlikely since we checked earlier)
            throw new RuntimeException("Reported user not found when setting name");
        }
        reportedRepo.save(reportedUser);
    }

    @Transactional
    public void removeReportedUser(Integer reportedPersonId) {
        reportedRepo.deleteAllByReportedPersonId(reportedPersonId);
    }

    public List<ReportedUser> getAllReportedUsers() {
        return reportedRepo.findAll();
    }

}
