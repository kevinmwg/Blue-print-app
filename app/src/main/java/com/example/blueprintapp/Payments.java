package com.example.blueprintapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Payments extends AppCompatActivity {

    private EditText etStudentName, etAdmissionNumber, etClass, etAmount, etPhoneNumber;
    private Button btnPush;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

        etStudentName = findViewById(R.id.etStudentName);
        etAdmissionNumber = findViewById(R.id.etAdmissionNumber);
        etClass = findViewById(R.id.etClass);
        etAmount = findViewById(R.id.etAmount);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnPush = findViewById(R.id.btnPush);

        btnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String studentName = etStudentName.getText().toString();
                String admissionNumber = etAdmissionNumber.getText().toString();
                String className = etClass.getText().toString();
                String amount = etAmount.getText().toString();
                String phoneNumber = etPhoneNumber.getText().toString();

                if (studentName.isEmpty() || admissionNumber.isEmpty() || className.isEmpty() || amount.isEmpty() || phoneNumber.isEmpty()) {
                    Toast.makeText(Payments.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Call method to initiate the transaction
                    initiateTransaction(studentName, admissionNumber, className, amount, phoneNumber);
                }
            }
        });
    }

    private void initiateTransaction(String studentName, String admissionNumber, String className, String amount, String phoneNumber) {
        // Implement the logic to initiate the transaction

        // This could involve calling an API, performing a database operation, etc.
        // For example, you can initiate an M-PESA STK push here

        // Sample code to show a toast message for now
        Toast.makeText(this, "Transaction initiated for " + studentName, Toast.LENGTH_SHORT).show();
    }
}

