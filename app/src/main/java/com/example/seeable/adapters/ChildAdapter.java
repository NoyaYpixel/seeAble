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

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ChildViewHolder> {


    public interface OnChildClick {
        public void OnClick(Child child);
    }

    private List<Child> childList;
    private OnChildClick onChildClick;

    public ChildAdapter(OnChildClick onChildClick) {
        this.childList = new ArrayList<>();
        this.onChildClick = onChildClick;
    }


    public void setChildList(List<Child> children) {
        this.childList.clear();
        this.childList.addAll(children);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate של הפריסה עבור פריט יחיד
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_child, parent, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {
        // קישור הנתונים לפריסה
        Child child = childList.get(position);
        holder.tvChildName.setText(child.getFname() + " " + child.getLname());
        holder.tvCId.setText(child.getId());
        holder.tvbirthdayC.setText(child.getBirthday().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChildClick.OnClick(child);
            }
        });
    }

    @Override
    public int getItemCount() {
        // מחזיר את מספר הפריטים ברשימה
        return childList.size();
    }

    // ViewHolder - מחזיק את ה-View עבור פריט יחיד
    protected static class ChildViewHolder extends RecyclerView.ViewHolder {
        TextView tvChildName, tvbirthdayC, tvCId;

        public ChildViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChildName = itemView.findViewById(R.id.tvChildName);
            tvCId = itemView.findViewById(R.id.tvCId);
            tvbirthdayC = itemView.findViewById(R.id.tvbirthdayC);
        }
    }
}