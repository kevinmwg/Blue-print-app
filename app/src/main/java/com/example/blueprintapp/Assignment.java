package com.example.blueprintapp;

public class Assignment {
    private String fullName;
    private String studentId;
    private String studentClass;
    private String subject;
    private String assignmentTitle;
    private String dateOfSubmission;
    private String dueDate;
    private String emailAddress;
    private String phoneNumber;
    private String briefDescription;
    private String notes;
    private String pageNumbers;
    private String imageUrl;

    public Assignment() {
        // Default constructor required for calls to DataSnapshot.getValue(Assignment.class)
    }

    public Assignment(String fullName, String studentId, String studentClass, String subject, String assignmentTitle, String dateOfSubmission, String dueDate, String emailAddress, String phoneNumber, String briefDescription, String notes, String pageNumbers, String imageUrl) {
        this.fullName = fullName;
        this.studentId = studentId;
        this.studentClass = studentClass;
        this.subject = subject;
        this.assignmentTitle = assignmentTitle;
        this.dateOfSubmission = dateOfSubmission;
        this.dueDate = dueDate;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.briefDescription = briefDescription;
        this.notes = notes;
        this.pageNumbers = pageNumbers;
        this.imageUrl = imageUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public void setAssignmentTitle(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    public String getDateOfSubmission() {
        return dateOfSubmission;
    }

    public void setDateOfSubmission(String dateOfSubmission) {
        this.dateOfSubmission = dateOfSubmission;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription) {
        this.briefDescription = briefDescription;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPageNumbers() {
        return pageNumbers;
    }

    public void setPageNumbers(String pageNumbers) {
        this.pageNumbers = pageNumbers;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
