package com.example.blueprintapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

;

public class registration_form extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST_TEACHER = 1;
    private static final int PICK_IMAGE_REQUEST_STUDENT = 2;

    private EditText etTeacherFullName, etTeacherEmployeeID, etTeacherPhoneNumber, etTeacherEmail, etTeacherPassword, etTeacherConfirmPassword;
    private EditText etStudentFullName, etStudentAdmissionNumber, etStudentPhoneNumber, etStudentEmail, etStudentPassword, etStudentConfirmPassword;
    private Button btnTeacherRegister, btnStudentRegister, btnUploadTeacherProfilePicture, btnUploadStudentProfilePicture;
    private ImageView ivTeacherProfilePicture, ivStudentProfilePicture;
    private Uri teacherImageUri, studentImageUri;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Teacher Registration Views
        etTeacherFullName = findViewById(R.id.etTeacherFullName);
        etTeacherEmployeeID = findViewById(R.id.etTeacherEmployeeID);
        etTeacherPhoneNumber = findViewById(R.id.etTeacherPhoneNumber);
        etTeacherEmail = findViewById(R.id.etTeacherEmail);
        etTeacherPassword = findViewById(R.id.etTeacherPassword);
        etTeacherConfirmPassword = findViewById(R.id.etTeacherConfirmPassword);
        btnTeacherRegister = findViewById(R.id.btnTeacherRegister);
        ivTeacherProfilePicture = findViewById(R.id.ivTeacherProfilePicture);
        btnUploadTeacherProfilePicture = findViewById(R.id.btnUploadTeacherProfilePicture);

        // Student Registration Views
        etStudentFullName = findViewById(R.id.etStudentFullName);
        etStudentAdmissionNumber = findViewById(R.id.etStudentAdmissionNumber);
        etStudentPhoneNumber = findViewById(R.id.etStudentPhoneNumber);
        etStudentEmail = findViewById(R.id.etStudentEmail);
        etStudentPassword = findViewById(R.id.etStudentPassword);
        etStudentConfirmPassword = findViewById(R.id.etStudentConfirmPassword);
        btnStudentRegister = findViewById(R.id.btnStudentRegister);
        ivStudentProfilePicture = findViewById(R.id.ivStudentProfilePicture);
        btnUploadStudentProfilePicture = findViewById(R.id.btnUploadStudentProfilePicture);

        btnUploadTeacherProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser(PICK_IMAGE_REQUEST_TEACHER);
            }
        });

        btnUploadStudentProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser(PICK_IMAGE_REQUEST_STUDENT);
            }
        });

        btnTeacherRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(true);
            }
        });

        btnStudentRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(false);
            }
        });
    }

    private void openFileChooser(int requestCode) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (requestCode == PICK_IMAGE_REQUEST_TEACHER) {
                teacherImageUri = data.getData();
                ivTeacherProfilePicture.setImageURI(teacherImageUri);
            } else if (requestCode == PICK_IMAGE_REQUEST_STUDENT) {
                studentImageUri = data.getData();
                ivStudentProfilePicture.setImageURI(studentImageUri);
            }
        }
    }

    private void registerUser(final boolean isTeacher) {
        String fullName, id, phoneNumber, email, password, confirmPassword;
        Uri profilePictureUri;

        if (isTeacher) {
            fullName = etTeacherFullName.getText().toString().trim();
            id = etTeacherEmployeeID.getText().toString().trim();
            phoneNumber = etTeacherPhoneNumber.getText().toString().trim();
            email = etTeacherEmail.getText().toString().trim();
            password = etTeacherPassword.getText().toString().trim();
            confirmPassword = etTeacherConfirmPassword.getText().toString().trim();
            profilePictureUri = teacherImageUri;
        } else {
            fullName = etStudentFullName.getText().toString().trim();
            id = etStudentAdmissionNumber.getText().toString().trim();
            phoneNumber = etStudentPhoneNumber.getText().toString().trim();
            email = etStudentEmail.getText().toString().trim();
            password = etStudentPassword.getText().toString().trim();
            confirmPassword = etStudentConfirmPassword.getText().toString().trim();
            profilePictureUri = studentImageUri;
        }

        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(id) || TextUtils.isEmpty(phoneNumber) ||
                TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) ||
                profilePictureUri == null) {
            Toast.makeText(this, "Please fill all fields and upload a profile picture", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userId = mAuth.getCurrentUser().getUid();
                            Map<String, Object> user = new HashMap<>();
                            user.put("fullName", fullName);
                            user.put("id", id);
                            user.put("phoneNumber", phoneNumber);
                            user.put("email", email);
                            user.put("profilePictureUri", profilePictureUri.toString());

                            if (isTeacher) {
                                db.collection("teachers").document(userId).set(user)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(registration_form.this, "Teacher registered successfully", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(registration_form.this, "Failed to register teacher", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                db.collection("students").document(userId).set(user)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(registration_form.this, "Student registered successfully", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(registration_form.this, "Failed to register student", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        } else {
                            Toast.makeText(registration_form.this, "Failed to register user", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}


