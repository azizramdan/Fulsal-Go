package com.example.futsalgo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import static android.support.constraint.Constraints.TAG;
import static java.lang.Boolean.FALSE;

public class Pemesanan extends Fragment {
    public Pemesanan() {

    }

    LinearLayout view;
    CheckBox jam7, jam8, jam9, jam10, jam11, jam12, jam13, jam14, jam15, jam16, jam17, jam18, jam19, jam20, jam21, jam22;
    String nama_lapangan, waktu_pilih, metode_bayar, harga_lapangan;
    Integer id_lapangan, id_user;
    Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = (LinearLayout) inflater.inflate(R.layout.pemesanan, container, false);
        AndroidNetworking.initialize(getActivity());
        getActivity().setTitle("Pilih Jam & Metode Bayar");
        Bundle bundle = this.getArguments();
        id_lapangan = bundle.getInt("id_lapangan");
        nama_lapangan = bundle.getString("nama_lapangan");
        harga_lapangan = bundle.getString("harga_lapangan");
        waktu_pilih = bundle.getString("waktu_pilih");
        SharedPreferences user = getActivity().getSharedPreferences("dataUser", Context.MODE_PRIVATE);
        id_user = user.getInt("id", 0);

        jam7 = view.findViewById(R.id.jam7);
        jam8 = view.findViewById(R.id.jam8);
        jam9 = view.findViewById(R.id.jam9);
        jam10 = view.findViewById(R.id.jam10);
        jam11 = view.findViewById(R.id.jam11);
        jam12 = view.findViewById(R.id.jam12);
        jam13 = view.findViewById(R.id.jam13);
        jam14 = view.findViewById(R.id.jam14);
        jam15 = view.findViewById(R.id.jam15);
        jam16 = view.findViewById(R.id.jam16);
        jam17 = view.findViewById(R.id.jam17);
        jam18 = view.findViewById(R.id.jam18);
        jam19 = view.findViewById(R.id.jam19);
        jam20 = view.findViewById(R.id.jam20);
        jam21 = view.findViewById(R.id.jam21);
        jam22 = view.findViewById(R.id.jam22);

