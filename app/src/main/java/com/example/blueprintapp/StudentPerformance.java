package com.example.blueprintapp;



public class StudentPerformance {
    private String name;
    private float grade;
    private int attendance;

    public StudentPerformance() { }

    public StudentPerformance(String name, float grade, int attendance) {
        this.name = name;
        this.grade = grade;
        this.attendance = attendance;
    }

    public String getName() {
        return name;
    }

    public float getGrade() {
        return grade;
    }

    public int getAttendance() {
        return attendance;
    }
}
