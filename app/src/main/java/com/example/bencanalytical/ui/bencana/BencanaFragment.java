package com.example.bencanalytical.ui.bencana;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bencanalytical.LoginActivity;
import com.example.bencanalytical.R;
import com.example.bencanalytical.adapter.BencanaAdapter;
import com.example.bencanalytical.model.responseBencana;
import com.example.bencanalytical.model.responseBencanaData;
import com.example.bencanalytical.services.ApiClient;
import com.example.bencanalytical.services.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BencanaFragment extends Fragment {
    private View view;
    private ProgressDialog progress;
    private ApiService apiService;
    private List<responseBencanaData> responseBencanaData;
    private BencanaAdapter adapter;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bencana, container, false);

        recyclerView = view.findViewById(R.id.rvBencana);
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
        Call<responseBencana> responseBencanaCall = apiService.getBencana();
        responseBencanaCall.enqueue(new Callback<responseBencana>() {
            @Override
            public void onResponse(Call<responseBencana> call, Response<responseBencana> response) {
                if (response.isSuccessful()){
                    responseBencanaData = new ArrayList<>();

                    responseBencanaData.addAll(response.body().getData());

                    progress.dismiss();
                }
                adapter = new BencanaAdapter(responseBencanaData);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<responseBencana> call, Throwable t) {
                Log.i("ASD", call.request().url().toString());

                progress.dismiss();
                Toast.makeText(view.getContext(), "Jaringan Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}