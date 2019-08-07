package com.example.futsalgo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.futsalgo.data.WaktuPilihJamAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import static com.google.android.gms.wearable.DataMap.TAG;

public class DetailPemesanan extends Fragment {
    public DetailPemesanan() {

    }

    LinearLayout view;
    String nama_lapangan, waktu_pilih_tanggal, metode_bayar, harga_lapangan, bank, nama_rekening, no_rekening, no_hp;
    Integer id_lapangan, id_user;
    ArrayList<String> waktu_pilih_jam;
    JSONArray waktu_pilih_jamDB;
    TextView tvnama_lapangan, tvharga_lapangan, tvwaktu_pilih_tanggal, tvtotal_bayar, tvmetode_bayar, tvbank, tvnama_rekening, tvno_rekening, tvketerangan, tvno_hp;
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
        no_hp = bundle.getString("no_hp");
        waktu_pilih_tanggal = bundle.getString("waktu_pilih_tanggal");
        metode_bayar = bundle.getString("metode_bayar");
        harga_lapangan = bundle.getString("harga_lapangan");
        bank = bundle.getString("bank");
        nama_rekening = bundle.getString("nama_rekening");
        no_rekening = bundle.getString("no_rekening");
//        waktu_pilih_jamDB = bundle.getStringArrayList("waktu_pilih_jamDB");
        waktu_pilih_jamDB = new JSONArray(bundle.getStringArrayList("waktu_pilih_jamDB"));
        waktu_pilih_jam = bundle.getStringArrayList("waktu_pilih_jam");

        tvnama_lapangan = view.findViewById(R.id.nama_lapangan);
        tvharga_lapangan = view.findViewById(R.id.harga_lapangan);
        tvwaktu_pilih_tanggal = view.findViewById(R.id.waktu_pilih_tanggal);
        tvtotal_bayar = view.findViewById(R.id.total_bayar);
        tvmetode_bayar = view.findViewById(R.id.metode_bayar);
        tvbank = view.findViewById(R.id.bank);
        tvnama_rekening = view.findViewById(R.id.nama_rekening);
        tvno_rekening = view.findViewById(R.id.no_rekening);
        tvketerangan = view.findViewById(R.id.keterangan);
        tvno_hp = view.findViewById(R.id.no_hp);

        if(metode_bayar.equals("COD")) {
            tvbank.setVisibility(View.GONE);
            tvnama_rekening.setVisibility(View.GONE);
            tvno_rekening.setVisibility(View.GONE);
            tvno_hp.setVisibility(View.GONE);
            tvketerangan.setText("Segera lakukan pembayaran ke lokasi sebelum jam main tiba, apabila sudah melewati jam main maka otomatis pesanan akan dibatalkan");
        } else {
            tvbank.setText(bundle.getString("bank"));
            tvnama_rekening.setText("a.n. " + bundle.getString("nama_rekening"));
            tvno_rekening.setText(bundle.getString("no_rekening"));
            tvketerangan.setText("Segera lakukan pembayaran melalui transfer, lalu kirimkan bukti transfer sebelum jam main tiba ke no dibawah");
            tvno_hp.setText(no_hp);
        }

        String waktu_pilih_tanggal_formated = Konfigurasi.parseDate(waktu_pilih_tanggal, "yyyy-M-d", "EEEE, dd MMMM YYYY");

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
                pesanSekarang(view);

            }
        });
        return view;
    }

    private void pesanSekarang(View view) {
        final View v = view;
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Sending...");
        progressDialog.show();
        JSONObject data = new JSONObject();
        try {
            data.put("method", "store");
            data.put("id_user", id_user);
            data.put("id_lapangan", id_lapangan);
            data.put("waktu_pilih_tanggal", waktu_pilih_tanggal);
            data.put("waktu_pilih_jam", waktu_pilih_jamDB);
            data.put("metode_bayar", metode_bayar);

            Log.d(TAG, "zzz data yang dikirim: " + data);

            AndroidNetworking.post(Konfigurasi.PESANAN)
                    .addJSONObjectBody(data)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, "zzz respon php: " + response);
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Pemesanan berhasil!", Toast.LENGTH_LONG).show();

                            Fragment fragment = new MenuBeranda();
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();

                            activity.getSupportFragmentManager()
                                    .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            activity.getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.frame_container, fragment)
                                    .commit();
                        }
                        @Override
                        public void onError(ANError error) {
                            Log.d(TAG, "zzz onError: Failed " + error);
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Pemesanan gagal!", Toast.LENGTH_SHORT).show();
                        }
                    });

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "zzz onError: Failed json " + e);
            progressDialog.dismiss();
            Toast.makeText(getActivity(), "Pemesanan gagal!", Toast.LENGTH_SHORT).show();
        }
    }
}
