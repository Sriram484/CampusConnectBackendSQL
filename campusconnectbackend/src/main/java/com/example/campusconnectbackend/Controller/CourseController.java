package com.example.campusconnectbackend.Controller;

import com.example.campusconnectbackend.Model.CourseModel;
import com.example.campusconnectbackend.Model.Users;
import com.example.campusconnectbackend.Repository.CourseRepository;
import com.example.campusconnectbackend.Service.CourseService;
import com.example.campusconnectbackend.Service.UsersManagementService;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UsersManagementService usersManagementService;

    @PostMapping("/adminuser/courses/createCourse")
    public ResponseEntity<CourseModel> createCourse(@RequestBody CourseModel courseModel, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String email = userDetails.getUsername();
        System.out.println("User email: " + email); // For debugging

        try {
            CourseModel savedCourse = courseService.createCourse(courseModel, email);
            return new ResponseEntity<>(savedCourse, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Handle exceptions
            e.printStackTrace(); // Print stack trace for debugging
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/adminuser/courses/{courseId}/enroll")
    public ResponseEntity<String> enrollInCourse(@PathVariable Long courseId, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        String email = userDetails.getUsername();

        try {
            courseService.enrollUserInCourse(courseId, email);
            return ResponseEntity.ok("User enrolled successfully");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Enrollment failed");
        }
    }

    @PutMapping("/adminuser/courses/updateCourse/{id}")
public ResponseEntity<CourseModel> updateCourse(@PathVariable("id") Long courseId, @RequestBody CourseModel courseModel) throws JsonProcessingException, IOException {
    try {
        CourseModel updatedCourse = courseService.updateCourse(courseId, courseModel);
        return ResponseEntity.ok(updatedCourse);
    } catch (RuntimeException e) {
        e.printStackTrace(); // Print stack trace for debugging
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
    
    @DeleteMapping("/adminuser/courses/deleteCourse/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("id") Long courseId) {
        try {
            courseService.deleteCourse(courseId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            e.printStackTrace(); // Print stack trace for debugging
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/public/courses/{id}")
    public ResponseEntity<CourseModel> getCourseById(@PathVariable("id") Long courseId) {
        try {
            CourseModel course = courseService.getCourseById(courseId);
            return ResponseEntity.ok(course);
        } catch (RuntimeException e) {
            e.printStackTrace(); // Print stack trace for debugging
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/public/courses/all")
    public ResponseEntity<List<CourseModel>> getAllCourses() {
        try {
            List<CourseModel> courses = courseService.getAllCourses();
            return ResponseEntity.ok(courses);
        } catch (RuntimeException e) {
            e.printStackTrace(); // Print stack trace for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
