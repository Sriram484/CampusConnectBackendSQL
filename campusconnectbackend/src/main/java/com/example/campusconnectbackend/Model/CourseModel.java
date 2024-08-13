package com.example.campusconnectbackend.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "courses")
public class CourseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic Information
    @Column(length = 35)
    private String title = "";

    @Column(length = 40)
    private String subtitle = "";

    @Lob
    @Column(length = 1000)
    private String description = "";

    // Course Details
    @ElementCollection
    @CollectionTable(name = "audio_languages", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "language")
    private List<String> audioLanguages = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "subtitle_languages", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "language")
    private List<String> subtitleLanguages = new ArrayList<>();

    @Column(length = 20)
    private String level = "";

    @Column(length = 100)
    private String category = "";

    @ElementCollection
    @CollectionTable(name = "subcategories", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "subcategory")
    private List<String> subCategories = new ArrayList<>();

    @Column(length = 255)
    private String topic = "";

    // Media
    private String courseImage = "";
    private String promoVideo = "";

    // Additional Information
    @Column(length = 10)
    private String totalHours = "";

    @Column(length = 10)
    private String numberOfExercises = "";

    @Column(length = 10)
    private String numberOfArticles = "";

    @Column(length = 10)
    private String numberOfResources = "";

  
    @Column
    private Boolean includesMobileAccess; // Use Boolean wrapper class

    @Column
    private Boolean fullLifetimeAccess; // Use Boolean wrapper class

    @Column
    private Boolean certificateOfCompletion; // Use Boolean wrapper class
    
    // Intended Learners
    @Lob
    @Column(name = "learning_objectives")
    private String learningObjectivesJson = "";

    @Lob
    @Column(name = "requirements")
    private String requirementsJson = "";

    @Lob
    @Column(name = "intended_learners")
    private String intendedLearnersJson = "";

    // Sections (stored as JSON)
    @Lob
    @Column(name = "sections", columnDefinition = "LONGTEXT")
    private String sectionsJson = "";

    // Course Messages
    @Column(length = 1000)
    private String welcomeMessage = "";

    @Column(length = 1000)
    private String congratulationsMessage = "";

    // Pricing Information
    @Column(length = 10)
    private String currency = "";

    @Column(length = 10)
    private String priceTier = "";

    @Column(length = 100)
    private String bankName = "";

    @Column(length = 50)
    private String accountNumber = "";

    @Column(length = 15)
    private String ifscCode = "";

    // Relationship with User
    @Column(name = "creator_id")
    private Integer creatorId;

    @ElementCollection
    @CollectionTable(name = "enrolled_users", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "user_id")
    private Set<Integer> enrolledUserIds = new HashSet<>();


    //RelationShpi with category
    
    @Column(name = "category_id")
    private Long categoryId;

    // Helper methods to handle JSON conversion
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public List<String> getLearningObjectives() throws IOException {
        if (learningObjectivesJson == null || learningObjectivesJson.isEmpty()) {
            return new ArrayList<>();
        }
        return objectMapper.readValue(learningObjectivesJson,
                objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
    }

    public void setLearningObjectives(List<String> learningObjectives) throws JsonProcessingException {
        this.learningObjectivesJson = objectMapper.writeValueAsString(learningObjectives);
    }

    public List<String> getRequirements() throws IOException {
        if (requirementsJson == null || requirementsJson.isEmpty()) {
            return new ArrayList<>();
        }
        return objectMapper.readValue(requirementsJson,
                objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
    }

    public void setRequirements(List<String> requirements) throws JsonProcessingException {
        this.requirementsJson = objectMapper.writeValueAsString(requirements);
    }

    public List<String> getIntendedLearners() throws IOException {
        if (intendedLearnersJson == null || intendedLearnersJson.isEmpty()) {
            return new ArrayList<>();
        }
        return objectMapper.readValue(intendedLearnersJson,
                objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
    }

    public void setIntendedLearners(List<String> intendedLearners) throws JsonProcessingException {
        this.intendedLearnersJson = objectMapper.writeValueAsString(intendedLearners);
    }

    public List<Section> getSections() throws IOException {
        if (sectionsJson == null || sectionsJson.isEmpty()) {
            return new ArrayList<>();
        }
        return objectMapper.readValue(sectionsJson,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Section.class));
    }

    public void setSections(List<Section> sections) throws JsonProcessingException {
        this.sectionsJson = objectMapper.writeValueAsString(sections);
    }

    // Getters and Setters for all fields...
}
