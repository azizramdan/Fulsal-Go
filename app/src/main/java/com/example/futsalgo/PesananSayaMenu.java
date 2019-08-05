package com.example.futsalgo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.futsalgo.data.LapanganAdapter;
import com.example.futsalgo.data.PesananSayaAdapter;
import com.example.futsalgo.data.model.Lapangan;
import com.example.futsalgo.data.model.PesananSaya;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class PesananSayaMenu extends Fragment {
    public PesananSayaMenu(){}
    RelativeLayout view;
    List<PesananSaya> dataList;
    RecyclerView recyclerView;
    Integer id_user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Pesanan Saya");

        view = (RelativeLayout) inflater.inflate(R.layout.pesanan_saya_menu, container, false);
        recyclerView = view.findViewById(R.id.main_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dataList = new ArrayList<>();
        SharedPreferences user = getActivity().getSharedPreferences("dataUser", Context.MODE_PRIVATE);
        id_user = user.getInt("id", 0);

        AndroidNetworking.initialize(getActivity());

        getPesananSaya();
        return view;
    }

    public void getPesananSaya() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        AndroidNetworking.get(Konfigurasi.PESANAN)
                .addQueryParameter("method", "getClient")
                .addQueryParameter("id_user", id_user.toString())
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "berhasil mang response " + response);
                        try {
                            JSONArray data = response.getJSONArray("data");
                            Log.d(TAG, "berhasil mang data " + data);

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject dataPesanan = data.getJSONObject(i);

                                dataList.add(new PesananSaya(
                                        dataPesanan.getInt("id"),
                                        dataPesanan.getString("waktu_pilih_tanggal"),
                                        dataPesanan.getString("waktu_pilih_jam"),
                                        dataPesanan.getString("metode_bayar"),
                                        dataPesanan.getString("status"),
                                        dataPesanan.getString("nama_lapangan"),
                                        dataPesanan.getString("harga"),
                                        dataPesanan.getString("alamat")
                                        ));
                            }

                            Log.d(TAG, "berhasil mang selesai looping");
                            PesananSayaAdapter adapter = new PesananSayaAdapter(dataList);
                            recyclerView.setAdapter(adapter);
                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        Log.d(TAG, "error mang " + error);
                        progressDialog.dismiss();
                    }
                });
    }

}
