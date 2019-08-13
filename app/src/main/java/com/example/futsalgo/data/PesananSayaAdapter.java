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
import com.example.futsalgo.PesananSayaDetail;
import com.example.futsalgo.R;
import com.example.futsalgo.data.model.PesananSaya;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class PesananSayaAdapter extends RecyclerView.Adapter<PesananSayaAdapter.ViewHolder> {
    private List<PesananSaya> list;
    Fragment fragment = null;

    public PesananSayaAdapter(List<PesananSaya> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pesanan_saya_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final PesananSaya pesanan_saya = list.get(position);

        holder.textId.setText("ID pesanan " + pesanan_saya.getId().toString());
        holder.textNamaLapangan.setText(pesanan_saya.getNamaLapangan());
        holder.textWaktuPilihTanggal.setText(pesanan_saya.getWaktuPilihTanggal());
        holder.textWaktuPilihJam.setText(pesanan_saya.getWaktuPilihJam());
        holder.textStatus.setText(pesanan_saya.getStatus());
        holder.textHarga.setText(NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(Double.parseDouble(pesanan_saya.getHarga())));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", pesanan_saya.getId());
                bundle.putString("nama_lapangan", pesanan_saya.getNamaLapangan());
                bundle.putString("waktu_pilih_tanggal", pesanan_saya.getWaktuPilihTanggal());
                bundle.putString("waktu_pilih_jam", pesanan_saya.getWaktuPilihJam());
                bundle.putString("metode_bayar", pesanan_saya.getMetodeBayar());
                bundle.putString("status", pesanan_saya.getStatus());
                bundle.putString("harga", pesanan_saya.getHarga());
                bundle.putString("alamat", pesanan_saya.getAlamat());
                bundle.putString("bank", pesanan_saya.getBank());
                bundle.putString("nama_rekening", pesanan_saya.getNamaRekening());
                bundle.putString("no_rekening", pesanan_saya.getNoRekening());
                bundle.putString("telp", pesanan_saya.getTelp());
                bundle.putString("latitude", pesanan_saya.getLatitude());
                bundle.putString("longitude", pesanan_saya.getLongitude());

                fragment = new PesananSayaDetail();
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
        private TextView textId, textNamaLapangan, textWaktuPilihTanggal, textWaktuPilihJam, textStatus, textHarga;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            textId = itemView.findViewById(R.id.id_pesanan);
            textNamaLapangan = itemView.findViewById(R.id.nama_lapangan);
            textWaktuPilihTanggal = itemView.findViewById(R.id.waktu_pilih_tanggal);
            textWaktuPilihJam = itemView.findViewById(R.id.waktu_pilih_jam);
            textStatus = itemView.findViewById(R.id.status);
            textHarga = itemView.findViewById(R.id.harga);
            cardView = itemView.findViewById(R.id.cardView);
        }

    }
}
