package com.example.blueprintapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioTeacher, radioStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radioGroup);
        radioTeacher = findViewById(R.id.radioTeacher);
        radioStudent = findViewById(R.id.radioStudent);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == radioTeacher.getId()) {
                    navigateToTeacherLogin();
                } else if (checkedId == radioStudent.getId()) {
                    navigateToStudentLogin();
                } else {
                    Toast.makeText(MainActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void navigateToTeacherLogin() {
        Intent intent = new Intent(MainActivity.this, TeacherLoginActivity.class);
        startActivity(intent);
    }

    private void navigateToStudentLogin() {
        Intent intent = new Intent(MainActivity.this, StudentLoginActivity.class);
        startActivity(intent);
    }
}
