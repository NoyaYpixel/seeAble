package com.example.seeable.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seeable.R;
import com.example.seeable.model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    public interface OnUserListener {
        void onSwitch(boolean isChecked);
    }

    List<User> users;
    OnUserListener onUserListener;

    public UsersAdapter(OnUserListener onUserListener) {
        users = new ArrayList<>();
        this.onUserListener = onUserListener;
    }

    public void setUsers(@NonNull List<User> users) {
        this.users.clear();
        this.users.addAll(users);
        notifyDataSetChanged();
    }

    public List<User> getUsers() {
        return users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /// inflate the item_selected_food layout
        /// @see R.layout.item_selected_food
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = this.users.get(position);
        if (user == null) return;

        holder.userFNameTextView.setText(user.getFname());
        holder.userLNameTextView.setText(user.getLname());
        holder.switchUser.setChecked(user.getPosition().equals(User.Position.Team.getType()));

        holder.switchUser.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onUserListener.onSwitch(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView userFNameTextView;
        public final TextView userLNameTextView;
        public final Switch switchUser;

        public ViewHolder(View itemView) {
            super(itemView);
            userFNameTextView = itemView.findViewById(R.id.user_fname_text_view);
            userLNameTextView = itemView.findViewById(R.id.user_lname_text_view);
            switchUser = itemView.findViewById(R.id.switch_user);
        }
    }

}
