package com.AD340.Roommater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Adapter used to show a simple grid of products.
 */
public class MatchesRecyclerViewAdapter extends RecyclerView.Adapter<MatchesViewHolder> {

    private List<Matches> matchesList;
    private Consumer<Matches> onClickCallback;

    MatchesRecyclerViewAdapter(List<Matches> matchesList, Consumer<Matches> onClickCallback) {
        this.matchesList = matchesList;
        this.onClickCallback = onClickCallback;
    }

    @NonNull
    @Override
    public MatchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.matches_card, parent, false);
        return new MatchesViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesViewHolder holder, int position) {
        if (matchesList != null && position < matchesList.size()) {
            Matches match = matchesList.get(position);
            holder.matchesName.setText(match.getName());
            holder.matchesAge.setText(match.getAge());
            if (match.isLiked()) {
                holder.likeButton.setImageResource(R.drawable.match_liked_button);
            } else {
                holder.likeButton.setImageResource(R.drawable.match_button_default);
            }
            holder.likeButton.setOnClickListener((v) -> {
                Toast.makeText(v.getContext(),
                        String.format(v.getContext().getString(R.string.liked_message),
                                match.getName()), Toast.LENGTH_LONG).show();
                onClickCallback.accept(match);
            });
        }
    }

    @Override
    public int getItemCount() {
        return matchesList.size();
    }
}
