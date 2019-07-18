package com.example.futsalgo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.futsalgo.data.WaktuPilihJamAdapter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DetailPemesanan extends Fragment {
    public DetailPemesanan() {

    }

    LinearLayout view;
    String nama_lapangan, waktu_pilih_tanggal, metode_bayar, harga_lapangan;
    Integer id_lapangan, id_user;
    ArrayList<String> waktu_pilih_jam, waktu_pilih_jamDB;
    TextView tvnama_lapangan, tvharga_lapangan, tvwaktu_pilih_tanggal, tvtotal_bayar, tvmetode_bayar;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = (LinearLayout) inflater.inflate(R.layout.detail_pemesanan, container, false);
//        AndroidNetworking.initialize(getActivity());
        getActivity().setTitle("Detail Pemesanan");
        recyclerView = view.findViewById(R.id.main_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Bundle bundle = this.getArguments();
        id_lapangan = bundle.getInt("id_lapangan");
        nama_lapangan = bundle.getString("nama_lapangan");
        waktu_pilih_tanggal = bundle.getString("waktu_pilih_tanggal");
        metode_bayar = bundle.getString("metode_bayar");
        harga_lapangan = bundle.getString("harga_lapangan");
        waktu_pilih_jamDB = bundle.getStringArrayList("waktu_pilih_jamDB");
        waktu_pilih_jam = bundle.getStringArrayList("waktu_pilih_jam");

        tvnama_lapangan = view.findViewById(R.id.nama_lapangan);
        tvharga_lapangan = view.findViewById(R.id.harga_lapangan);
        tvwaktu_pilih_tanggal = view.findViewById(R.id.waktu_pilih_tanggal);
        tvtotal_bayar = view.findViewById(R.id.total_bayar);
        tvmetode_bayar = view.findViewById(R.id.metode_bayar);

        String waktu_pilih_tanggal_formated = parseDate(waktu_pilih_tanggal, "yyyy-M-d", "EEEE, dd MMMM YYYY");

        String harga_lapangan_idr = NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(Double.parseDouble(harga_lapangan));
        Integer total_bayar = waktu_pilih_jam.size() * Integer.parseInt(harga_lapangan);
        String total_bayar_idr = NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(Double.parseDouble(total_bayar.toString()));

        tvnama_lapangan.setText(nama_lapangan);
        tvharga_lapangan.setText("Harga per jam " + harga_lapangan_idr);
        tvwaktu_pilih_tanggal.setText("Tanggal yang dipilih " + waktu_pilih_tanggal_formated);
        tvtotal_bayar.setText("Total bayar " + waktu_pilih_jam.size() + " jam X " + harga_lapangan_idr + " = " + total_bayar_idr);
        tvmetode_bayar.setText("Pembayaran melalui " + metode_bayar);

        SharedPreferences user = getActivity().getSharedPreferences("dataUser", Context.MODE_PRIVATE);
        id_user = user.getInt("id", 0);

        WaktuPilihJamAdapter adapter = new WaktuPilihJamAdapter(waktu_pilih_jam);
        recyclerView.setAdapter(adapter);

        Button pesan_sekarang = view.findViewById(R.id.pesan_sekarang);
        pesan_sekarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                detailPesan(v);
            }
        });
//        getTime();
        return view;
    }

    public String parseDate(String time, String inputPattern, String outputPattern) {

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, new Locale("id", "ID"));

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}
