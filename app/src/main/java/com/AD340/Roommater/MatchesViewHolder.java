package com.AD340.Roommater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MatchesViewHolder extends RecyclerView.ViewHolder {

    public TextView matchesAge;
    public TextView matchesName;
    public ImageButton likeButton;

    public MatchesViewHolder(@NonNull View itemView) {
        super(itemView);
        matchesAge = itemView.findViewById(R.id.matches_age);
        matchesName = itemView.findViewById(R.id.matches_name);
        likeButton = itemView.findViewById(R.id.like_button);
    }
}
