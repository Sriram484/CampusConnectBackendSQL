package com.example.campusconnectbackend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.campusconnectbackend.Model.ReportedUser;


public interface ReportedRepo extends JpaRepository<ReportedUser,Integer>{
    void deleteAllByReportedPersonId(Integer reportedPersonId);
} 