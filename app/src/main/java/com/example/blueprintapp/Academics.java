package com.example.blueprintapp;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Academics extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_SELECT = 2;

    private ImageView imageView;
    private Uri photoUri;
    private CollectionReference assignmentsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academics);

        imageView = findViewById(R.id.img_display);
        assignmentsRef = FirebaseFirestore.getInstance().collection("assignments");

        ImageButton btnSelectImage = findViewById(R.id.btn_select_image);
        ImageButton btnCaptureImage = findViewById(R.id.btn_capture_image);
        ImageButton btnUploadImage = findViewById(R.id.btn_upload_image);

        btnSelectImage.setOnClickListener(v -> selectImage());
        btnCaptureImage.setOnClickListener(v -> captureImage());
        btnUploadImage.setOnClickListener(v -> showAssignmentSubmissionForm());

        // Check and request permissions
        if (!hasPermissions()) {
            requestPermissions();
        }
    }

    private boolean hasPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.READ_MEDIA_IMAGES,
                        Manifest.permission.CAMERA
                }, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            boolean allGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }
            if (!allGranted) {
                Toast.makeText(this, "Permissions are required for this app", Toast.LENGTH_SHORT).show();
                openAppSettings();
            } else {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openAppSettings() {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_SELECT);
    }

    private void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Handle error
            }
            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imageView.setImageURI(photoUri);
        } else if (requestCode == REQUEST_IMAGE_SELECT && resultCode == RESULT_OK && data != null) {
            photoUri = data.getData();
            imageView.setImageURI(photoUri);
        }
    }

    private void showAssignmentSubmissionForm() {
        // Inflate the custom layout/view
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.dialog_assignment_submission, null);

        // Build the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setTitle("Assignment Submission");

        // Set up the form fields
        EditText editTextFullName = view.findViewById(R.id.editTextFullName);
        EditText editTextStudentId = view.findViewById(R.id.editTextStudentId);
        EditText editTextClass = view.findViewById(R.id.editTextClass);
        EditText editTextSubject = view.findViewById(R.id.editTextSubject);
        EditText editTextAssignmentTitle = view.findViewById(R.id.editTextAssignmentTitle);
        EditText editTextDateOfSubmission = view.findViewById(R.id.editTextDateOfSubmission);
        EditText editTextDueDate = view.findViewById(R.id.editTextDueDate);
        EditText editTextEmailAddress = view.findViewById(R.id.editTextEmailAddress);
        EditText editTextPhoneNumber = view.findViewById(R.id.editTextPhoneNumber);
        EditText editTextBriefDescription = view.findViewById(R.id.editTextBriefDescription);
        EditText editTextNotes = view.findViewById(R.id.editTextNotes);
        EditText editTextPageNumbers = view.findViewById(R.id.editTextPageNumbers);

        builder.setPositiveButton("Submit", (dialog, which) -> {
            // Get the values from the form fields
            String fullName = editTextFullName.getText().toString().trim();
            String studentId = editTextStudentId.getText().toString().trim();
            String studentClass = editTextClass.getText().toString().trim();
            String subject = editTextSubject.getText().toString().trim();
            String assignmentTitle = editTextAssignmentTitle.getText().toString().trim();
            String dateOfSubmission = editTextDateOfSubmission.getText().toString().trim();
            String dueDate = editTextDueDate.getText().toString().trim();
            String emailAddress = editTextEmailAddress.getText().toString().trim();
            String phoneNumber = editTextPhoneNumber.getText().toString().trim();
            String briefDescription = editTextBriefDescription.getText().toString().trim();
            String notes = editTextNotes.getText().toString().trim();
            String pageNumbers = editTextPageNumbers.getText().toString().trim();

            // Create a new Assignment object
            Assignment assignment = new Assignment(
                    fullName, studentId, studentClass, subject, assignmentTitle, dateOfSubmission,
                    dueDate, emailAddress, phoneNumber, briefDescription, notes, pageNumbers, null
            );

            // Upload the image and save the assignment details
            uploadImageAndSaveAssignment(assignment);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void uploadImageAndSaveAssignment(Assignment assignment) {
        if (photoUri != null) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
            StorageReference imagesRef = storageRef.child("images/" + photoUri.getLastPathSegment());
            UploadTask uploadTask = imagesRef.putFile(photoUri);

            uploadTask.addOnSuccessListener(taskSnapshot -> imagesRef.getDownloadUrl().addOnSuccessListener(uri -> {
                Toast.makeText(Academics.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();

                // Set the image URL in the assignment object
                assignment.setImageUrl(uri.toString());

                // Save the assignment details to Firestore
                assignmentsRef.add(assignment)
                        .addOnSuccessListener(documentReference -> Toast.makeText(Academics.this, "Assignment submitted successfully", Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e -> Toast.makeText(Academics.this, "Failed to submit assignment: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            })).addOnFailureListener(e -> {
                Toast.makeText(Academics.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }
}

