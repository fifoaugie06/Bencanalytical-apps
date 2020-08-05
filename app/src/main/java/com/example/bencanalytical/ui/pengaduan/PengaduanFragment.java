package com.example.bencanalytical.ui.pengaduan;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bencanalytical.R;
import com.example.bencanalytical.adapter.PengaduanAdapter;
import com.example.bencanalytical.model.responsePengaduan;
import com.example.bencanalytical.model.responsePengaduanData;
import com.example.bencanalytical.services.ApiClient;
import com.example.bencanalytical.services.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengaduanFragment extends Fragment {
    private View view;
    private ProgressDialog progress;
    private ApiService apiService;
    private List<responsePengaduanData> responsePengaduanData;
    private PengaduanAdapter adapter;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_pengaduan, container, false);

        recyclerView = view.findViewById(R.id.rvPengaduan);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        loadData();

        return view;
    }

    private void loadData() {
        progress = new ProgressDialog(view.getContext());
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();

        apiService = ApiClient.getClient().create(ApiService.class);
        Call<responsePengaduan> responsePengaduanCall = apiService.getPengaduan("1");
        responsePengaduanCall.enqueue(new Callback<responsePengaduan>() {
            @Override
            public void onResponse(Call<responsePengaduan> call, Response<responsePengaduan> response) {
                if (response.isSuccessful()){
                    responsePengaduanData = new ArrayList<>();

                    responsePengaduanData.addAll(response.body().getData());

                    progress.dismiss();
                }
                adapter = new PengaduanAdapter(responsePengaduanData);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<responsePengaduan> call, Throwable t) {

            }
        });
    }
}