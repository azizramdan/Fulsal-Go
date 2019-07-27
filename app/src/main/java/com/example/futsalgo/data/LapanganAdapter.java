package com.example.futsalgo.data;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.futsalgo.DetailLapangan;
import com.example.futsalgo.R;
import com.example.futsalgo.data.model.Lapangan;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import static android.support.constraint.Constraints.TAG;


public class LapanganAdapter extends RecyclerView.Adapter<LapanganAdapter.ViewHolder> {
    private List<Lapangan> list;
    Fragment fragment = null;

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
        holder.textHarga.setText(NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(Double.parseDouble(lapangan.getHarga())));
        holder.textAlamat.setText(lapangan.getAlamat());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "berhasil mang di klik " + lapangan.getNama());

                Bundle bundle = new Bundle();
                bundle.putInt("id", lapangan.getId());
                bundle.putString("nama", lapangan.getNama());
                bundle.putString("foto", lapangan.getFoto());
                bundle.putString("harga", lapangan.getHarga());
                bundle.putString("telp", lapangan.getTelp());
                bundle.putString("alamat", lapangan.getAlamat());
                bundle.putString("latitude", lapangan.getLatitude());
                bundle.putString("longitude", lapangan.getLongitude());
                bundle.putString("bank", lapangan.getBank());
                bundle.putString("nama_rekening", lapangan.getNamaRekening());
                bundle.putString("no_rekening", lapangan.getNoRekening());
                fragment = new DetailLapangan();
                fragment.setArguments(bundle);
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container, fragment)
                        .addToBackStack(null)
                        .commit();
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
