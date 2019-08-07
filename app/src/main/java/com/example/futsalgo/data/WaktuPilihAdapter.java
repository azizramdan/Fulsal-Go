package com.example.futsalgo.data;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import com.example.futsalgo.Pemesanan;
import com.example.futsalgo.R;
import java.util.ArrayList;

public class WaktuPilihAdapter extends RecyclerView.Adapter<WaktuPilihAdapter.ViewHolder> {
    private ArrayList<String> waktu_pilih, waktu_pilih_text;
    private ArrayList<Boolean> kosong;
    SparseBooleanArray itemStateArray= new SparseBooleanArray();


    public WaktuPilihAdapter(ArrayList<String> waktu_pilih, ArrayList<String> waktu_pilih_text, ArrayList<Boolean> kosong) {
        this.waktu_pilih = waktu_pilih;
        this.waktu_pilih_text = waktu_pilih_text;
        this.kosong = kosong;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.waktu_pilih_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final String waktu_pilih_item = waktu_pilih.get(position);
        final String waktu_pilih_text_item = waktu_pilih_text.get(position);
        final Boolean kosong_item = kosong.get(position);

        holder.jamItem.setText(waktu_pilih_text_item);
        holder.jamItem.setEnabled(kosong_item);
        if (!itemStateArray.get(position, false)) {
            holder.jamItem.setChecked(false);}
        else {
            holder.jamItem.setChecked(true);
        }
        holder.jamItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!itemStateArray.get(position, false)) {
                    itemStateArray.put(position, true);
                    Pemesanan.waktu_pilih_state.add(waktu_pilih_item);
                    Pemesanan.waktu_pilih_text_state.add(waktu_pilih_text_item);
                }
                else  {
                    itemStateArray.put(position, false);
                    Pemesanan.waktu_pilih_state.remove(waktu_pilih_item);
                    Pemesanan.waktu_pilih_text_state.remove(waktu_pilih_text_item);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return waktu_pilih.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox jamItem;

        public ViewHolder(View itemView) {
            super(itemView);
            jamItem = itemView.findViewById(R.id.waktu_pilih);
        }

    }
}