        spinner = (Spinner) view.findViewById(R.id.metode_bayar);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.metode_bayar, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
//                String item = parent.getItemAtPosition(position).toString();
//                Log.d(TAG, item);
                metode_bayar = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "berhasil mang spinner " + parent.getItemAtPosition(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d(TAG, "gagal mang spinner ");

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
                        Log.d(TAG, "berhasil mang response " + response);
                        try {
                            JSONArray data = response.getJSONArray("data");
                            Log.d(TAG, "berhasil mang data " + data);

                            for (int i = 0; i < data.length(); i++) {
                                JSONObject dataTime = data.getJSONObject(i);
                                Log.d(TAG, "berhasil mang data time " + dataTime);
                                switch (dataTime.optString("waktu_pilih")) {
                                    case "07":
                                        jam7.setChecked(FALSE);
                                        jam7.setEnabled(FALSE);
                                        break;
                                    case "08":
                                        jam8.setChecked(FALSE);
                                        jam8.setEnabled(FALSE);
                                        break;
                                    case "09":
                                        jam9.setChecked(FALSE);
                                        jam9.setEnabled(FALSE);
                                        break;
                                    case "10":
                                        jam10.setChecked(FALSE);
                                        jam10.setEnabled(FALSE);
                                        break;
                                    case "11":
                                        jam11.setChecked(FALSE);
                                        jam11.setEnabled(FALSE);
                                        break;
                                    case "12":
                                        jam12.setChecked(FALSE);
                                        jam12.setEnabled(FALSE);
                                        break;
                                    case "13":
                                        jam13.setChecked(FALSE);
                                        jam13.setEnabled(FALSE);
                                        break;
                                    case "14":
                                        jam14.setChecked(FALSE);
                                        jam14.setEnabled(FALSE);
                                        break;
                                    case "15":
                                        jam15.setChecked(FALSE);
                                        jam15.setEnabled(FALSE);
                                        break;
                                    case "16":
                                        jam16.setChecked(FALSE);
                                        jam16.setEnabled(FALSE);
                                        break;
                                    case "17":
                                        jam17.setChecked(FALSE);
                                        jam17.setEnabled(FALSE);
                                        break;
                                    case "18":
                                        jam18.setChecked(FALSE);
                                        jam18.setEnabled(FALSE);
                                        break;
                                    case "19":
                                        jam19.setChecked(FALSE);
                                        jam19.setEnabled(FALSE);
                                        break;
                                    case "20":
                                        jam20.setChecked(FALSE);
                                        jam20.setEnabled(FALSE);
                                        break;
                                    case "21":
                                        jam21.setChecked(FALSE);
                                        jam21.setEnabled(FALSE);
                                        break;
                                    case "22":
                                        jam22.setChecked(FALSE);
                                        jam22.setEnabled(FALSE);
                                        break;
                                    default:
                                        break;
                                }
                            }

                            Log.d(TAG, "berhasil mang selesai looping");

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
    private void detailPesan(View v) {
        ArrayList<String> dataDB = new ArrayList<>();
        ArrayList<String> data = new ArrayList<>();

        if(jam7.isChecked()) {
            data.add(jam7.getText().toString());
            dataDB.add("07:00:00");
        }
        if(jam8.isChecked()) {
            data.add(jam8.getText().toString());
            dataDB.add("08:00:00");
        }
        if(jam9.isChecked()) {
            data.add(jam9.getText().toString());
            dataDB.add("09:00:00");
        }
        if(jam10.isChecked()) {
            data.add(jam10.getText().toString());
            dataDB.add("10:00:00");
        }
        if(jam11.isChecked()) {
            data.add(jam11.getText().toString());
            dataDB.add("11:00:00");
        }
        if(jam12.isChecked()) {
            data.add(jam12.getText().toString());
            dataDB.add("12:00:00");
        }
        if(jam13.isChecked()) {
            data.add(jam13.getText().toString());
            dataDB.add("13:00:00");
        }
        if(jam14.isChecked()) {
            data.add(jam14.getText().toString());
            dataDB.add("14:00:00");
        }
        if(jam15.isChecked()) {
            data.add(jam15.getText().toString());
            dataDB.add("15:00:00");
        }
        if(jam16.isChecked()) {
            data.add(jam16.getText().toString());
            dataDB.add("16:00:00");
        }
        if(jam17.isChecked()) {
            data.add(jam17.getText().toString());
            dataDB.add("17:00:00");
        }
        if(jam18.isChecked()) {
            data.add(jam18.getText().toString());
            dataDB.add("18:00:00");
        }
        if(jam19.isChecked()) {
            data.add(jam19.getText().toString());
            dataDB.add("19:00:00");
        }
        if(jam20.isChecked()) {
            data.add(jam20.getText().toString());
            dataDB.add("20:00:00");
        }
        if(jam21.isChecked()) {
            data.add(jam21.getText().toString());
            dataDB.add("21:00:00");
        }
        if(jam22.isChecked()) {
            data.add(jam22.getText().toString());
            dataDB.add("22:00:00");
        }

        if(dataDB.size() != 0) {
            Bundle bundle = new Bundle();
            bundle.putInt("id_lapangan", id_lapangan);
            bundle.putString("nama_lapangan", nama_lapangan);
            bundle.putString("waktu_pilih_tanggal", waktu_pilih);
            bundle.putString("harga_lapangan", harga_lapangan);
            bundle.putString("metode_bayar", metode_bayar);
            bundle.putStringArrayList("waktu_pilih_jamDB", dataDB);
            bundle.putStringArrayList("waktu_pilih_jam", data);

            Fragment fragment = new DetailPemesanan();
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
