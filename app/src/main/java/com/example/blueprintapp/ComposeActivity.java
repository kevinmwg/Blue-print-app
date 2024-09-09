package com.example.blueprintapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class ComposeActivity extends AppCompatActivity {

    private EditText etTitle, etMessage;
    private Button btnSend;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compose);

        etTitle = findViewById(R.id.etTitle);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);

        db = FirebaseFirestore.getInstance();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String message = etMessage.getText().toString();
                if (!title.isEmpty() && !message.isEmpty()) {
                    sendNotification(title, message);
                } else {
                    Toast.makeText(ComposeActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendNotification(String title, String message) {
        Map<String, Object> notification = new HashMap<>();
        notification.put("title", title);
        notification.put("message", message);

        db.collection("notifications")
                .add(notification)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(ComposeActivity.this, "Notification sent", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ComposeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(ComposeActivity.this, "Error sending notification", Toast.LENGTH_SHORT).show();
                });
    }
}