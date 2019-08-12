package com.example.futsalgo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.NumberFormat;
import java.util.Locale;
import static java.lang.Boolean.FALSE;

public class PesananSayaDetail extends Fragment implements OnMapReadyCallback {
    public PesananSayaDetail() {}

    LinearLayout view;
    String nama_lapangan, waktu_pilih_tanggal, waktu_pilih_jam, metode_bayar, status, harga, alamat, telp;
    Double  latitude, longitude;
    Integer id;
    TextView tvnama_lapangan, tvharga, tvwaktu_pilih_tanggal, tvwaktu_pilih_jam, tvstatus, tvmetode_bayar, tvalamat, tvbank, tvnama_rekening, tvno_rekening, tvketerangan, tvtelp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = (LinearLayout) inflater.inflate(R.layout.pesanan_saya_detail, container, false);
        getActivity().setTitle("Detail Pemesanan");

        Bundle bundle = this.getArguments();
        id = bundle.getInt("id");
        nama_lapangan = bundle.getString("nama_lapangan");
        waktu_pilih_tanggal = bundle.getString("waktu_pilih_tanggal");
        waktu_pilih_jam = bundle.getString("waktu_pilih_jam");
        metode_bayar = bundle.getString("metode_bayar");
        status = bundle.getString("status");
        harga = bundle.getString("harga");
        alamat = bundle.getString("alamat");
        telp = bundle.getString("telp");
        latitude = Double.parseDouble(bundle.getString("latitude"));
        longitude = Double.parseDouble(bundle.getString("longitude"));

        tvnama_lapangan = view.findViewById(R.id.nama_lapangan);
        tvharga = view.findViewById(R.id.harga);
        tvwaktu_pilih_tanggal = view.findViewById(R.id.waktu_pilih_tanggal);
        tvwaktu_pilih_jam = view.findViewById(R.id.waktu_pilih_jam);
        tvstatus = view.findViewById(R.id.status);
        tvmetode_bayar = view.findViewById(R.id.metode_bayar);
        tvalamat = view.findViewById(R.id.alamat);
        tvbank = view.findViewById(R.id.bank);
        tvnama_rekening = view.findViewById(R.id.nama_rekening);
        tvno_rekening = view.findViewById(R.id.no_rekening);
        tvtelp = view.findViewById(R.id.telp);
        tvketerangan = view.findViewById(R.id.keterangan);

        if(metode_bayar.equals("cod")) {
            tvbank.setVisibility(View.GONE);
            tvnama_rekening.setVisibility(View.GONE);
            tvno_rekening.setVisibility(View.GONE);
            tvtelp.setVisibility(View.GONE);
            tvketerangan.setText("Segera lakukan pembayaran ke lokasi sebelum jam main tiba, apabila sudah melewati jam main maka otomatis pesanan akan dibatalkan");
        } else {
            tvbank.setText(bundle.getString("bank"));
            tvnama_rekening.setText("a.n. " + bundle.getString("nama_rekening"));
            tvno_rekening.setText(bundle.getString("no_rekening"));
            tvketerangan.setText("Segera lakukan pembayaran melalui transfer, lalu kirimkan bukti transfer sebelum jam main tiba ke no dibawah");
            tvtelp.setText(telp);
        }

        String harga_idr = NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(Double.parseDouble(harga));

        tvnama_lapangan.setText(nama_lapangan);
        tvharga.setText("Harga: " + harga_idr);
        tvwaktu_pilih_tanggal.setText("Tanggal: " + waktu_pilih_tanggal);
        tvwaktu_pilih_jam.setText("Jam: " + waktu_pilih_jam);
        tvstatus.setText("Status: " + status);
        tvmetode_bayar.setText("Pembayaran melalui " + metode_bayar);
        tvalamat.setText("Alamat lapangan: " + alamat);

        final ScrollView nScrollView = view.findViewById(R.id.svContainer);
        MySupportMapFragment mSupportMapFragment;
        mSupportMapFragment = (MySupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mSupportMapFragment.setListener(new MySupportMapFragment.OnTouchListener() {
            @Override
            public void onTouch() {
                nScrollView.requestDisallowInterceptTouchEvent(true);
            }
        });

        if (mSupportMapFragment != null) {
            mSupportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    if (googleMap != null) {
                        googleMap.getUiSettings().setAllGesturesEnabled(true);
                        googleMap.getUiSettings().setZoomControlsEnabled(true);
                        LatLng marker_latlng = new LatLng(latitude, longitude);
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(marker_latlng).zoom(15.0f).build();
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                        googleMap.moveCamera(cameraUpdate);
                        if (latitude != null && longitude != null) {
                            googleMap.addMarker(new MarkerOptions().position(marker_latlng).title(nama_lapangan));
                        }
                    }
                }
            });
        }

        Button batal = view.findViewById(R.id.batal);
        if(!status.equals("Belum bayar")) {
            batal.setEnabled(FALSE);
        } else {
            batal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    batalPesanan(view);
                }
            });
        }

        return view;
    }
    public void onMapReady(GoogleMap googleMap) {

        LatLng myloc = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(myloc)
                .title(nama_lapangan));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myloc, 20));
    }

    private void batalPesanan(View view) {
        final View v = view;
        new AlertDialog.Builder(getContext())
                .setTitle("Batalkan pesanan?")
                .setMessage(
                        "Apakah Anda yakin ingin membatalkan pesanan?")
                .setIcon(
                        android.R.drawable.ic_dialog_alert
                )
                .setPositiveButton(
                        "Ya",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                                progressDialog.setMessage("Sending...");
                                progressDialog.show();
                                AndroidNetworking.get(Konfigurasi.PESANAN)
                                        .addQueryParameter("method", "batal")
                                        .addQueryParameter("id", id.toString())
                                        .setPriority(Priority.LOW)
                                        .build()
                                        .getAsJSONObject(new JSONObjectRequestListener() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    progressDialog.dismiss();
                                                        if(response.getBoolean("status")) {
                                                            Toast.makeText(getActivity(), "Pesanan berhasil dibatalkan!", Toast.LENGTH_LONG).show();

                                                            Fragment fragment = new PesananSayaMenu();
                                                            AppCompatActivity activity = (AppCompatActivity) v.getContext();

                                                            activity.getSupportFragmentManager()
                                                                    .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                                            activity.getSupportFragmentManager()
                                                                    .beginTransaction()
                                                                    .replace(R.id.frame_container, fragment)
                                                                    .commit();
                                                        } else {
                                                            Toast.makeText(getActivity(), "Pesanan gagal dibatalkan!", Toast.LENGTH_LONG).show();
                                                        }

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                    progressDialog.dismiss();
                                                    Toast.makeText(getActivity(), "Pesanan gagal dibatalkan!", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                            @Override
                                            public void onError(ANError error) {
                                                progressDialog.dismiss();
                                                Toast.makeText(getActivity(), "Pesanan gagal dibatalkan!", Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                        })
                .setNegativeButton(
                        "Tidak",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                //Do Something Here
                            }
                        }).show();
    }
}
