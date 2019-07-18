package com.example.futsalgo.data;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.futsalgo.R;

import java.util.ArrayList;

public class WaktuPilihJamAdapter extends RecyclerView.Adapter<WaktuPilihJamAdapter.ViewHolder> {
    private ArrayList<String> list;

    public WaktuPilihJamAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.jam_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String data = list.get(position);

        holder.jamItem.setText(data);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView jamItem;

        public ViewHolder(View itemView) {
            super(itemView);
            jamItem = itemView.findViewById(R.id.jam_item);
        }

    }
}
