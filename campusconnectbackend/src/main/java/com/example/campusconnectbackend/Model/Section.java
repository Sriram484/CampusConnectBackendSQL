package com.example.campusconnectbackend.Model;

import java.util.ArrayList;
import java.util.List;

public class Section {
    private String title;
    private List<Lecture> lectures = new ArrayList<>();

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }
}
