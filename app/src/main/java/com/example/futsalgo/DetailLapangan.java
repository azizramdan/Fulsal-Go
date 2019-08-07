package com.example.futsalgo;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.support.constraint.Constraints.TAG;

public class DetailLapangan extends Fragment implements OnMapReadyCallback {
    public DetailLapangan() {

    }
    LinearLayout view;
    Double latitude, longitude;
    String title, harga_lapangan, bank, nama_rekening, no_rekening, no_hp;
    ScrollView nScrollView;
    Integer id_lapangan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = (LinearLayout) inflater.inflate(R.layout.detail_lapangan, container, false);

        ImageView foto = view.findViewById(R.id.foto);
        TextView nama = view.findViewById(R.id.nama);
        TextView harga = view.findViewById(R.id.harga);
        TextView telp = view.findViewById(R.id.telp);
        TextView alamat = view.findViewById(R.id.alamat);
        Button lanjut_pesan = view.findViewById(R.id.lanjut_pesan);

        Bundle bundle = this.getArguments();

        Glide.with(this)
            .load(bundle.getString("foto"))
            .placeholder(R.drawable.picture)
            .into(foto);
        nama.setText(bundle.getString("nama"));
        harga.setText("Harga per jam: " + NumberFormat.getCurrencyInstance(new Locale("id", "ID")).format(Double.parseDouble(bundle.getString("harga"))));
        telp.setText("No. HP: " + bundle.getString("telp"));
        alamat.setText("Alamat: " + bundle.getString("alamat"));

        id_lapangan = bundle.getInt("id");
        title = bundle.getString("nama");
        no_hp = bundle.getString("telp");
        harga_lapangan = bundle.getString("harga");
        bank = bundle.getString("bank");
        nama_rekening = bundle.getString("nama_rekening");
        no_rekening = bundle.getString("no_rekening");
        latitude = Double.parseDouble(bundle.getString("latitude"));
        longitude = Double.parseDouble(bundle.getString("longitude"));


        nScrollView = (ScrollView) view.findViewById(R.id.svContainer);
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
                            googleMap.addMarker(new MarkerOptions().position(marker_latlng).title(title));
                        }
                    }
                }
            });
        }

        lanjut_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Fragment fragment = new Pemesanan();
//                AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                activity.getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.frame_container, fragment)
//                        .addToBackStack(null)
//                        .commit();
                lanjutPesan(v);
            }
        });

        getActivity().setTitle("Detail Lapangan");

        return view;
    }
    public void onMapReady(GoogleMap googleMap) {

        LatLng myloc = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(myloc)
                .title(title));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myloc, 20));
    }

    public void lanjutPesan(View view) {
        final View v = view;
        final Calendar c = Calendar.getInstance();
        Integer mYear = c.get(Calendar.YEAR);
        Integer mMonth = c.get(Calendar.MONTH);
        Integer mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        date = Konfigurasi.parseDate(date, "yyyy-M-d", "yyyy-MM-dd");
                        Log.d(TAG, "berhasil mang date " + date);

                        Bundle bundle = new Bundle();
                        bundle.putInt("id_lapangan", id_lapangan);
                        bundle.putString("nama_lapangan", title);
                        bundle.putString("no_hp", no_hp);
                        bundle.putString("harga_lapangan", harga_lapangan);
                        bundle.putString("waktu_pilih", date);
                        bundle.putString("bank", bank);
                        bundle.putString("nama_rekening", nama_rekening);
                        bundle.putString("no_rekening", no_rekening);

                        Fragment fragment = new Pemesanan();
                        fragment.setArguments(bundle);
                        AppCompatActivity activity = (AppCompatActivity) v.getContext();
                        activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container, fragment)
                        .addToBackStack(null)
                        .commit();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }
}
