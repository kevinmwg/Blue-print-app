package com.example.blueprintapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class activity_teacher_dashboard extends AppCompatActivity {

    private TextView textViewWelcomeTeacher;
    private Button buttonUpdatePerformanceTrack, buttonComposeSpecialMessages, buttonViewAssignments, buttonViewStudentPayments ,buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_dashboard);

        textViewWelcomeTeacher = findViewById(R.id.textViewWelcomeTeacher);
        buttonUpdatePerformanceTrack = findViewById(R.id.buttonUpdatePerformanceTrack);
        buttonComposeSpecialMessages = findViewById(R.id.buttonComposeSpecialMessages);
        buttonViewAssignments = findViewById(R.id.buttonViewAssignments);
        buttonViewStudentPayments = findViewById(R.id.buttonViewStudentPayments);
        buttonLogout = findViewById(R.id.buttonLogout);

        // Assuming the teacher's name is passed through the intent
        String teacherName = getIntent().getStringExtra("teacherName");
        textViewWelcomeTeacher.setText("Welcome, " + teacherName);

        // Set onClickListeners for buttons (replace with actual navigation logic)
        buttonUpdatePerformanceTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity_teacher_dashboard.this, update_performance_track.class);
                startActivity(intent);
            }
            // Navigate to Update Performance Track
        });

        buttonComposeSpecialMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity_teacher_dashboard.this, ComposeActivity.class);
                startActivity(intent);
            }
            // Navigate to Compose Special Messages
        });

        buttonViewAssignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity_teacher_dashboard.this, AssignmentsActivity.class);
                startActivity(intent);
            }

            // Navigate to View Assignments
        });

        buttonViewStudentPayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity_teacher_dashboard.this, Paymentupdates.class);
                startActivity(intent);
            }

            // Navigate to View Student Payments, Transaction Status, Payment %
        });
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle logout logic here, for example, clearing shared preferences and returning to login screen
                Intent intent = new Intent(activity_teacher_dashboard.this, StudentLoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // Finish current activity
            }
        });
    }
}