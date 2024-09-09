package com.example.blueprintapp;

public class Paymentupdates {
    private String studentName;
    private String admissionNumber;
    private String className;
    private String amount;
    private String phoneNumber;

    // Default constructor required for calls to DataSnapshot.getValue(Payment.class)
    public Paymentupdates() {
    }

    public Paymentupdates(String studentName, String admissionNumber, String className, String amount, String phoneNumber) {
        this.studentName = studentName;
        this.admissionNumber = admissionNumber;
        this.className = className;
        this.amount = amount;
        this.phoneNumber = phoneNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getAdmissionNumber() {
        return admissionNumber;
    }

    public String getClassName() {
        return className;
    }

    public String getAmount() {
        return amount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
