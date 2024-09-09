package com.example.blueprintapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class update_performance_track extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextGrades;
    private EditText editTextAttendance;
    private Button buttonSave;

    private Button button_addAnotherStudent;

    private DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_performance_track);

        editTextName = findViewById(R.id.edit_text_name);
        editTextGrades = findViewById(R.id.edit_text_grades);
        editTextAttendance = findViewById(R.id.edit_text_attendance);
        buttonSave = findViewById(R.id.button_save);
        button_addAnotherStudent = findViewById(R.id.button_addAnotherStudent);
        button_addAnotherStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(update_performance_track.this, update_performance_track.class);
                startActivity(intent);
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("performance");

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStudent();
            }
        });
    }

    private void saveStudent() {
        String name = editTextName.getText().toString().trim();
        String gradesStr = editTextGrades.getText().toString().trim();
        String attendanceStr = editTextAttendance.getText().toString().trim();

        if (name.isEmpty() || gradesStr.isEmpty() || attendanceStr.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        float grades;
        int attendance;
        try {
            grades = Float.parseFloat(gradesStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input for grades", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            attendance = Integer.parseInt(attendanceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input for attendance", Toast.LENGTH_SHORT).show();
            return;
        }

        StudentPerformance student = new StudentPerformance(name, grades, attendance);
        databaseReference.push().setValue(student)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(update_performance_track.this, "Student added", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(update_performance_track.this, "Error adding student", Toast.LENGTH_SHORT).show());
    }

}
