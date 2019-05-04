package com.niki.eorder.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.niki.eorder.R;
import com.niki.eorder.Utility;
import com.niki.eorder.model.History;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private Context context;
    private ArrayList<History> histories;
    private Utility util = new Utility();


    public HistoryAdapter(Context context, ArrayList<History> histories){
        this.context = context;
        this.histories = histories;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_history_card, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyy, HH:mm");
        Timestamp timestamp = histories.get(position).getDateAndTime();

        holder.tvDate.setText(simpleDateFormat.format(timestamp.toDate()));
        holder.tvPrice.setText(util.toIDR(histories.get(position).getTotalPrice()));
        holder.tvLocation.setText(histories.get(position).getLocationID());
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView tvLocation, tvPrice, tvDate;
        public HistoryViewHolder(View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tv_history_time);
            tvLocation = itemView.findViewById(R.id.tv_history_location);
            tvPrice = itemView.findViewById(R.id.tv_history_total);
        }
    }
}
