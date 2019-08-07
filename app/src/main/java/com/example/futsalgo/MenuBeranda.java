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

public class MenuBeranda extends Fragment {
    public MenuBeranda(){}
    RelativeLayout view;
    private List<Lapangan> dataList;
    private RecyclerView recyclerView;
    String sort;
    Fragment fragment = null;
    FragmentManager fragmentManager;

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
            fragment = new MenuBeranda();
            bundle.putString("sort", "terlaris");
            fragment.setArguments(bundle);
        } else if(id == R.id.termurah) {
            fragment = new MenuBeranda();
            bundle.putString("sort", "termurah");
            fragment.setArguments(bundle);
        } else if(id == R.id.kurang75) {
            fragment = new MenuBeranda();
        } else if(id == R.id.kurang100) {
            fragment = new MenuBeranda();
        } else if(id == R.id.kurang150) {
            fragment = new MenuBeranda();
        }
        callFragment(fragment);
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

        view = (RelativeLayout) inflater.inflate(R.layout.menu_beranda, container, false);
        recyclerView = view.findViewById(R.id.main_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dataList = new ArrayList<>();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            sort = bundle.getString("sort", "");
        }

        AndroidNetworking.initialize(getActivity());

        getLapangan();
        return view;
    }

    public void getLapangan() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        AndroidNetworking.get(Konfigurasi.LAPANGAN)
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

}
