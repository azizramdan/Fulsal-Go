package com.example.futsalgo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

public class DetailLapangan extends Fragment implements OnMapReadyCallback {
    public DetailLapangan() {

    }
    LinearLayout view;
    Double latitude, longitude;
    String title;
    ScrollView nScrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = (LinearLayout) inflater.inflate(R.layout.detail_lapangan, container, false);

        ImageView foto = view.findViewById(R.id.foto);
        TextView nama = view.findViewById(R.id.nama);
        TextView harga = view.findViewById(R.id.harga);
        TextView telp = view.findViewById(R.id.telp);
        TextView alamat = view.findViewById(R.id.alamat);

        Bundle bundle = this.getArguments();

        Glide.with(this)
            .load(bundle.getString("foto"))
            .placeholder(R.drawable.picture)
            .into(foto);
        nama.setText(bundle.getString("nama"));
        harga.setText("Harga: " + bundle.getString("harga"));
        telp.setText("No. HP: " + bundle.getString("telp"));
        alamat.setText("Alamat: " + bundle.getString("alamat"));

        title = bundle.getString("nama");
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



        getActivity().setTitle("Detail Lapangan");

        return view;
    }
    public void onMapReady(GoogleMap googleMap) {

        LatLng myloc = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(myloc)
                .title(title));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myloc, 20));
    }
}
