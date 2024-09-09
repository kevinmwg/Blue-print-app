package com.example.blueprintapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class student_dashboard extends AppCompatActivity {

    private TextView textViewWelcomeStudent;
    private Button buttonAcademics, buttonUpdates, buttonMakePayments, buttonPerformanceTrack, buttonLogout;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        textViewWelcomeStudent = findViewById(R.id.textViewWelcomeStudent);
        buttonAcademics = findViewById(R.id.buttonAcademics);
        buttonUpdates = findViewById(R.id.buttonUpdates);
        buttonMakePayments = findViewById(R.id.buttonMakePayments);
        buttonPerformanceTrack = findViewById(R.id.buttonPerformanceTrack);
        buttonLogout = findViewById(R.id.buttonLogout);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Fetch the user's name from Firestore
        fetchStudentName();

        // Set onClickListeners for buttons
        buttonAcademics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(student_dashboard.this, Academics.class);
                startActivity(intent);
            }
        });

        buttonUpdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(student_dashboard.this, updates.class);
                startActivity(intent);
            }
        });

        buttonMakePayments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(student_dashboard.this, Payments.class);
                startActivity(intent);
            }
        });

        buttonPerformanceTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(student_dashboard.this, PerformanceTrack.class);
                startActivity(intent);
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle logout logic here, for example, clearing shared preferences and returning to login screen
                Intent intent = new Intent(student_dashboard.this, StudentLoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish(); // Finish current activity
            }
        });
    }

    private void fetchStudentName() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Reference to the user's document in the "users" collection
            DocumentReference userRef = db.collection("users").document(userId);

            userRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String fullName = document.getString("fullName");
                        if (fullName != null) {
                            textViewWelcomeStudent.setText("Welcome, " + fullName);
                        } else {
                            textViewWelcomeStudent.setText("Welcome, Student");
                        }
                    } else {
                        textViewWelcomeStudent.setText("Welcome, User");
                        Toast.makeText(student_dashboard.this, "User data not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    textViewWelcomeStudent.setText("Welcome, User");
                    Toast.makeText(student_dashboard.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            textViewWelcomeStudent.setText("Welcome, User");
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }
}
