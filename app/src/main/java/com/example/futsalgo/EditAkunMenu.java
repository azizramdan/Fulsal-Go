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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class EditAkunMenu extends Fragment {
    public EditAkunMenu(){}
    LinearLayout view;
    Integer id;
    TextView tvnama, tvemail, tvtelp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Edit Akun");

        view = (LinearLayout) inflater.inflate(R.layout.edit_akun_menu, container, false);
        tvnama = view.findViewById(R.id.nama);
        tvemail = view.findViewById(R.id.email);
        tvtelp = view.findViewById(R.id.telp);

        AndroidNetworking.initialize(getActivity());
        SharedPreferences user = getActivity().getSharedPreferences("dataUser", Context.MODE_PRIVATE);
        id = user.getInt("id", 0);
        edit();

        Button btnsimpan = view.findViewById(R.id.simpan);
        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan();
            }
        });
        return view;
    }

    private void edit() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        AndroidNetworking.post(Konfigurasi.USER)
                .addBodyParameter("method", "edit")
                .addBodyParameter("id", id.toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "berhasil mang response " + response);
                        try {
                            JSONArray data = response.getJSONArray("data");
                            JSONObject dataUser = data.getJSONObject(0);

                            tvnama.setText(dataUser.getString("nama"));
                            tvemail.setText(dataUser.getString("email"));
                            tvtelp.setText(dataUser.getString("telp"));

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
    private void simpan() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        AndroidNetworking.post(Konfigurasi.USER)
                .addBodyParameter("method", "update")
                .addBodyParameter("id", id.toString())
                .addBodyParameter("nama", tvnama.getText().toString())
                .addBodyParameter("telp", tvtelp.getText().toString())
                .addBodyParameter("email", tvemail.getText().toString())
                .addBodyParameter("password", id.toString())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "berhasil mang response " + response);
                        try {
                            if(response.getBoolean("status")) {
                                Toast.makeText(getActivity(), "Data berhasil disimpan!", Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(getActivity(), "Gagal menyimpan data!", Toast.LENGTH_LONG).show();

                            }

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
}
