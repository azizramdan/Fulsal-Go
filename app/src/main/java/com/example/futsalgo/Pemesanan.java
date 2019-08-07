package com.example.futsalgo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.futsalgo.data.WaktuPilihAdapter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class Pemesanan extends Fragment {
    public Pemesanan() {

    }

    LinearLayout view;
    String nama_lapangan, waktu_pilih, metode_bayar, harga_lapangan, bank, nama_rekening, no_rekening, no_hp;
    Integer id_lapangan, id_user;
    Spinner spinner;
    RecyclerView recyclerView;
    public static ArrayList<String> waktu_pilih_state, waktu_pilih_text_state;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = (LinearLayout) inflater.inflate(R.layout.pemesanan, container, false);
        recyclerView = view.findViewById(R.id.main_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        AndroidNetworking.initialize(getActivity());

        getActivity().setTitle("Pilih Jam & Metode Bayar");

        waktu_pilih_state = new ArrayList<>();
        waktu_pilih_text_state = new ArrayList<>();

        Bundle bundle = this.getArguments();
        id_lapangan = bundle.getInt("id_lapangan");
        nama_lapangan = bundle.getString("nama_lapangan");
        no_hp = bundle.getString("no_hp");
        harga_lapangan = bundle.getString("harga_lapangan");
        waktu_pilih = bundle.getString("waktu_pilih");
        bank = bundle.getString("bank");
        nama_rekening = bundle.getString("nama_rekening");
        no_rekening = bundle.getString("no_rekening");
        SharedPreferences user = getActivity().getSharedPreferences("dataUser", Context.MODE_PRIVATE);
        id_user = user.getInt("id", 0);

        spinner = (Spinner) view.findViewById(R.id.metode_bayar);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.metode_bayar, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                metode_bayar = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        Button detail_pesan = view.findViewById(R.id.detail_pesanan);
        detail_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailPesan(v);
            }
        });
        getTime();
        return view;
    }

    private void getTime() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        AndroidNetworking.post(Konfigurasi.PESANAN)
                .addBodyParameter("method", "showTime")
                .addBodyParameter("waktu_pilih", waktu_pilih)
                .addBodyParameter("id_lapangan", id_lapangan.toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList<String> waktu_pilih_arr = new ArrayList<>();
                        ArrayList<String> waktu_pilih_text_arr = new ArrayList<>();
                        ArrayList<Boolean> kosong = new ArrayList<>();
                        try {
                            JSONArray data = response.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject dataTime = data.getJSONObject(i);
                                waktu_pilih_arr.add(dataTime.optString("waktu_pilih"));
                                waktu_pilih_text_arr.add(dataTime.optString("waktu_pilih_text"));
                                kosong.add(dataTime.optBoolean("kosong"));
                            }
                            WaktuPilihAdapter adapter = new WaktuPilihAdapter(waktu_pilih_arr, waktu_pilih_text_arr, kosong);
                            recyclerView.setAdapter(adapter);
                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        progressDialog.dismiss();
                    }
                });
    }
    private void detailPesan(View v) {
        if(waktu_pilih_state.size() != 0) {
            Bundle bundle = new Bundle();
            bundle.putInt("id_lapangan", id_lapangan);
            bundle.putString("nama_lapangan", nama_lapangan);
            bundle.putString("no_hp", no_hp);
            bundle.putString("waktu_pilih_tanggal", waktu_pilih);
            bundle.putString("harga_lapangan", harga_lapangan);
            bundle.putString("metode_bayar", metode_bayar);
            bundle.putString("bank", bank);
            bundle.putString("nama_rekening", nama_rekening);
            bundle.putString("no_rekening", no_rekening);
            bundle.putStringArrayList("waktu_pilih_jamDB", waktu_pilih_state);
            bundle.putStringArrayList("waktu_pilih_jam", waktu_pilih_text_state);

            Fragment fragment = new PemesananDetail();
            fragment.setArguments(bundle);
            AppCompatActivity activity = (AppCompatActivity) v.getContext();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_container, fragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            Toast.makeText(getActivity(), "Pilih jam terlebih dahulu!",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
