package com.example.blueprintapp;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AssignmentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AssignmentAdapter adapter;
    private FirebaseFirestore db;
    private SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);

        recyclerView = findViewById(R.id.recyclerViewAssignments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchView = findViewById(R.id.searchView);

        adapter = new AssignmentAdapter(this);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        loadAssignments();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterAssignments(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterAssignments(newText);
                return false;
            }
        });
    }

    private void loadAssignments() {
        db.collection("assignments").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<QueryDocumentSnapshot> assignments = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    assignments.add(document);
                }
                adapter.setAssignments(assignments);
            } else {
                // Handle the error
            }
        });
    }

    private void filterAssignments(String query) {
        db.collection("assignments")
                .whereEqualTo("studentClass", query)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<QueryDocumentSnapshot> filteredAssignments = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            filteredAssignments.add(document);
                        }
                        adapter.setAssignments(filteredAssignments);
                    }
                });
    }
}
