package com.example.campusconnectbackend.Model;

import java.util.ArrayList;
import java.util.List;

public class Lecture {
    private String title;
    private List<Content> contents = new ArrayList<>();

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }
}
