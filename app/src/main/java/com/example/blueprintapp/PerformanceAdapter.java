package com.example.blueprintapp;
// PerformanceAdapter.java


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class PerformanceAdapter extends RecyclerView.Adapter<PerformanceAdapter.PerformanceViewHolder> {

    private List<StudentPerformance> students;

    public PerformanceAdapter(List<StudentPerformance> students) {
        this.students = students;
    }

    @NonNull
    @Override
    public PerformanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student_performance, parent, false);
        return new PerformanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PerformanceViewHolder holder, int position) {
        StudentPerformance student = students.get(position);
        holder.nameTextView.setText(student.getName());

        // Grade chart
        List<BarEntry> gradeEntries = new ArrayList<>();
        gradeEntries.add(new BarEntry(0, student.getGrade()));
        BarDataSet gradeDataSet = new BarDataSet(gradeEntries, "Grade");
        BarData gradeData = new BarData(gradeDataSet);
        holder.gradeChart.setData(gradeData);
        holder.gradeChart.invalidate();

        // Attendance chart
        int attendance = student.getAttendance();
        int absence = 100 - attendance;
        List<PieEntry> attendanceEntries = new ArrayList<>();
        attendanceEntries.add(new PieEntry(attendance, "Attendance"));
        attendanceEntries.add(new PieEntry(absence, "Absence"));
        PieDataSet attendanceDataSet = new PieDataSet(attendanceEntries, "Attendance");
        PieData attendanceData = new PieData(attendanceDataSet);
        holder.attendanceChart.setData(attendanceData);
        holder.attendanceChart.invalidate();
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public void filterList(List<StudentPerformance> filteredList) {
        students = filteredList;
        notifyDataSetChanged();
    }

    static class PerformanceViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        BarChart gradeChart;
        PieChart attendanceChart;

        public PerformanceViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.student_name);
            gradeChart = itemView.findViewById(R.id.grade_chart);
            attendanceChart = itemView.findViewById(R.id.attendance_chart);
        }
    }
}
