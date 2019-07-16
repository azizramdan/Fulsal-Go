package com.example.futsalgo.data;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.futsalgo.R;
import com.example.futsalgo.data.model.Lapangan;

import java.util.List;


public class LapanganAdapter extends RecyclerView.Adapter<LapanganAdapter.ViewHolder> {
    private List<Lapangan> list;

    public LapanganAdapter(List<Lapangan> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate view yang akan digunakan yaitu layout instansi_item.xml
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lapangan_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Lapangan lapangan = list.get(position);

        holder.textNama.setText(lapangan.getNama());
        holder.textHarga.setText(lapangan.getHarga());
        holder.textAlamat.setText(lapangan.getAlamat());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textNama, textHarga, textAlamat;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            textNama = itemView.findViewById(R.id.nama);
            textHarga = itemView.findViewById(R.id.harga);
            textAlamat = itemView.findViewById(R.id.alamat);
            cardView = itemView.findViewById(R.id.cardView);
        }

    }
}
