package com.example.blueprintapp;



import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PerformanceTrack extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PerformanceAdapter adapter;
    private List<StudentPerformance> studentPerformanceList;
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_track);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        studentPerformanceList = new ArrayList<>();
        adapter = new PerformanceAdapter(studentPerformanceList);
        recyclerView.setAdapter(adapter);

        searchBar = findViewById(R.id.search_bar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        fetchDataFromDatabase();
    }

    private void fetchDataFromDatabase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("performance");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                studentPerformanceList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    StudentPerformance student = snapshot.getValue(StudentPerformance.class);
                    studentPerformanceList.add(student);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }

    private void filter(String text) {
        List<StudentPerformance> filteredList = new ArrayList<>();
        for (StudentPerformance student : studentPerformanceList) {
            if (student.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(student);
            }
        }
        adapter.filterList(filteredList);
    }
}

