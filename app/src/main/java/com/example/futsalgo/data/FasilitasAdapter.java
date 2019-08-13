package com.example.futsalgo.data;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.futsalgo.LapanganDetail;
import com.example.futsalgo.R;
import com.example.futsalgo.data.model.Lapangan;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class FasilitasAdapter extends RecyclerView.Adapter<FasilitasAdapter.ViewHolder> {
    private ArrayList<String> list;

    public FasilitasAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lapangan_detail_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final String data = list.get(position);

        holder.textNama.setText(data);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textNama;

        public ViewHolder(View itemView) {
            super(itemView);
            textNama = itemView.findViewById(R.id.nama);
        }

    }
}
