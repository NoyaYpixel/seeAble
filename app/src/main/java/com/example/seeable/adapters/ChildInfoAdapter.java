package com.example.seeable.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeable.R;
import com.example.seeable.model.Child;

import java.util.ArrayList;
import java.util.List;

public class ChildInfoAdapter extends RecyclerView.Adapter<ChildInfoAdapter.ChildViewHolder>{

    final List<Child> childList;

    public ChildInfoAdapter() {
        childList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate של הפריסה עבור פריט יחיד
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_childinfo, parent, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {
        Child child = childList.get(position);
        if (child == null) return;

        holder.tvFNameC.setText(child.getFname());
        holder.tvLNameC.setText(child.getLname());
        holder.tvCId.setText(child.getId());
        holder.tvCommentsC.setText(child.getNotes());
        holder.tvbirthdayC.setText(child.getBirthday().toString());

    }

    @Override
    public int getItemCount() {
        return childList.size();
    }

    public List<Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Child> childList) {
        this.childList.clear();
        this.childList.addAll(childList);
        this.notifyDataSetChanged();
    }

    protected static class ChildViewHolder extends RecyclerView.ViewHolder {
        TextView tvLNameC, tvbirthdayC, tvCId, tvFNameC, tvCommentsC;

        public ChildViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFNameC = itemView.findViewById(R.id.tvFNameC);
            tvLNameC = itemView.findViewById(R.id.tvLNameC);
            tvCId = itemView.findViewById(R.id.tvCId);
            tvbirthdayC = itemView.findViewById(R.id.tvbirthdayC);
            tvCommentsC = itemView.findViewById(R.id.tvCommentC);

        }
    }
}
