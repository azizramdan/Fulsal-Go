package com.example.futsalgo;

import android.app.ProgressDialog;
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
import com.example.futsalgo.data.model.Lapangan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.support.constraint.Constraints.TAG;

public class MenuBeranda extends Fragment {
    public MenuBeranda(){}
    RelativeLayout view;
    private List<Lapangan> dataList;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Beranda");

        view = (RelativeLayout) inflater.inflate(R.layout.menu_beranda, container, false);
        recyclerView = view.findViewById(R.id.main_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dataList = new ArrayList<>();

        AndroidNetworking.initialize(getActivity());

        getLapangan();
        return view;
    }

    public void getLapangan() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        AndroidNetworking.get(Konfigurasi.LAPANGAN)
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
                                JSONObject dataLapangan = data.getJSONObject(i);

                                dataList.add(new Lapangan(
                                        dataLapangan.getInt("id"),
                                        dataLapangan.getString("nama"),
                                        NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(Double.parseDouble(dataLapangan.getString("harga"))),
                                        dataLapangan.getString("telp"),
                                        dataLapangan.getString("alamat"),
                                        dataLapangan.getString("longitude"),
                                        dataLapangan.getString("latitude"),
                                        dataLapangan.getString("foto"),
                                        dataLapangan.getString("email")
                                        ));
                            }

                            Log.d(TAG, "berhasil mang selesai looping");
                            LapanganAdapter adapter = new LapanganAdapter(dataList);
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
