package com.example.blueprintapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {

    private List<QueryDocumentSnapshot> assignments = new ArrayList<>();
    private Context context;

    public AssignmentAdapter(Context context) {
        this.context = context;
    }

    public void setAssignments(List<QueryDocumentSnapshot> assignments) {
        this.assignments = assignments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_assignment, parent, false);
        return new AssignmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
        DocumentSnapshot assignment = assignments.get(position);
        holder.tvStudentName.setText(assignment.getString("fullName"));
        holder.tvAssignmentTitle.setText(assignment.getString("assignmentTitle"));
        holder.tvSubmissionDate.setText(assignment.getString("dateOfSubmission"));
        holder.tvClass.setText(assignment.getString("studentClass"));
        holder.tvSubject.setText(assignment.getString("subject"));

        String imageUrl = assignment.getString("imageUrl");

        holder.btnSeeDetails.setOnClickListener(v -> {
            if (imageUrl != null && !imageUrl.isEmpty()) {
                Picasso.get().load(imageUrl).into(holder.ivAssignmentImage);
                holder.ivAssignmentImage.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(context, "No image available", Toast.LENGTH_SHORT).show();
                holder.ivAssignmentImage.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }

    static class AssignmentViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentName, tvAssignmentTitle, tvSubmissionDate, tvClass, tvSubject;
        Button btnSeeDetails;
        ImageView ivAssignmentImage;

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
            tvAssignmentTitle = itemView.findViewById(R.id.tvAssignmentTitle);
            tvSubmissionDate = itemView.findViewById(R.id.tvSubmissionDate);
            tvClass = itemView.findViewById(R.id.tvClass);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            btnSeeDetails = itemView.findViewById(R.id.btnSeeDetails);
            ivAssignmentImage = itemView.findViewById(R.id.ivAssignmentImage);
        }
    }
}
