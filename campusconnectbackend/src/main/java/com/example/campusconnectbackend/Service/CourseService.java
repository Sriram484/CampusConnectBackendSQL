package com.example.campusconnectbackend.Service;

import com.example.campusconnectbackend.Model.Category;
import com.example.campusconnectbackend.Model.CourseModel;
import com.example.campusconnectbackend.Model.Users;
import com.example.campusconnectbackend.Repository.CategoryRepository;
import com.example.campusconnectbackend.Repository.CourseRepository;
import com.example.campusconnectbackend.Repository.UserRepo;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JWTUtils jwtUtils;

    // Method to save a course and set the creator
    public CourseModel createCourse(CourseModel courseModel, String email) {

        System.out.println("DSVds");
        Optional<Users> creatorOptional = userRepo.findByEmail(email);

        if (creatorOptional.isPresent()) {
            Users creator = creatorOptional.get();
            courseModel.setCreatorId(creator.getId());

            Optional<Category> categoryOptional = categoryRepository.findByName(courseModel.getCategory());

            if (categoryOptional.isPresent()) {
               Category category = categoryOptional.get();
            courseModel.setCategoryId(category.getId());
    
            // Save the course
            CourseModel savedCourse = courseRepository.save(courseModel);
    
            // Update the existing category with the new course ID
            if (category.getCourseIds() == null) {
                category.setCourseIds(new HashSet<>());
            }
            category.getCourseIds().add(savedCourse.getId());
            categoryRepository.save(category);
                // Update the creator's createdCourses
                creator.getCreatedCourseIds().add(savedCourse.getId());
                userRepo.save(creator);
    
                return savedCourse;
            } else {
                throw new RuntimeException("Category not found");
            }
        } else {
            throw new RuntimeException("Creator not found");
        }
    }

    // Method to enroll a user in a course
    public void enrollUserInCourse(Long courseId, String email) {
        Optional<Users> userOptional = userRepo.findByEmail(email);
        Optional<CourseModel> courseOptional = courseRepository.findById(courseId);

        if (userOptional.isPresent() && courseOptional.isPresent()) {
            Users user = userOptional.get();
            CourseModel course = courseOptional.get();

            // Add user to the course's enrolled users
            course.getEnrolledUserIds().add(user.getId());
            courseRepository.save(course);

            // Add course to the user's enrolled courses
            user.getEnrolledCourseIds().add(courseId);
            userRepo.save(user);
        } else {
            throw new RuntimeException("User or Course not found");
        }
    }

    public CourseModel updateCourse(Long courseId, CourseModel updatedCourseModel)
            throws JsonProcessingException, IOException {
        CourseModel existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Merge the fields
        System.out.println("UpdateCourse");
        if (updatedCourseModel.getTitle() != null && !updatedCourseModel.getTitle().isEmpty()) {
            existingCourse.setTitle(updatedCourseModel.getTitle());
        }
        if (updatedCourseModel.getSubtitle() != null && !updatedCourseModel.getSubtitle().isEmpty()) {
            System.out.println("Subtitle in updated model: " + updatedCourseModel.getSubtitle());
            existingCourse.setSubtitle(updatedCourseModel.getSubtitle());
        }
        if (updatedCourseModel.getDescription() != null && !updatedCourseModel.getDescription().isEmpty()) {
            existingCourse.setDescription(updatedCourseModel.getDescription());
        }
        if (updatedCourseModel.getAudioLanguages() != null && !updatedCourseModel.getAudioLanguages().isEmpty()) {
            existingCourse.setAudioLanguages(updatedCourseModel.getAudioLanguages());
        }
        if (updatedCourseModel.getSubtitleLanguages() != null && !updatedCourseModel.getSubtitleLanguages().isEmpty()) {
            existingCourse.setSubtitleLanguages(updatedCourseModel.getSubtitleLanguages());
        }
        if (updatedCourseModel.getLevel() != null && !updatedCourseModel.getLevel().isEmpty()) {
            existingCourse.setLevel(updatedCourseModel.getLevel());
        }
        if (updatedCourseModel.getCategory() != null && !updatedCourseModel.getCategory().isEmpty()) {
            existingCourse.setCategory(updatedCourseModel.getCategory());
        }
        if (updatedCourseModel.getSubCategories() != null && !updatedCourseModel.getSubCategories().isEmpty()) {
            existingCourse.setSubCategories(updatedCourseModel.getSubCategories());
        }
        if (updatedCourseModel.getTopic() != null && !updatedCourseModel.getTopic().isEmpty()) {
            existingCourse.setTopic(updatedCourseModel.getTopic());
        }
        if (updatedCourseModel.getCourseImage() != null && !updatedCourseModel.getCourseImage().isEmpty()) {
            existingCourse.setCourseImage(updatedCourseModel.getCourseImage());
        }
        if (updatedCourseModel.getPromoVideo() != null && !updatedCourseModel.getPromoVideo().isEmpty()) {
            existingCourse.setPromoVideo(updatedCourseModel.getPromoVideo());
        }
        if (updatedCourseModel.getTotalHours() != null && !updatedCourseModel.getTotalHours().isEmpty()) {
            existingCourse.setTotalHours(updatedCourseModel.getTotalHours());
        }
        if (updatedCourseModel.getNumberOfExercises() != null && !updatedCourseModel.getNumberOfExercises().isEmpty()) {
            existingCourse.setNumberOfExercises(updatedCourseModel.getNumberOfExercises());
        }
        if (updatedCourseModel.getNumberOfArticles() != null && !updatedCourseModel.getNumberOfArticles().isEmpty()) {
            existingCourse.setNumberOfArticles(updatedCourseModel.getNumberOfArticles());
        }
        if (updatedCourseModel.getNumberOfResources() != null && !updatedCourseModel.getNumberOfResources().isEmpty()) {
            existingCourse.setNumberOfResources(updatedCourseModel.getNumberOfResources());
        }
        if (updatedCourseModel.getIncludesMobileAccess() != null) {
            existingCourse.setIncludesMobileAccess(updatedCourseModel.getIncludesMobileAccess());
        }
        if (updatedCourseModel.getFullLifetimeAccess() != null) {
            existingCourse.setFullLifetimeAccess(updatedCourseModel.getFullLifetimeAccess());
        }
        if (updatedCourseModel.getCertificateOfCompletion() != null) {
            existingCourse.setCertificateOfCompletion(updatedCourseModel.getCertificateOfCompletion());
        }
        if (updatedCourseModel.getWelcomeMessage() != null && !updatedCourseModel.getWelcomeMessage().isEmpty()) {
            existingCourse.setWelcomeMessage(updatedCourseModel.getWelcomeMessage());
        }
        if (updatedCourseModel.getCongratulationsMessage() != null
                && !updatedCourseModel.getCongratulationsMessage().isEmpty()) {
            existingCourse.setCongratulationsMessage(updatedCourseModel.getCongratulationsMessage());
        }
        if (updatedCourseModel.getCurrency() != null && !updatedCourseModel.getCurrency().isEmpty()) {
            existingCourse.setCurrency(updatedCourseModel.getCurrency());
        }
        if (updatedCourseModel.getPriceTier() != null && !updatedCourseModel.getPriceTier().isEmpty()) {
            existingCourse.setPriceTier(updatedCourseModel.getPriceTier());
        }
        if (updatedCourseModel.getBankName() != null && !updatedCourseModel.getBankName().isEmpty()) {
            existingCourse.setBankName(updatedCourseModel.getBankName());
        }
        if (updatedCourseModel.getAccountNumber() != null && !updatedCourseModel.getAccountNumber().isEmpty()) {
            existingCourse.setAccountNumber(updatedCourseModel.getAccountNumber());
        }
        if (updatedCourseModel.getIfscCode() != null && !updatedCourseModel.getIfscCode().isEmpty()) {
            existingCourse.setIfscCode(updatedCourseModel.getIfscCode());
        }
        if (updatedCourseModel.getLearningObjectives() != null
                && !updatedCourseModel.getLearningObjectives().isEmpty()) {
            existingCourse.setLearningObjectives(updatedCourseModel.getLearningObjectives());
        }
        if (updatedCourseModel.getRequirements() != null && !updatedCourseModel.getRequirements().isEmpty()) {
            existingCourse.setRequirements(updatedCourseModel.getRequirements());
        }
        if (updatedCourseModel.getIntendedLearners() != null && !updatedCourseModel.getIntendedLearners().isEmpty()) {
            existingCourse.setIntendedLearners(updatedCourseModel.getIntendedLearners());
        }
        if (updatedCourseModel.getSections() != null && !updatedCourseModel.getSections().isEmpty()) {
            existingCourse.setSections(updatedCourseModel.getSections());
        }

        // Save the updated course
        return courseRepository.save(existingCourse);
    }

    // Method to delete a course
    public void deleteCourse(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new RuntimeException("Course not found");
        }

        // Optional: Remove course ID from creator's createdCourseIds
        // Optional: Remove course ID from enrolled users' enrolledCourseIds

        courseRepository.deleteById(courseId);
    }

    public CourseModel getCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    public List<CourseModel> getAllCourses() {
        return courseRepository.findAll();
    }
}
