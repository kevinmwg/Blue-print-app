package com.example.blueprintapp;


public class Notification {

    private String id;
    private String title;
    private String message;

    // Default constructor required for calls to DataSnapshot.getValue(Notification.class)
    public Notification() {
    }

    public Notification(String id, String title, String message) {
        this.id = id;
        this.title = title;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

