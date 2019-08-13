package com.example.futsalgo;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import java.util.ArrayList;
import java.util.List;

public class BerandaMenu extends Fragment {
    public BerandaMenu(){}
    RelativeLayout view;
    private List<Lapangan> dataList, lapanganKategori;
    private RecyclerView recyclerView;
    String sort;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        Bundle bundle = new Bundle();

        if(id == R.id.terlaris) {
            fragment = new BerandaMenu();
            bundle.putString("sort", "terlaris");
            fragment.setArguments(bundle);
            callFragment(fragment);
        } else if(id == R.id.termurah) {
            fragment = new BerandaMenu();
            bundle.putString("sort", "termurah");
            fragment.setArguments(bundle);
            callFragment(fragment);
        }

        if(id == R.id.kurang75) {
            lapanganKategori(75000);
        } else if(id == R.id.kurang100) {
            lapanganKategori(100000);
        } else if(id == R.id.kurang150) {
            lapanganKategori(150000);
        }
        return super.onOptionsItemSelected(item);
    }
    private void callFragment(Fragment fragment) {
        fragmentManager = getFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Beranda");

        view = (RelativeLayout) inflater.inflate(R.layout.beranda_menu, container, false);
        recyclerView = view.findViewById(R.id.main_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dataList = new ArrayList<>();
        lapanganKategori = new ArrayList<>();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            sort = bundle.getString("sort", "");
        }

        AndroidNetworking.initialize(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        getLapangan();
        return view;
    }

    public void getLapangan() {
        progressDialog.show();
        AndroidNetworking.get(Konfigurasi.LAPANGAN)
                .addQueryParameter("method", "index")
                .addQueryParameter("sort", sort)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject dataLapangan = data.getJSONObject(i);
                                dataList.add(new Lapangan(
                                        dataLapangan.getInt("id"),
                                        dataLapangan.getString("nama"),
                                        dataLapangan.getString("harga"),
                                        dataLapangan.getString("foto"),
                                        dataLapangan.getString("telp"),
                                        dataLapangan.getString("alamat"),
                                        dataLapangan.getString("latitude"),
                                        dataLapangan.getString("longitude"),
                                        dataLapangan.getString("bank"),
                                        dataLapangan.getString("nama_rekening"),
                                        dataLapangan.getString("no_rekening")
                                        ));
                            }
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
                        progressDialog.dismiss();
                    }
                });
    }

    private void lapanganKategori(Integer kategori) {
        progressDialog.show();
        lapanganKategori.clear();
        for (int i = 0; i < dataList.size(); i++) {
            int harga;
            try {
                harga = Integer.parseInt(dataList.get(i).getHarga());
                if(harga <= kategori) {
                    lapanganKategori.add(new Lapangan(
                        dataList.get(i).getId(),
                        dataList.get(i).getNama(),
                        dataList.get(i).getHarga(),
                        dataList.get(i).getFoto(),
                        dataList.get(i).getTelp(),
                        dataList.get(i).getAlamat(),
                        dataList.get(i).getLatitude(),
                        dataList.get(i).getLongitude(),
                        dataList.get(i).getBank(),
                        dataList.get(i).getNamaRekening(),
                        dataList.get(i).getNoRekening()
                    ));
                }
            } catch (NumberFormatException nfe) {

            }
        }
        LapanganAdapter adapter = new LapanganAdapter(lapanganKategori);
        recyclerView.setAdapter(adapter);
        progressDialog.dismiss();
    }

}
