package com.example.campusconnectbackend.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "reported_users")
@Getter
@Setter
@NoArgsConstructor
public class ReportedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportedId;

    private String reportedBy;

    @ManyToOne
    @JoinColumn(name = "reportedPersonId", referencedColumnName = "id")
    @JsonBackReference
    private Users reportedPerson;
    
    private Integer reportedUserId;
    private String reportedPersonName;
    private String reportedReview;
    private String reportedReason;
    private String reportedOn;


}
