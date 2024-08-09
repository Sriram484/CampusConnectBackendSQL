package com.example.campusconnectbackend.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.campusconnectbackend.Model.BlockedUser;
import com.example.campusconnectbackend.Model.Users;
import com.example.campusconnectbackend.Repository.BlockedRepo;
import com.example.campusconnectbackend.Repository.ReportedRepo;
import com.example.campusconnectbackend.Repository.UserRepo;

@Service
public class BlockedUserService {

    @Autowired
    private BlockedRepo blockedRepo;

    @Autowired
    private ReportedRepo reportedRepo;

    @Autowired
    private UserRepo usersRepo;

    @Transactional
    public void blockUser(Integer blockedPersonId) {
        // Retrieve the blocked user
        Users blockedPerson = usersRepo.findById(blockedPersonId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Create and save BlockedUser entity
        BlockedUser blockedUser = new BlockedUser();
        blockedUser.setBlockedPerson(blockedPerson);
        blockedUser.setName(blockedPerson.getName());
        blockedUser.setEmail(blockedPerson.getEmail());
        LocalDate currentDate = LocalDate.now();
        String dateString = currentDate.toString();
        blockedUser.setBlockedOn(dateString);
        blockedRepo.save(blockedUser);

        // Update the blockedUsers collection
    
        Optional<Users> optionalCurrentUser = usersRepo.findById(blockedPersonId);
        // Clear reported users if necessary
        reportedRepo.deleteAllByReportedPersonId(blockedPersonId);

        if (optionalCurrentUser.isPresent()) {
            Users currentUser = optionalCurrentUser.get();
        
            // Initialize blockedUsers collection if null
            if (currentUser.getBlockedUsers() == null) {
                currentUser.setBlockedUsers(new HashSet<>());
            }
            System.out.print("##");
        
            // Add the blocked user to the collection
            currentUser.getBlockedUsers().add(blockedUser);
        
            // Save the updated user
            usersRepo.save(currentUser);
        } else {
            // Handle the case where the user is not found
            // Example: log an error or throw an exception
            System.err.println("User not found with ID: " + blockedPersonId);
        }

    }

    public List<BlockedUser> getAllBlockedUsers() {
        return blockedRepo.findAll();
    }
}
