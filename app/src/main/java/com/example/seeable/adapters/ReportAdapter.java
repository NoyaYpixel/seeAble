package com.example.seeable.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeable.R;
import com.example.seeable.model.Child;
import com.example.seeable.model.Report;
import com.example.seeable.screens.DailyReport;

import java.util.ArrayList;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder>{


    public interface OnReportClick {
        public void OnClick(Report report);
    }

    private List<Report> dailyReportList;
    private ReportAdapter.OnReportClick onReportClick;

    public ReportAdapter(ReportAdapter.OnReportClick onReportClick) {
        this.dailyReportList = new ArrayList<>();
        this.onReportClick = onReportClick;
    }

    public void setReportList(List<Report> reports) {
        this.dailyReportList.clear();
        this.dailyReportList.addAll(reports);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReportAdapter.ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_child, parent, false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ReportViewHolder holder, int position) {
        Report report = dailyReportList.get(position);
        holder.tvDate.setText(report.getDate().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReportClick.OnClick(report);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dailyReportList.size();
    }


    public class ReportViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate;
        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.item_report_date);
        }
    }
}
