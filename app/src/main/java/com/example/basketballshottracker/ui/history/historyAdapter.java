package com.example.basketballshottracker.ui.history;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basketballshottracker.R;

import java.util.List;

public class historyAdapter extends RecyclerView.Adapter<historyAdapter.historyVH> {

    private static final String TAG = "historyAdapter";
    List<previousSession> previousSessionsList;

    public historyAdapter(List<previousSession> previousSessionsList) {
        this.previousSessionsList = previousSessionsList;
    }

    @NonNull
    @Override
    public historyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.previous_session_item, parent, false);
        return new historyVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull historyVH holder, int position) {
        previousSession previousSession = previousSessionsList.get(position);
        holder.sessionDateTextView.setText(previousSession.getDate());
        holder.previousMakesTextView.setText(previousSession.getMakes());
        holder.previousMissesTextView.setText(previousSession.getMisses());
        holder.totalShotsTextView.setText(previousSession.getTotal());

        Log.d(TAG, "Current List Displaying: " + previousSession.getDate() +","+ previousSession.getMakes() +","+ previousSession.getMisses() +","+ previousSession.getTotal());

        boolean isExpanded = previousSession.isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        holder.chevron.setRotation(isExpanded ? -90 : 90);

        // Set the visibility of the "star" item based on highScore
        if (previousSession.isHighScore()) {
            holder.star.setVisibility(View.VISIBLE);
        } else {
            holder.star.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return previousSessionsList.size();
    }

    public class historyVH extends RecyclerView.ViewHolder {

        private static final String TAG = "historyVH";

        ConstraintLayout expandableLayout;
        TextView sessionDateTextView, previousMakesTextView, previousMissesTextView, totalShotsTextView;
        ImageView chevron, star;
        CardView historyCard;
        public historyVH(@NonNull final View view) {
            super(view);

            sessionDateTextView = view.findViewById(R.id.sessionDateTextView);
            previousMakesTextView = view.findViewById(R.id.previousMakesTextView);
            previousMissesTextView = view.findViewById(R.id.previousMissesTextView);
            totalShotsTextView = view.findViewById(R.id.totalShotsTextView);
            expandableLayout = view.findViewById(R.id.expandableLayout);

            historyCard = view.findViewById(R.id.historyCardView);
            chevron = view.findViewById(R.id.expandedChevron);
            star = view.findViewById(R.id.highlightStar);
            star.setVisibility(View.GONE);

            historyCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    previousSession previousSession = previousSessionsList.get(getAdapterPosition());
                    previousSession.setExpanded(!previousSession.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
